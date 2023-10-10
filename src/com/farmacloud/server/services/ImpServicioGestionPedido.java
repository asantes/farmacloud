package com.farmacloud.server.services;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.FetchGroup;
import javax.jdo.FetchPlan;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.commons.lang3.concurrent.ConcurrentException;

import com.farmacloud.client.services.ServicioGestionPedido;
import com.farmacloud.server.utiles.PMF;
import com.farmacloud.shared.model.LineaPedido;
import com.farmacloud.shared.model.MedicamentoFarmacia;
import com.farmacloud.shared.model.Pedido;
import com.farmacloud.shared.model.Proveedor;
import com.farmacloud.shared.model.UnidadMedicamento;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ImpServicioGestionPedido extends RemoteServiceServlet implements ServicioGestionPedido
{
	public static final Logger log = Logger.getLogger(ImpServicioUsuario.class.getName());
	public static final int MAX_RETRIES = 5;
	public static final int SLEEP_TIME_MILLIS = 100;
	
	@Override
	public void crearPedido(Pedido pedido, String primaryKeyProveedor)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		/* Establecemos los atributos necesarios por adelantado */
		pedido.setEstado("PENDIENTE");
		pedido.setFecha(new Date());
		pedido.setIdExterno((int) (Math.random() * (99999 - 1) + 1));
		pedido.setImporte((float) (Math.random() * (99999 - 1) + 1));
		
		boolean exito = false;
		int retries = MAX_RETRIES;
		while(retries>=0 && !exito)
		{
			try
			{
				tx.begin();
				Proveedor proveedor = obtenerProveedor(primaryKeyProveedor);
			
				if(proveedor != null)
				{
					System.out.println("Encuentro el proveddor > "+proveedor.getNombre());
					pm.makePersistent(pedido);
				}
				
				tx.commit();
				exito = true;
			}
			catch(ConcurrentModificationException e)
			{
				log.log(Level.SEVERE, "Server --> crearPedido(); "+e);
				retries--;
			}
			finally
			{
				if(tx.isActive())
					tx.rollback();
				
				if(retries!=0 && !exito)
					duerme();
			}
		}
		
		pm.close();
		
		if(exito)
			log.info("Server --> crearPedido(); ejecucion exitosa");
	}

	@Override
	public List<Proveedor> getNombreProveedoresPedidosNoCompletos(String tipo)
	{
		List<Proveedor> listaProveedoresRetorno = new ArrayList<Proveedor>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		/* Para obtener todos las entidades Proveedor */
		Query q = pm.newQuery(Proveedor.class);
		
		/* Con esta consulta obtenemos las entidades Pedido en un determinado ESTADO y realizadas a un determinado Proveedor */
		Query q2 = pm.newQuery(Pedido.class);
		q2.setFilter("estado == estadoParam && proveedorKey == proveedorKeyParam");
		q2.declareParameters("String estadoParam, String proveedorKeyParam");
		
		try
		{
			/* Obtenemos todos los proveedores */
			List<Proveedor> proveedores = (List<Proveedor>) q.execute();
			if(!proveedores.isEmpty())
			{
				/* Para cada proveedor iteramos */
				for(Proveedor proveedor: proveedores)
				{
					List<Pedido> pedidos = (List<Pedido>) q2.execute(tipo, proveedor.getNIF());
					
					/* Si tiene pedidos en un estado determinado lo añadimos a la lista */
					if(!pedidos.isEmpty())
					{
						System.out.println("No puede sert "+pedidos.get(0).getIdInterno());
						listaProveedoresRetorno.add(proveedor);
					}
				}
			}
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "Server --> getNombreProveedoresPedidoNoCompleto(); "+e);
		}
		finally
		{
			q.closeAll();
			q2.closeAll();
			pm.close();
		}
		
		if(!listaProveedoresRetorno.isEmpty())
		{
			log.info("Server --> getNombrePedidosNoCompletos(); Listado de Proveedores con pedidos en estado "+tipo);
			for(Proveedor p : listaProveedoresRetorno)
				log.info(p.getNombre());
		}
		else
			log.info("Server --> getNombrePedidosNoCompletos(); ningun Proveedor con pedidos en estado "+tipo);
		
		return listaProveedoresRetorno;
	}

	@Override
	public List<Pedido> getPedidosNoCompletos(String primaryKeyProveedor, String tipo)
	{
		List<Pedido> listaPedidosReturn = new ArrayList<Pedido>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
			
		/* Consulta para obtener todos las entidades Pedido con un determinado ESTADO y realizados a un determinado Proveedor */
		Query q = pm.newQuery(Pedido.class);
		q.setFilter("proveedorKey == proveedorKeyParam && estado == estadoParam");
		q.declareParameters("String proveedorKeyParam, String estadoParam");
		
		try
		{	
			List<Pedido> listaPedidos = (List<Pedido>) q.execute(primaryKeyProveedor, tipo);			
			if(!listaPedidos.isEmpty())
			{
				for(Pedido p : listaPedidos)
					listaPedidosReturn.add(p);	
			}
			else 
				log.info("Server --> getPedidosNoCompletos(); no hay pedidos en estado "+tipo+" del proveedor "+primaryKeyProveedor);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "Server --> getPedidosNoCompletos(); "+e);
		}
		finally
		{
			q.closeAll();
			pm.close();
		}

		return listaPedidosReturn;
	}
	
	@Override
	public Pedido getPedidoFull(int primaryKeyPedido) 
	{
		Pedido pedido = obtenerPedido(primaryKeyPedido);
		return pedido;
	}

	@Override
	public List<MedicamentoAbstracto> recepcionarPedido(int primaryKeyPedido, List<LineaPedido> listaMedicamentos)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		Pedido pedido = obtenerPedido(primaryKeyPedido);
		boolean exito = false;
		
		boolean recepcionOk = false;
		if(pedido != null)
		{
			for(LineaPedido lpDDBB: pedido.getListaLineaPedido())
			{
				String codigoNacional = lpDDBB.getListaUnidadMedicamento().get(0).getCodigoNacional();
				int i = 1;
				boolean medEncontrado = false;
				for(LineaPedido lp : listaMedicamentos)
				{
					if(lp.getCodigoNacional().equals(codigoNacional) && medEncontrado==false)
					{
						if(lp.getUnidadesPedidas() == lp.getUnidadesRecibidas())
						{
							System.out.println("Server oki: recepcionando med biien");
							medEncontrado = true;
						}
						else
						{
							/* Faltan unidades de un medicamento */
							log.info("Server --> recepcionarPedido(); faltan unidades del medicamento con codigo nacional "+codigoNacional);
							medEncontrado = true;
							recepcionOk = false;
						}
					}
					else if(listaMedicamentos.size() == i && medEncontrado==false)
						{
							/* Falta por recepcionar un medicamento */
							log.info("Server --> recepcionarPedido(); falta por recepcionar el medicamento con codigo nacion "+codigoNacional);
							recepcionOk=false;
						}
					i++;
				}
			}
		}

		/* En caso de haber recepcionado correctamente en el cliente, marcamos las entidades UnidadMedicamento como DISPONIBLES */
		if(recepcionOk)
		{
			log.info("Server --> recepcionarPedido(); check de recepcion de pedido ok");
			
			int retries = MAX_RETRIES;
			while(retries>=0 && !exito)
			{
				try
				{
					tx.begin();
					
					for(LineaPedido lp : pedido.getListaLineaPedido())
					{
						for(UnidadMedicamento um : lp.getListaUnidadMedicamento())
						{
							um.setEstado("DISPONIBLE");
						}
					}
					
					/* Actualizamos la entidad Pedido a estado RECEPCIONADO */
					pedido.setEstado("RECEPCIONADO");
					
					tx.commit();
					exito = true;
				}
				catch(ConcurrentModificationException e)
				{
					log.log(Level.SEVERE, "Server --> recepcionarPedido(); "+e);
				}
				finally
				{
					if (tx.isActive())
				        tx.rollback();
					
					if(retries!=0 && !exito)
						duerme();
				}
			}
		}
		
		if(exito)
			log.info("Server --> recepcionarPedido(); ejecucion exitosa");
		
		return null;
	}
	
	public Proveedor obtenerProveedor(Object primaryKey)

	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		FetchPlan fp = pm.getFetchPlan();
		FetchGroup fg = pm.getFetchGroup(Proveedor.class, "completo");
		fg.addMember("catalogo");
		fp.addGroup("completo");
		Proveedor entidad = null;
		Proveedor entidadDetached = null;
		
		try
		{
			entidad = pm.getObjectById(Proveedor.class, primaryKey);
			entidadDetached = pm.detachCopy(entidad);
		}
		catch(JDOObjectNotFoundException e)
		{
			log.log(Level.INFO, "Server --> no existe ninguna entidad con clave primaria "+primaryKey +" en el tipo Proveedor");
		}
		finally
		{
			pm.close();
		}
		
		return entidadDetached;
	}
	
	public Pedido obtenerPedido(Object primaryKey)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		FetchPlan fp = pm.getFetchPlan();
		FetchGroup fg = pm.getFetchGroup(Pedido.class, "completo");
		fg.addMember("listaLineaPedido");
		fp.addGroup("completo");
		Pedido entidad = null;
		Pedido entidadDetached = null;
		
		try
		{
			entidad = pm.getObjectById(Pedido.class, primaryKey);
			entidadDetached = pm.detachCopy(entidad);
		}
		catch(JDOObjectNotFoundException e)
		{
			log.log(Level.INFO, "Server --> no existe ninguna entidad con clave primaria "+primaryKey+" en el tipo Pedido");
		}
		finally
		{
			pm.close();
		}
		
		return entidadDetached;
	}
	
	public void duerme()
	{
		try
		{
			Thread.sleep(SLEEP_TIME_MILLIS);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
			log.log(Level.SEVERE, "Server --> ImpServicioGestionPedido.java");
		}
	}
}


