package com.farmacloud.server.services;

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
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;

import com.farmacloud.client.services.ServicioGestionFarmacia;
import com.farmacloud.server.utiles.PMF;
import com.farmacloud.server.utiles.Utiles;
import com.farmacloud.shared.model.ContadorNoticias;
import com.farmacloud.shared.model.Noticia;
import com.farmacloud.shared.model.UsuarioPharma;
import com.farmacloud.shared.model.DTO.NoticiaDTO;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.google.inject.Singleton;

@SuppressWarnings("serial")
@Singleton
public class ImpServicioGestionFarmacia extends XsrfProtectedServiceServlet implements ServicioGestionFarmacia
{
	public static final Logger log = Logger.getLogger(ImpServicioGestionFarmacia.class.getName());
	public static final int MAX_RETRIES = 5;
	public static final int SLEEP_TIME_MILLIS = 100;
	
	public  ImpServicioGestionFarmacia() {
	}
	
	@Override
	@RequiresRoles("pharma")
	public boolean añadirNoticia(byte[] cuerpoNoticia, String titularNoticia) 
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean exito = false;
		int retries = MAX_RETRIES;
		
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();

		if(true)
		{
			KeyFactory.Builder keyBuilder = new KeyFactory.Builder(UsuarioPharma.class.getSimpleName(), principal);
			
			while(retries>=0 && !exito)
			{
				try
				{
					tx.begin();
					
					/* Si se produce algun error al obtener el contador, decrementamos el numero de intentos y se
					 * vuelve a intentar de nuevo 
					 */
					ContadorNoticias contadorNoticias = obtenerContadorNoticias();
					if(contadorNoticias!=null)
					{
						Noticia noticia = new Noticia();
						noticia.setTitular(titularNoticia);
						noticia.setAutor(principal);
						noticia.setFecha(new Date());
						Blob blob = new Blob(cuerpoNoticia);
						noticia.setCuerpoCrudo(blob);
						String stringKey = KeyFactory.createKeyString(keyBuilder.getKey(), Noticia.class.getSimpleName(), noticia.getTitular());
						noticia.setEncodedKey(stringKey);
						
						pm.makePersistent(noticia);
						/* Incrementamos el contador y lo persistimos */
						contadorNoticias.setContador(contadorNoticias.getContador()+1);
						pm.makePersistent(contadorNoticias);
						
						tx.commit();
						exito = true;
					}
					else retries--;
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE, ""+e);
					retries--;
				}
				
				finally
				{
					if(tx.isActive())
						tx.rollback();
					
					if(retries!=0 && !exito)
						Utiles.duerme(this.getClass().getName(), SLEEP_TIME_MILLIS);
				}
			}
		}
			
		if(exito){
			log.log(Level.INFO, "ejecucion exitosa");
		}
		
		return exito;
	}

	/* Devuelve null si no encuentra noticias */
	@Override
	@RequiresRoles("pharma")
	public List<NoticiaDTO> obtenerNoticias(int numNoticias, int numPag)
	{		
		
		List<NoticiaDTO> listaNoticiaDTO = null;
		if(numPag>=1)
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();

			ContadorNoticias cn = null;
			cn = obtenerContadorNoticias();
			
			if(cn!=null)
			{
				listaNoticiaDTO = new ArrayList<NoticiaDTO>();
				
				/* Obtenemos las noticias en orden descendente respecto a la fecha. Obtenemos
				 * tantas noticias como queramos mostrar en una pagina. Ni mas ni menos */
				Query q = pm.newQuery(Noticia.class);
				q.setOrdering("fecha desc");
				//if(numPag==0)
					//q.setRange((numPag*numNoticias)-numNoticias, 3);
				//else
				q.setRange((numPag*numNoticias)-numNoticias, numPag*numNoticias);
				
				List<Noticia >listaNoticia = (List<Noticia>) q.execute();
				if(!listaNoticia.isEmpty())
				{
					System.out.println("he obtenido "+listaNoticia.size());
					
					/* Pasamos las Noticia del modelo a un NoticiaDTO debido a un campo en Noticia no
					 * compatible con el cliente
					 */
					for(Noticia n : listaNoticia)
					{
						NoticiaDTO aux = new NoticiaDTO();	
						Noticia noticiaDetached = pm.detachCopy(n);
						aux = noticiaDetached.toDTO();
						if(aux==null)
						{
							log.log(Level.WARNING, "error al obtener la NoticiaDTO");
						}
						else {
							aux.setContador(cn.getContador());
							listaNoticiaDTO.add(aux);
						}
					}
				}
				else{
					log.info("sin Noticias en el rango indicado");
				}
			}
		}

		return listaNoticiaDTO;
	}
		
	/* Devuelve null en caso de no obtener la entidad o no ser capaz de persistirla */
	@SuppressWarnings("unchecked")
	@RequiresRoles("pharma")
	private ContadorNoticias obtenerContadorNoticias()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		ContadorNoticias cn = null;
		ContadorNoticias cnDetached = null;
		
		boolean exito = false;
		int retries = MAX_RETRIES;
		
		while(!exito && retries>=0)
		{			
			try
			{
				tx.begin();
				
				/* Sino existe la entidad, la persistimos */
				try{
					cn = (ContadorNoticias) pm.getObjectById(ContadorNoticias.class, ContadorNoticias.clavePrimaria);
					cnDetached = pm.detachCopy(cn);
					log.log(Level.INFO, "entidad obtenida");
				}
				catch(Exception e)
				{
					/* Obtenemos todas las Noticias almacenadas en la base de datos */
					List<Noticia> lista;
					Query q = pm.newQuery(Noticia.class);
					lista = (List<Noticia>) q.execute();
					cn = new ContadorNoticias();
					cn.setName(ContadorNoticias.clavePrimaria);
					
					/* Si no habia noticias el contador sera 0 */
					if(lista==null)
						cn.setContador(0);
					else cn.setContador(lista.size());
					
					pm.makePersistent(cn);
					log.log(Level.INFO, "entidad persistida"); 
					cnDetached = pm.detachCopy(cn);
				}
				
				/* Si la entidad existe, hemos acabado */
				tx.commit();
				exito = true;
				
			}
			catch(Exception e)
			{
				log.log(Level.INFO, ""+e);
				retries--;
			}
			finally
			{
				if(tx.isActive())
					tx.rollback();
				
				if(retries!=0 && !exito)
					Utiles.duerme(this.getClass().getName(), SLEEP_TIME_MILLIS);
			}
		}
		
		pm.close();
		
		if(exito)
			log.log(Level.INFO, "ejecucion exitosa");
		
		return cnDetached;
	}
	
	@RequiresRoles("pharma")
	public Noticia getNoticia(Object primaryKey)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Noticia entidad = null;
		Noticia entidadDetached = null;
		
		try
		{
			entidad = pm.getObjectById(Noticia.class, primaryKey);
			entidadDetached = pm.detachCopy(entidad);
			log.log(Level.INFO, "encontrada una entidad con clave primaria "+primaryKey);
		}
		catch(JDOObjectNotFoundException e){
			log.log(Level.INFO, "no existe ninguna entidad con clave primaria "+primaryKey);
		}
		finally
		{
			pm.close();
		}
		
		return entidadDetached;
	}
}
