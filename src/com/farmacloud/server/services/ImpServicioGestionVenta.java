package com.farmacloud.server.services;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.farmacloud.client.services.ServicioGestionVenta;
import com.farmacloud.server.utiles.PMF;
import com.farmacloud.shared.model.UnidadMedicamento;
import com.farmacloud.shared.model.Venta;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ImpServicioGestionVenta extends RemoteServiceServlet implements ServicioGestionVenta
{
	public static final Logger log = Logger.getLogger(ImpServicioUsuario.class.getName());
	public static final int MAX_RETRIES = 5;
	public static final int SLEEP_TIME_MILLIS = 100;

	public ImpServicioGestionVenta() {
		System.out.println("soy el señor ventas");
	}
	@Override
	public List<UnidadMedicamento> buscarUnidad(String codigoNacional, int num) 
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		List<UnidadMedicamento> listaUnidadMedicamentos = new ArrayList<UnidadMedicamento>();
		
		/* Obtenemos todas las UnidadMedicamento con estado DISPONIBLE del medicamento con codigo
		 * nacional recibido como parametro. Por orden de fecha de caducidad primero
		 */
		Query q = pm.newQuery(UnidadMedicamento.class);
		q.setFilter("codigoNacional == codigoNacionalParam && estado == estadoParam");
		q.declareParameters("String codigoNacionalParam, String estadoParam");
		q.setOrdering("fechaCaducidad asc");
		
		boolean exito = false;
		int retries = 5;
		while(retries>=0 && !exito)
		{
			try
			{
				tx.begin();
				
				List<UnidadMedicamento> listaAuxiliar = (List<UnidadMedicamento>) q.execute();
				if(listaAuxiliar.size() == num)
				{
					for(UnidadMedicamento um : listaAuxiliar)
					{
						um.setEstado("PROCESANDO");
						listaUnidadMedicamentos.add(um);
					}
				}
				else
				{
					log.info("Server --> buscarUnidad(); sin unidades suficientes del medicamento con codigo nacional "+codigoNacional);
				}
				
				tx.commit();
				exito = true;
			}
			catch (ConcurrentModificationException e)
			{
		        if (retries == 0) {
		        	System.out.println("Server fallaco: buscando Unidad . Todaslas retries realizadas");
		            throw e;
		        }
		        
		        log.log(Level.SEVERE, "Server --> buscarUnidad(); "+e);
		        --retries;
			}
			finally
			{
				if (tx.isActive())
			        tx.rollback();
				
				if(retries!=0 && !exito)
					duerme();
			}
		}
		
		if(exito)
			log.log(Level.INFO, "Server --> buscarUnidad(); ejecucion exitosa");
				
		q.closeAll();
		pm.close();
		
		return listaUnidadMedicamentos;
	}

	@Override
	public void cerrarVenta(Venta venta) 
	{
		System.out.println("hola");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		venta.setFechaVenta(new Date());
		
		boolean exito = false;
		int retries = 5;
		while(retries>0 && !exito)
		{
			try
			{
				System.out.println("llegammos aqui");
				tx.begin();
				
				/* Persistimos la venta */
				pm.makePersistent(venta);
				
				tx.commit();
				exito = true;
			}
			catch (ConcurrentModificationException e)
			{
		        if (retries == 0) {
		        	System.out.println("Server fallaco: cerrandoVenta. Toda las retries realizadas");
		            throw e;
		        }
		
		        log.log(Level.SEVERE, "Server --> cerrarVenta(); "+e);
		        --retries;
			}
			finally
			{
				if(tx.isActive())
					tx.rollback();
				
				if(retries!=0 && !exito)
					duerme();
			}
		}
		
		if(exito)
			log.log(Level.INFO, "Server --> buscarUnidad(); ejecucion exitosa");
		
		pm.close();
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
			log.log(Level.SEVERE, "Server --> ImpServicioGestionVenta.java");
		}
	}
}