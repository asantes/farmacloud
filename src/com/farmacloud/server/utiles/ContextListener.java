package com.farmacloud.server.utiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.farmacloud.server.services.ImpServicioUsuario;
import com.farmacloud.shared.model.CatalogoMedicamentos;
import com.farmacloud.shared.model.Medicamento;
import com.farmacloud.shared.model.MedicamentoProveedor;
import com.farmacloud.shared.model.Proveedor;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;

public class ContextListener implements ServletContextListener
{
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	public static final Logger log = Logger.getLogger(ImpServicioUsuario.class.getName());
	
	public static List<String> getNombresCatalogoNacional (ServletContext context){
		return (List<String>) context.getAttribute("catalogoNombres");
	}

	public static CatalogoMedicamentos getCatalogoMedicamentos(ServletContext context, String primaryKey){
		return(CatalogoMedicamentos) context.getAttribute(primaryKey);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		getNombresCatalogoNacional(arg0.getServletContext()).clear();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) 
	{	
		log.info("Contexto inicializado");
		//loadFullNomenclator(arg0);
		loadNomenclatorsNames(arg0);
	}
	
	public void loadNomenclatorsNames(ServletContextEvent arg0)
	{
		/* Utilizamos la API de bajo nivel pues es mas rapida */
		List<String> catalogoNombres = new ArrayList<String>();
		
		/* Recuperamos todas las entidades MedicamentoFinanciado de la BBDD */
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Medicamento");
	    PreparedQuery pq = datastore.prepare(q);
		//FetchOptions fetchOptions = FetchOptions.Builder.withLimit(24000);
	    
	  
		/* Guardamos el nombre de cada entidad Medicamento en un array */
	    //QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
	    Iterator<Entity> results = pq.asIterator();
	    int i = 0;
	    while(results.hasNext())
		{
			String s = (String) results.next().getProperty("nombre");
			if(s!=null)
				catalogoNombres.add(s.toLowerCase());
			i++;
	    }
	    log.info("iteraiones "+pq.countEntities());
	    
		/* Recuperamos todas las entidades MedicamentoNoFinanciado de la BBDD 
		q = new com.google.appengine.api.datastore.Query("Medicamento");
	    pq = datastore.prepare(q);
		//FetchOptions fetchOptions = FetchOptions.Builder.withLimit(24000);
	    
		
	    //QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
	    results = pq.asIterator();
	    while(results.hasNext())
		{
			String s = (String) results.next().getProperty("nombre");
			if(s!=null)
				catalogoNombres.add(s);
	    }*/
	   
		log.info("Tamaño del listado --> "+catalogoNombres.size());
		
		/* Guardamos el arrayList con todos los nombres */
		ServletContext context = arg0.getServletContext();
		context.setAttribute("catalogoNombres", catalogoNombres);
	}
	
	public void loadFullNomenclator(ServletContextEvent arg0)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ServletContext context = arg0.getServletContext();
		
		Query q = pm.newQuery(Medicamento.class);
		CatalogoMedicamentos catalogoNacional;
		String primaryKey = "catalogo_nacional";
		
		try
		{
			catalogoNacional = pm.getObjectById(CatalogoMedicamentos.class, primaryKey);
					
			/* Obtenemos los MedicamentoFinanciado */
			List<MedicamentoAbstracto> listaMedicamentos = (List<MedicamentoAbstracto>) q.execute();
			if(listaMedicamentos != null)
			{
				for(MedicamentoAbstracto ma : listaMedicamentos)
					catalogoNacional.getListaMedicamentos().add(ma);
			}
			
			/* Obtenemos los MedicamentoNoFinanciado 
			q = pm.newQuery(Medicamento.class);
			
			listaMedicamentos.clear();
			listaMedicamentos = (List<MedicamentoAbstracto>) q.execute();
			if(listaMedicamentos != null)
			{
				for(MedicamentoAbstracto ma : listaMedicamentos)
					catalogoNacional.getListaMedicamentos().add(ma);
			}
			
			
			if(!catalogoNacional.getListaMedicamentos().isEmpty())
			{
				context.setAttribute(primaryKey, catalogoNacional.getListaMedicamentos());
				log.info("Server --> ContextListener.java: cargando catalogo_nacional");
			}*/
			
		}
		catch(JDOObjectNotFoundException e)
		{
			log.log(Level.SEVERE, ""+e);
			System.out.println("No exixte catalogo");
		}
		finally
		{
			q.closeAll();
			pm.close();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void cargarCatalogosProveedores(ServletContextEvent arg0)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ServletContext context = arg0.getServletContext();
		
		/* Queries */
		Query qProveedores = pm.newQuery(Proveedor.class);
		Query q = pm.newQuery(MedicamentoProveedor.class);
		
		try
		{
			/* Obtenemos todas las entidades Proveedor */
			List<Proveedor> lista = (List<Proveedor>) qProveedores.execute();
			
			if(lista!=null)
			{
				/* Obtenemos el catalogo de medicamentos de cada entidad Proveedor */
				for(Proveedor p : lista)
				{
					p.getCatalogo();
					Proveedor proveedor = p;
					pm.makeTransient(proveedor);
					
					/* Obtenemos los MedicamentoFinanciadoProveedor */
					q = pm.newQuery(MedicamentoProveedor.class);
					q.setFilter("parentKey == parentEncodedKeyParam");
					q.declareParameters("String parentEncodedKeyParam");
					
					List<MedicamentoAbstracto> listaMedicamentos = (List<MedicamentoAbstracto>) q.execute(proveedor.getCatalogo().getKeyName());
					if(listaMedicamentos != null)
					{
						for(MedicamentoAbstracto ma : listaMedicamentos)
							proveedor.getCatalogo().getListaMedicamentos().add(ma);
					}
					
					/* Obtenemos los MedicamentoNoFinanciadoProveedor *
					q = pm.newQuery(MedicamentoProveedor.class);
					q.setFilter("parentEncodedKey == parentEncodedKeyParam");
					q.declareParameters("String parentEncodedKeyParam");
					
					listaMedicamentos.clear();
					listaMedicamentos = (List<MedicamentoAbstracto>) q.execute(proveedor.getCatalogo().getName());
					if(listaMedicamentos != null)
					{
						for(MedicamentoAbstracto ma : listaMedicamentos)
							proveedor.getCatalogo().getListaMedicamentos().add(ma);
					}
					

					if(!proveedor.getCatalogo().getListaMedicamentos().isEmpty()){
						context.setAttribute(proveedor.getNIF(), proveedor.getCatalogo().getListaMedicamentos());
						log.info("Server --> ContexListener.java: cargado catalogo del proveedor "+proveedor.getNombre()+" y NIF "+proveedor.getNIF());
				}*/
					
					q.closeAll();
				}
			}
		}
		catch(Exception e )
		{
			log.log(Level.SEVERE, "Server --> ContextListener.java: "+e);
		}
		finally
		{
			qProveedores.closeAll();
			pm.close();
		}
	}
}
