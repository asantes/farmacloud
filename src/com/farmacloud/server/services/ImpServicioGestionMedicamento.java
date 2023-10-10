package com.farmacloud.server.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.farmacloud.client.services.ServicioGestionMedicamento;
import com.farmacloud.server.utiles.ContextListener;
import com.farmacloud.server.utiles.MyMultiOracleHelper;
import com.farmacloud.server.utiles.MyProgressListener;
import com.farmacloud.server.utiles.PMF;
import com.farmacloud.shared.model.CatalogoMedicamentos;
import com.farmacloud.shared.model.Medicamento;
import com.farmacloud.shared.model.MedicamentoFarmacia;
import com.farmacloud.shared.model.DTO.ProgressUploadDTO;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.farmacloud.shared.model.infoView.GetTableWithCursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Singleton;
import com.opencsv.CSVReader;

@SuppressWarnings("serial")
@Singleton
public class ImpServicioGestionMedicamento extends RemoteServiceServlet implements ServicioGestionMedicamento 
{	
	public static final Logger log = Logger.getLogger(ImpServicioGestionMedicamento.class.getName());
	public static final int MAX_RETRIES = 5;
	public static final int SLEEP_TIME_MILLIS = 100;
	private MyMultiOracleHelper oracle = new MyMultiOracleHelper();
	
	@Override
	public ProgressUploadDTO progressUpload()
	{
		ProgressUploadDTO progress = new ProgressUploadDTO();
		
		Session session = SecurityUtils.getSubject().getSession();
		MyProgressListener testProgressListener = (MyProgressListener) session.getAttribute("progressListener");
		if (testProgressListener == null) {
			log.log(Level.WARNING, "el progressListener es null :S");
		}
		else
		{
			progress = testProgressListener.getProgress();
			if(progress.isContentLengthKnown()){
				log.info(""+testProgressListener.getMessage());
				log.info("porcentaje de fichero subido --> "+progress.getPercentDone()+"%");
			}
			else
			{
				log.info("tamaño de fichero desconocido."+testProgressListener.getMessage());
			}
		}
		return progress;
	}

	
    private List<MedicamentoAbstracto> leerCSV(String catalogo) 
    {
		/* Lista donde almacenamos todas las entidades Medicamento que vamos a escribir en el Data Store */
		List<MedicamentoAbstracto> listaMedicamentos = new ArrayList<MedicamentoAbstracto>();
		
		/* Creamos el lector de CSV. Le pasamos como argumento de entrada el catalogo */
    	StringReader stringReader = new StringReader(catalogo);
    	CSVReader reader = new CSVReader(stringReader);
        String [] linea;

        Medicamento medicamento = new Medicamento();
        /* Leemos una a una las lineas que componen el fichero */
        try 
        {
			while ((linea = reader.readNext()) != null)
			{
				/* Cada linea se guarda en un array de String. Cada String del array contendra
				 * un campo de la linea
				 */
				
				/* Vamos creando entidades y añadiendolas a una lista */
				medicamento = new Medicamento();
				//medicamento.setParentKey(catalogoMedicamentos.getName()); Esta se hace luego
				medicamento.setCodigoNacional(linea[0]);
				medicamento.setNombre(linea[1]);
				medicamento.setLaboratorio(linea[2]);
				medicamento.setPrincipioActivo(linea[3]);
				medicamento.setPrecioPVP(Float.parseFloat(linea[4]));
				
				listaMedicamentos.add(medicamento);
			}
		} 
        catch (NumberFormatException e1) {
			e1.printStackTrace();
		} 
        catch (IOException e1) {
			e1.printStackTrace();
		}
        
        try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        if(!listaMedicamentos.isEmpty())
        {
        	log.info("Se han leido "+listaMedicamentos.size()+" entradas");
        	//crearCatalogoMedicamentos(listaMedicamentos);
        }
        else
        	log.info("No se ha leido ninguna entrada :S");
        
        return listaMedicamentos;
		
    }
    
	@Override
	public void crearCatalogoMedicamentosFinanciados(String catalogo) 
	{			
		this.borrar();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		/* Lista donde almacenamos todas las entidades Medicamento que vamos a escribir en el Data Store */
		List<MedicamentoAbstracto> listaMedicamentos = new ArrayList<MedicamentoAbstracto>();
		listaMedicamentos = leerCSV(catalogo);
		
		/* Creamos la entidad de tipo CatalogoMedicamentos por adelantado */
		CatalogoMedicamentos catalogoMedicamentos = new CatalogoMedicamentos();
		catalogoMedicamentos.setFecha(new Date());
		String name = "catalogo-nacional";
		//catalogoMedicamentos.setName(name);
		
		/* Creamos el lector de CSV. Le pasamos como argumento de entrada el catalogo */
    	StringReader stringReader = new StringReader(catalogo);
    	CSVReader reader = new CSVReader(stringReader);
        String [] linea;
        
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(CatalogoMedicamentos.class.getSimpleName(), name);
       
		boolean exito = false;
		int retries = 5;
		while(retries>0 && !exito)
		{
			try
			{
				tx.begin();
				
				/* Persistimos la entidad CatalogoMedicamentos */
				catalogoMedicamentos.setNumMedicamentos(listaMedicamentos.size());
				pm.makePersistent(catalogoMedicamentos);
				
				for(MedicamentoAbstracto m : listaMedicamentos){
					String stringKey = KeyFactory.createKeyString(keyBuilder.getKey(), Medicamento.class.getSimpleName(), m.getCodigoNacional());
					System.out.println(stringKey);
					m.setEncodedKey(stringKey);
					}
				
				/* Persistimos los medicamentos que forman el catalogo */
				pm.makePersistentAll(listaMedicamentos);
				
				tx.commit();
				exito = true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				retries--;
				log.log(Level.SEVERE, "Creando catalogo de medicamentos: "+e);
			}
			finally
			{
				if(tx.isActive())
					tx.rollback();
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		if(exito){
			log.log(Level.INFO, "Creando catalogo de medicamentos. Ejecucion exitosa");
		}
		else{
			log.log(Level.SEVERE, "Creando catalogo de medicamentos. Ejecucion fallida");
		}
	}
	
	public void borrar()
	{
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
	
		com.google.appengine.api.datastore.Query  q = new com.google.appengine.api.datastore.Query("Medicamento");
		
		PreparedQuery pq = dataStore.prepare(q);
		for(Entity e: pq.asIterable())
			dataStore.delete(e.getKey());
	}
	
	@Override
	public int test(){
		
		int num = 0;
		System.out.println("test");
		/*int num = 0;
		
		List<Medicamento> listaMedicamento = new ArrayList<Medicamento>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery(Medicamento.class);
		q.setFilter("parentKey == parentKeyParam");


		try 
		{ 
		  List<Medicamento> resultsFinanciados = (List<Medicamento>) q.execute("catalogo_nacional");
		  if (!resultsFinanciados.isEmpty()) 
		  {
			  for(Medicamento m : resultsFinanciados)
				  if(m!=null)
					  listaMedicamento.add(m);
			  num = resultsFinanciados.size();
		  } 
		  else
			  log.info("catalogo vacio");
		  
		}
		finally 
		{
		  q.closeAll();
		  pm.close();
		}*/
		
		
		return num;
	}
	
	public List<MedicamentoAbstracto> getCatalogoMedicamentos()
	{
		List<MedicamentoAbstracto> listaMedicamento = new ArrayList<MedicamentoAbstracto>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query qFinanciados = pm.newQuery(Medicamento.class);
		//qFinanciados.setFilter("parentKey == parentKeyParam");
		//qFinanciados.declareParameters("String parentKeyParam");
		
		Query qNoFinanciados = pm.newQuery(Medicamento.class);
		qNoFinanciados.setFilter("parentKey == parentKeyParam");
		qNoFinanciados.declareParameters("String parentKeyParam");
		
		try 
		{ 
		  List<MedicamentoAbstracto> resultsFinanciados = (List<MedicamentoAbstracto>) qFinanciados.execute();
		  if (!resultsFinanciados.isEmpty()) 
		  {
			  for(MedicamentoAbstracto m : resultsFinanciados)
				  if(m!=null)
					  listaMedicamento.add(m);
		  } 
		  else
			  log.info("catalogo vacio");
		  
		  List<MedicamentoAbstracto> resultsNoFinanciados = (List<MedicamentoAbstracto>) qNoFinanciados.execute("catalogo_nacional");
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
	
	@Override
 	public MedicamentoFarmacia crearMedicamentoFarmacia(List<String> data)
	{	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		MedicamentoFarmacia medicamentoFarmacia = new MedicamentoFarmacia();
		medicamentoFarmacia.setCodigoNacional(data.get(0));
		medicamentoFarmacia.setStockMinimo(Integer.parseInt(data.get(1)));
		medicamentoFarmacia.setStockMaximo(Integer.parseInt(data.get(2)));
		medicamentoFarmacia.setContadorVentas(0);
		
		boolean exito = false;
		int retries = MAX_RETRIES;
		while(retries>=0 && !exito)
		{
			try
			{
				tx.begin();
				
				/* Si el MedicamentoFarmacia ya existe, no lo añadimos a la BBDD*/
				try{
					MedicamentoFarmacia aux = pm.getObjectById(MedicamentoFarmacia.class, medicamentoFarmacia.getCodigoNacional());
				}
				catch(JDOObjectNotFoundException e){
					retries = 0;
					log.log(Level.INFO, "Server --> crearMedicamento(); la entidad MedicamentoFarmacia con codigo nacional "
							+medicamentoFarmacia.getCodigoNacional() +" ya existe en la BBDD. "+e);
				}

				pm.makePersistent(medicamentoFarmacia);
				tx.commit();
				exito = true;
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE, "Server --> crearMedicamento(); "+e);
				System.out.println("Server --> crearMedicamento(); "+e);
				
				retries--;
			}
			finally
			{
				if(tx.isActive())
					tx.rollback();
				
				/* Tras un pequeño sleep reintentamos */
				if(retries!=0)
				{
					try {
						Thread.sleep(SLEEP_TIME_MILLIS);
					} 
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		pm.close();
		if(exito)
		{
			log.log(Level.INFO, "SERVER --> crearMedicamentoFarmacias(); ejecucion exitosa");
		}
				
		System.out.println("Server me llega" +data.get(0));
		
		return null;
	}
	
	/*@Override
	public boolean esMedicamentoNuevo(String codigoNacional) 
	{	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery(MedicamentoFarmacia.class);
		q.setFilter("codigoNacional == codigoNacionalParam");
		q.declareParameters("String codigoNacionalParam");
		
		boolean esNuevo = false;
		try 
		{
		  List<MedicamentoFarmacia> results = (List<MedicamentoFarmacia>) q.execute(codigoNacional);
		  if (results.isEmpty()) 
		  {
			  esNuevo = true;
		  } 
		  else
		  {
			esNuevo = false;
		  }
		}
		finally 
		{
		  q.closeAll();
		  pm.close();
		}
	
		return esNuevo;
	}*/

	public MedicamentoAbstracto getMedicamento(String codigoNacional)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		MedicamentoAbstracto medicamento = new MedicamentoAbstracto();
		
		boolean encontrado = false;
		/* Buscamos el medicamento tanto en MedicamentoFinanciado como MedicamentoNoFinanciado */
		try 
		{   
			/* Primero buscamos en MedicamentoFinanciado */
			medicamento = pm.getObjectById(Medicamento.class, codigoNacional);
			if(medicamento!=null)
			{
				log.log(Level.INFO, "Server --> getMedicamento(); medicamento con codigo "+codigoNacional +" encontrado"
						+ "en el catalogo de medicamentos financiados");
				encontrado = true;
				medicamento.setExiste(true);
				medicamento.setExisteMedicamentoFarmacia(false);
			}
			else
			{	
				/* Si no lo hemos encontrado pasamos a buscar en MedicamentoNoFinanciado */
				medicamento = pm.getObjectById(Medicamento.class, codigoNacional);
				if(medicamento!=null)
				{
					log.log(Level.INFO, "Server --> getMedicamento(); medicamento con codigo "+codigoNacional +" encontrado"
							+ "en el catalogo de medicamentos NO financiados");
					encontrado = true;
					medicamento.setExiste(true);
					medicamento.setExisteMedicamentoFarmacia(false);
				}
			}
			
			/* Si el medicamento se encuentra en alguno de los dos catalogos, comprobamos si tambien existe
			 * como MedicamentoFarmacia
			 */
			if(encontrado)
			{
				MedicamentoFarmacia mf = pm.getObjectById(MedicamentoFarmacia.class, codigoNacional);
				if(mf!=null)
					medicamento.setExisteMedicamentoFarmacia(true);
				else medicamento.setExisteMedicamentoFarmacia(false);
			}
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "Server --> getMedicamento(); "+e);
		}
		finally
		{
		  pm.close();
		}

		if(encontrado)
		{
			MedicamentoAbstracto ma = pm.detachCopy(medicamento);
			return ma;
		}
		else
		{
			medicamento = new MedicamentoAbstracto();
			medicamento.setExiste(false);
			medicamento.setExisteMedicamentoFarmacia(false);
			return medicamento;
		}
	}
	
	@Override
	public List<MultiWordSuggestion> getSuggestions(Request request)
	{
		if(oracle.isEmpty()){
	    	List<String> catalogoNombres = ContextListener.getNombresCatalogoNacional(getServletContext());
	    	oracle.addAll(catalogoNombres);
		}
    	List<MultiWordSuggestion> listaSugerencias = oracle.requestSuggestions(request);

    	log.info("Encontradas --> "+listaSugerencias.size()+" sugerencias para la busqueda --> all"+request.getQuery());
		return listaSugerencias;
	}
	

	@Override
	public GetTableWithCursor getPorcionCatalogo(int rango)
	{
		GetTableWithCursor retorno = new GetTableWithCursor();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Medicamento.class);
		q.setRange(rango, rango+100);
		
		List<Medicamento> results = (List<Medicamento>) q.execute();
		
		if(!results.isEmpty())
		{
			for(Medicamento m : results)
				retorno.getListaMedicamento().add(m);
			retorno.setTotal(24234);
			System.out.println("server tam "+retorno.getListaMedicamento().size());
			return retorno;
		}
		
		return null;
	}

	public int getPositionInCatalogue(String nombre)
	{
		int posicion = -1;
		
		List<String> lista = ContextListener.getNombresCatalogoNacional(getServletContext());
		for(int i=0; i<lista.size() ; i++)
			if(lista.get(i).equals(nombre))
				posicion = i;
		System.out.println("Server posiccion "+posicion);
		return posicion;
	}
}










