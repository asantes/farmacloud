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
import javax.validation.Validation;
import javax.validation.Validator;

import com.farmacloud.client.services.ServicioGestionProveedor;
import com.farmacloud.server.utiles.PMF;
import com.farmacloud.server.utiles.Utiles;
import com.farmacloud.shared.model.CatalogoMedicamentos;
import com.farmacloud.shared.model.Medicamento;
import com.farmacloud.shared.model.Proveedor;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.google.inject.Singleton;

@Singleton
public class ImpServicioGestionProveedor extends XsrfProtectedServiceServlet implements ServicioGestionProveedor 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6872548265836750027L;
	public static final Logger log = Logger.getLogger(ImpServicioGestionProveedor.class.getName());
	public static final int MAX_RETRIES = 5;
	public static final int SLEEP_TIME_MILLIS = 100;

	Validator validator;
	
	public ImpServicioGestionProveedor() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	} 
	
	@Override
	public Proveedor añadirProveedor(Proveedor proveedor) 
	{	
		Proveedor proveedorDetached = null;
		CatalogoMedicamentos catalogo = new CatalogoMedicamentos();
		catalogo.setFecha(new Date());
		catalogo.setNumMedicamentos(0);
		catalogo.setKeyName("catalogo-"+proveedor.getNombre());
		boolean exito = false;
		
		if(Utiles.validate(validator, proveedor))
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Transaction tx = pm.currentTransaction();
	
			int retries = MAX_RETRIES;
			while(retries>=0 && !exito)
			{
				try 
				{
					tx.begin();
					
					pm.makePersistent(proveedor);
					
					tx.commit();
					proveedorDetached = pm.detachCopy(proveedor);
					exito = true;
				}
				catch (ConcurrentModificationException e)
				{
					log.severe(e.getMessage());
			        --retries;
				}
				finally 
				{
				    if (tx.isActive()) 
				        tx.rollback();	
				    /* Tras un pequeño sleep reintentamos */
					if(retries!=0){
						Utiles.duerme(this.getServletName(), SLEEP_TIME_MILLIS);
					}
				}
			}
		}
		else
			log.fine("Encontradas violaciones al validar");
		
		if(exito)
			log.fine("Ejecucion exitosa");

		return proveedorDetached;
	}
	
	public List<Proveedor> obtenerProveedores()
	{
		List<Proveedor> listaProveedores = new ArrayList<Proveedor>();

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Proveedor proveedor = new Proveedor();
		Query q = pm.newQuery(Proveedor.class);

		try 
		{
		  List<Proveedor> results = (List<Proveedor>) q.execute();
		  if (!results.isEmpty()) 
		  {
			  for(Proveedor p : results)
			  {
				  Proveedor aux = p;
				  listaProveedores.add(aux);
			  }
		  } 
		  else
		  {
			  log.log(Level.INFO, "Sin Proveedores en la BBDD");
		  }
		}
		finally 
		{
		  q.closeAll();
		  pm.close();
		}
	
		return listaProveedores;
	}
	
	@Override
	public List<MedicamentoAbstracto> getCatalogoMedicamentos(String proveedorNIF)
	{
		List<MedicamentoAbstracto> listaMedicamento = new ArrayList<MedicamentoAbstracto>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query qFinanciados = pm.newQuery(Medicamento.class);
		qFinanciados.setFilter("parentKey == parentKeyParam");
		qFinanciados.declareParameters("String parentKeyParam");
		
		Query qNoFinanciados = pm.newQuery(Medicamento.class);
		qNoFinanciados.setFilter("parentKey == parentKeyParam");
		qNoFinanciados.declareParameters("String parentKeyParam");
		
		try 
		{ 
		  List<MedicamentoAbstracto> resultsFinanciados = (List<MedicamentoAbstracto>) qFinanciados.execute(proveedorNIF);
		  if (!resultsFinanciados.isEmpty()) 
		  {
			  for(MedicamentoAbstracto m : resultsFinanciados)
				  if(m!=null)
					  listaMedicamento.add(m);
		  } 
		  
		  List<MedicamentoAbstracto> resultsNoFinanciados = (List<MedicamentoAbstracto>) qNoFinanciados.execute(proveedorNIF);
		  if (!resultsNoFinanciados.isEmpty()) 
		  {
			  for(MedicamentoAbstracto m : resultsNoFinanciados)
				  if(m!=null)
					  listaMedicamento.add(m);
		  }

		}
		finally 
		{
		  qFinanciados.closeAll();
		  qNoFinanciados.closeAll();
		  pm.close();
		}

		return listaMedicamento;
	}
	
	public static Proveedor obtenerProveedor(Object primaryKey)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		FetchPlan fp = pm.getFetchPlan();
		FetchGroup fg = pm.getFetchGroup(Proveedor.class, "completo");
		fg.addMember("catalogo");;
		fp.addGroup("completo");
		Proveedor entidad = null;
		Proveedor entidadDetached = null;
		
		try
		{
			entidad = pm.getObjectById(Proveedor.class, primaryKey);
			entidadDetached = pm.detachCopy(entidad);
			log.log(Level.INFO, "encontrada una entidad con clave primaria "+primaryKey +" en el tipo Proveedor");
		}
		catch(JDOObjectNotFoundException e){
			log.log(Level.INFO, "no existe ninguna entidad con clave primaria "+primaryKey +" en el tipo Proveedor");
		}
		finally
		{
			pm.close();
		}
		
		return entidadDetached;
	}
	
}



