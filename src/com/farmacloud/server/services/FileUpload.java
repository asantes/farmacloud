package com.farmacloud.server.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.FetchGroup;
import javax.jdo.FetchPlan;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.farmacloud.server.utiles.MyProgressListener;
import com.farmacloud.server.utiles.PMF;
import com.farmacloud.shared.model.CatalogoMedicamentos;
import com.farmacloud.shared.model.Farmacia;
import com.farmacloud.shared.model.Medicamento;
import com.farmacloud.shared.model.Proveedor;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.inject.Singleton;
import com.opencsv.CSVReader;

@Singleton
public class FileUpload extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2059425049675751119L;
	public static final int MAX_RETRIES = 5;
	public static final int SLEEP_TIME_MILLIS = 100;
	static final Logger log = Logger.getLogger(FileUpload.class.getName());
	
	public FileUpload(){
	}
	
    public void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException 
    {
    	PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
    	boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    	if(isMultipart)
    	{
	        ServletFileUpload upload = new ServletFileUpload();
	        MyProgressListener testProgressListener = new MyProgressListener();
	        upload.setProgressListener(testProgressListener);
	        
	        Subject currentUser = SecurityUtils.getSubject();
			currentUser.getSession().setAttribute("progressListener", testProgressListener);

			FileItemIterator iterator = null;
			try {
				iterator = upload.getItemIterator(request);
			} 
			catch (FileUploadException e) {
				log.log(Level.SEVERE, ""+e.toString());
				out.write("332:Fichero incompatible");
			}

			try 
			{
				while (iterator.hasNext()) 
				{
					log.info("tratanto con el primer elemento. Y deberia ser el unico");
				    FileItemStream item = iterator.next();
				    if (!item.isFormField()) 
				    {
				    	/* Atributos del fichero subido */
				        String fieldName = item.getFieldName();
				        String fileName = item.getName();
				        String contentType = item.getContentType();
				       // long sizeInBytes = item.getSize();
				    	log.log(Level.INFO, "recibiendo fichero");
				    	log.log(Level.INFO, "\nfieldName: \t"+fieldName+
				    						"\nfileName: \t"+fileName+
				    						"\ncontentType: \t"+contentType);
				    	
				    	
						String catalogo = IOUtils.toString(new InputStreamReader(item.openStream(), Charset.defaultCharset()));
						if(catalogo!=null)
						{
							//System.out.println(catalogo);
							/* Leemos el fichero CSV 
							List<MedicamentoAbstracto> listaMedicamentos = leerCSV(catalogo);
							if(!listaMedicamentos.isEmpty()  && fileName!=null)
								crearCatalogoMedicamentos(listaMedicamentos, fieldName); */
						}
					    response.setContentType("text/html");				
					 }	 
				    else{
				    	log.log(Level.WARNING, "hemos recibido un form en lugar de un fichero...");
				    	out.write("332:Fichero incompatible");
				    	log.info("Tenemos --> "+item.getFieldName()+IOUtils.toString(new InputStreamReader(item.openStream(), Charset.defaultCharset())));
				    }
				}
			} 
			catch (FileUploadException e) {
				log.log(Level.SEVERE, ""+e.toString());
				out.write("332:Error al procesar");
			}
    	}
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
	        	medicamento = new Medicamento();
				/* Cada linea se guarda en un array de String. Cada String del array contendra
				 * un campo de la linea
				 */
				
				/* Vamos creando entidades y añadiendolas a una lista */
				medicamento = new Medicamento();
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
        	log.info("Se han leido "+listaMedicamentos.size()+" entradas");	
        else
        	log.info("No se ha leido ninguna entrada :S");
        
        return listaMedicamentos;
    }
    
	public void crearCatalogoMedicamentos(List<MedicamentoAbstracto> listaMedicamentos, String fieldName) 
	{			
		Farmacia farmacia = obtenerFarmacia(fieldName);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		/* Creamos la entidad de tipo CatalogoMedicamentos por adelantado */
		CatalogoMedicamentos catalogoMedicamentos = farmacia.getCatalogo();
		catalogoMedicamentos.setFecha(new Date());
		catalogoMedicamentos.setKeyName("catalogo-farmacia");
		farmacia.setCatalogo(catalogoMedicamentos);
		
		/* Creamos una clave a partir de la entidad padre y de su id */
		KeyFactory.Builder keyBuilderMed = new KeyFactory.Builder(CatalogoMedicamentos.class.getSimpleName(), catalogoMedicamentos.getKeyName());
		 
		boolean exito = false;
		int retries = MAX_RETRIES;
		while(retries>0 && !exito)
		{
			try
			{
				tx.begin();
				
				/* Persistimos la entidad CatalogoMedicamentos */
				catalogoMedicamentos.setNumMedicamentos(listaMedicamentos.size());
				pm.makePersistent(farmacia);
				
				for(MedicamentoAbstracto m : listaMedicamentos)
				{
					/* Utilizamos la clave creada anteriormente para crear la clave del hijo. Se crea con la entidad hija y su id mas la clave del padre creada anteriormete */
					String stringKey = KeyFactory.createKeyString(keyBuilderMed.getKey(), Medicamento.class.getSimpleName(), m.getCodigoNacional());
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
				int intento = MAX_RETRIES - retries;
				log.log(Level.SEVERE,"Intento numero "+intento+"\n"+e);
			}
			finally
			{
				if(tx.isActive())
					tx.rollback();
				
				try {
					Thread.sleep(SLEEP_TIME_MILLIS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	
		pm.close();
		
		if(exito){
			log.log(Level.INFO, "Exito");
		}
		else{
			log.log(Level.SEVERE, "Fallo");
		}
	}
	
	public void borrar()
	{
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query  q = new com.google.appengine.api.datastore.Query("Medicamento");
		
		PreparedQuery pq = dataStore.prepare(q);
		for(Entity e: pq.asIterable())
			dataStore.delete(e.getKey());;
	}
	
	public Farmacia obtenerFarmacia(Object primaryKey)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		FetchPlan fp = pm.getFetchPlan();
		FetchGroup fg = pm.getFetchGroup(Farmacia.class, "completo");
		fg.addMember("catalogo");;
		fp.addGroup("completo");
		Farmacia entidad = null;
		Farmacia entidadDetached = null;
		
		try
		{
			entidad = pm.getObjectById(Farmacia.class, primaryKey);
			entidadDetached = pm.detachCopy(entidad);
			log.log(Level.INFO, "encontrada una entidad con clave primaria "+primaryKey +" en el tipo Farmacia");
		}
		catch(JDOObjectNotFoundException e){
			log.log(Level.INFO, "no existe ninguna entidad con clave primaria "+primaryKey +" en el tipo Farmacia");
		}
		finally
		{
			pm.close();
		}
		
		return entidadDetached;
	}
}
