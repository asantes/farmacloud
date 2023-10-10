package com.farmacloud.server.services;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.FetchGroup;
import javax.jdo.FetchPlan;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;

import com.farmacloud.client.services.ServicioUsuario;
import com.farmacloud.server.utiles.PMF;
import com.farmacloud.shared.Utiles;
import com.farmacloud.shared.model.Usuario;
import com.farmacloud.shared.model.UsuarioPharma;
import com.farmacloud.shared.model.DTO.UserRoleDTO;
import com.farmacloud.shared.model.abstracts.UsuarioAbstracto;
import com.farmacloud.shared.validation.Credenciales;
import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.google.inject.Singleton;

@SuppressWarnings("serial")
@Singleton
public class ImpServicioUsuario extends XsrfProtectedServiceServlet implements ServicioUsuario
{
	Validator validator;
	public static final Logger log = Logger.getLogger(ImpServicioUsuario.class.getName());
	public static final int MAX_RETRIES = 5;
	public static final int SLEEP_TIME_MILLIS = 100;
	
	public ImpServicioUsuario() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	} 
	
	private <T> boolean validate(T obj)
	{
		Set<ConstraintViolation<T>> violations = validator.validate(obj, Default.class);
		if (violations.size() > 0)
		{
			log.info("se han encontrado violaciones al validar");
			for(ConstraintViolation<T> c : violations)
			{
				log.info("   "+c.getRootBeanClass().toString()+"\n\t\t"+
						 "message: "+c.getMessage()+"\n\t\t"+
						 "invalid value: "+c.getInvalidValue().toString());
			}
			return false;
		}
		else{
			log.info("sin errores validando "+obj.getClass().getSimpleName());
			return true;
		}	
	}
	
	@Override
	public void crearUsuario(UsuarioAbstracto _usuario)
	{	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean usuarioExistente = false;
		
		boolean exito = false;
		int retries = MAX_RETRIES;
		while(retries>=0 && !exito)
		{
			try
			{
				tx.begin();
				
				Usuario usuarioAux = obtenerUsuarioCliente(_usuario.getNameUser());
				if(usuarioAux==null)
				{
					UsuarioPharma usuarioPharma = obtenerUsuarioPharma(_usuario.getNameUser());
					if(usuarioPharma!=null)
						usuarioExistente = true;
				}
				
				if(!usuarioExistente)
				{
					log.log(Level.INFO, "creando usuario con nombre "+_usuario.getNameUser());
					
					//We'll use a Random Number Generator to generate salts.  This
					//is much more secure than using a username as a salt or not
					//having a salt at all.  Shiro makes this easy.
					//
					//Note that a normal app would reference an attribute rather
					//than create a new RNG every time:
					RandomNumberGenerator rng = new SecureRandomNumberGenerator();
					Object salt = rng.nextBytes();

					//Now hash the plain-text password with the random salt and multiple
					//iterations and then Base64-encode the value (requires less space than Hex):
					String hashedPasswordBase64 = new Sha256Hash(_usuario.getPassword(), salt, 1024).toBase64();
				
					//save the salt with the new account.  The HashedCredentialsMatcher
					//will need it later when handling login attempts:

	
					//String hash = BCrypt.hashpw(_usuario.getPassword(), BCrypt.gensalt()); //Esto era el hash que utilizabamos antes
					_usuario.setPassword(hashedPasswordBase64);
					_usuario.setSalt(salt.toString());
					
					if(_usuario instanceof UsuarioPharma)
						log.log(Level.INFO, "creando usuario tipo UsuarioPharma con nombre "+_usuario.getNameUser());
					
					if(_usuario instanceof Usuario)
						log.log(Level.INFO, "creando usuario tipo Usuario con nombre "+_usuario.getNameUser());
					
					Set<String> roles = new HashSet<String>();
					roles.add("pharma");
					_usuario.setRoles(roles);
					pm.makePersistent(_usuario);
				}
				else
				{
					log.log(Level.INFO, "ya existe una entidad registrada con clave primaria "+_usuario.getNameUser() +" en el sistema");
				}
				
				tx.commit();
				exito = true;
			}
			catch(ConcurrentModificationException e)
			{
				log.log(Level.SEVERE, "Server --> crearUsuario(); "+e);
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
		
		if(exito)
			log.log(Level.INFO, "Server --> crearUsuario(); ejecucion exitosa");
		
		pm.close();
	}

	@Override
	public UserRoleDTO login(Credenciales creds, boolean remember)
	{
		UserRoleDTO userRole = null;
		
		if(validate(creds))
		{
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isAuthenticated())
			{
				String username = creds.getUsuario();
				String password = creds.getContraseña();
		        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		        
		        if(remember)
		        {
		        	token.setRememberMe(true);
		        	log.info("Solicitado login con recuerda");
		        }
		        
		        try{
		        	currentUser.login(token);
		        }
		        catch(AuthenticationException e){
		        	log.info(e.getMessage());
		        }
		        
		        if(currentUser.isAuthenticated()) /* Si el login ha tenido exito, obtenemos el usuario de la BBDD para mandarselo al cliente*/
		        {
		        	userRole = getUserRole(currentUser);
		            log.info("Usuario --> "+creds.getUsuario()+" autenticado correctamente con roles "+userRole.toString());	
		        }
			}
			else{
				log.info("Usuario ya autenticado en el sistema :S Lo echamos por comportamiento extraño");
				currentUser.logout();
			}
		}

        return userRole;
	}
	
	@Override
	public void logout()
	{
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isAuthenticated() || currentUser.isRemembered())
		{
	    	String primaryPrincipal = (String) currentUser.getPrincipal();
	    	if(primaryPrincipal!=null)
	    	{
	    		if(currentUser.isAuthenticated())
	    			log.info("Logout del usuario --> "+primaryPrincipal+". Abandonando su estado de autenticado");
	    		else if(currentUser.isRemembered())
	    				log.info("Logout del usuario --> "+primaryPrincipal+". Abandonando su estado de recordado");
	    	}
		}
		else
			log.info("Se ha solicitado logout de un subject inactivo en el sistema :S");
		
        currentUser.logout();
	}
	
	private UserRoleDTO getUserRole(Subject currentUser)
	{
		UserRoleDTO userRole = null;
		
		String principal = (String) currentUser.getPrincipal();
		if(principal!=null)
		{
			userRole = new UserRoleDTO();
			userRole.setPrincipal(principal);
			if(currentUser.hasRole(Utiles.Roles.PHARMA)){
				userRole.getRoles().add(Utiles.Roles.PHARMA);
			}
			if(currentUser.hasRole(Utiles.Roles.ADMIN)){
				userRole.getRoles().add(Utiles.Roles.ADMIN);
			}
		}
     
       return userRole;
	}
	
	@Override
	public UserRoleDTO isLoggedIn()
	{
		UserRoleDTO userRole = null;
	    Subject currentUser = SecurityUtils.getSubject();
	    
	    if(currentUser.isAuthenticated())
	    {
	    	userRole = getUserRole(currentUser);
	    	log.info("Usuario --> "+userRole.getPrincipal()+" autenticado en el sistema");
	    }
	    else {
	    	log.info("Cliente no autenticado en el sistema");
	    }
	
	    return userRole;
	}
	
	@Override
	public UserRoleDTO isRemembered()
	{
		UserRoleDTO userRole = null;
	    Subject currentUser = SecurityUtils.getSubject();
	    
	    if(currentUser.isRemembered())
	    {
	    	userRole = getUserRole(currentUser);
	    	log.info("Usuario --> "+userRole.getPrincipal()+" recordado en el sistema"); 	
	    }
	    else {
	    	log.info("Cliente no recordado en el sistema");
	    }
	    
	    return userRole;
	}
	
	@Override
	public UserRoleDTO isActive()
	{
		UserRoleDTO userRole = null;
		
	    /* El usuario estará activo en el sistema tanto si está autenticado como recordado */
    	userRole = isRemembered();
    	if(userRole==null){
    		userRole = isLoggedIn();
    	}
	    
	    return userRole;
	}
	
	@Override
	public void dumbRequest()
	{  
	    HttpSession sesion = getThreadLocalRequest().getSession(false);
		if(sesion!=null)
		{
			Cookie[] coo = getThreadLocalRequest().getCookies();
			int i =1;
			if(coo!=null)
			{
				for(Cookie c: coo)
				{
					log.info("Cookie numero "+i +" : " +c.getValue());
					i++;
				}
			}
		}
		else
			log.info("El subject no tiene sesion");
	}

	public UsuarioAbstracto obtenerUsuario(Object primaryKey)
	{
		UsuarioAbstracto usuario = null;
		usuario = obtenerUsuarioCliente(primaryKey);
		if(usuario==null)
			usuario = obtenerUsuarioPharma(primaryKey);
		
		return usuario;	
	}
	
	public Usuario obtenerUsuarioCliente(Object primaryKey)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		FetchPlan fp = pm.getFetchPlan();
		FetchGroup fg = pm.getFetchGroup(Usuario.class, "completo");
		fg.addMember("sesionTemporal");
		fg.addMember("sesionRecordar");
		fp.addGroup("completo");
		Usuario entidad = null;
		Usuario entidadDetached = null;
		
		try
		{
			entidad = pm.getObjectById(Usuario.class, primaryKey);
			entidadDetached = pm.detachCopy(entidad);
			log.log(Level.INFO, "encontrada una entidad con clave primaria "+primaryKey +" en el tipo Usuario");
		}
		catch(JDOObjectNotFoundException e){
			log.log(Level.INFO, "no existe ninguna entidad con clave primaria "+primaryKey +" en el tipo Usuario");
		}
		finally
		{
			pm.close();
		}
		
		return entidadDetached;
	}
	
	public UsuarioPharma obtenerUsuarioPharma(Object primaryKey)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		FetchPlan fp = pm.getFetchPlan();
		FetchGroup fg = pm.getFetchGroup(UsuarioPharma.class, "completo");
		fg.addMember("sesionTemporal");
		fg.addMember("sesionRecordar");
		fp.addGroup("completo");
		UsuarioPharma entidad = null;
		UsuarioPharma entidadDetached = null;
		
		try
		{
			entidad = pm.getObjectById(UsuarioPharma.class, primaryKey);
			entidadDetached = pm.detachCopy(entidad);
			log.log(Level.INFO, "encontrada una entidad con clave primaria "+primaryKey +" en el tipo UsuarioPharma");
		}
		catch(JDOObjectNotFoundException e){
			log.log(Level.INFO, "no existe ninguna entidad con clave primaria "+primaryKey +" en el tipo UsuarioPharma");
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
			log.log(Level.SEVERE, "durmiendo");
		}
	}
	
}
