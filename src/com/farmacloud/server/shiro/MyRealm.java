package com.farmacloud.server.shiro;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.FetchGroup;
import javax.jdo.FetchPlan;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.farmacloud.server.utiles.PMF;
import com.farmacloud.shared.Utiles;
import com.farmacloud.shared.model.Usuario;
import com.farmacloud.shared.model.UsuarioPharma;
import com.farmacloud.shared.model.abstracts.UsuarioAbstracto;

public class MyRealm extends IniRealm
{
    private static Logger log = Logger.getLogger(MyRealm.class.getName());

    Section usersSection;
    Section rolesSection;
    
    public MyRealm(Ini ini)  
    {
		super(ini);
		log.info("Soy el MyRealm");
		usersSection = ini.getSection(USERS_SECTION_NAME);
		rolesSection = ini.getSection(ROLES_SECTION_NAME);
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) 
	{
		String username = (String) principalCollection.getPrimaryPrincipal();
		if(username==null)
		{
			log.info("Subject anonimo");
			Set<String> roles = new HashSet<String>();
			roles.add(Utiles.Roles.ANONYMOUS);
			return new SimpleAuthorizationInfo(roles);
		}
        UsuarioAbstracto usuario = obtenerUsuario(username);
        if(usuario != null)
        {
     		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(usuario.getRoles());
    		return info;
        }
        else
        {
        	log.info("No existe un usuario con tales credenciales en la BBDDD. "+
        			"Username --> "+username);
        	Set<String> roles = new HashSet<String>();
        	
    	   	String values = usersSection.get(username);
        	if(values!=null)
        	{
	        	if(!values.equals(""))
	        	{
	        		String[] contraseñaRol = values.split(",");
	        		for (int i = 1; i < contraseñaRol.length; i++)  //En la posicion i=1 esta el password, de ahi en adelante estan los roles
		        		roles.add(contraseñaRol[i]);
	        	}
        	}
    		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
    		return info; 
        }
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) 
			throws AuthenticationException
	{
        final UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        if (usernamePasswordToken.getUsername() == null || usernamePasswordToken.getUsername().isEmpty()) {
            throw new AuthenticationException("Authentication failed");
        }
        
        // Find the thing that stores your user's credentials.  This may be the same or different than
        // the thing that stores the roles.
        UsuarioAbstracto usuario = obtenerUsuario(usernamePasswordToken.getUsername());
        if(usuario==null)
        {
        	log.info("No existe un usuario con tales credenciales en la BBDD. "+
        			"Username --> "+usernamePasswordToken.getUsername());
        	
        	/* Comprobamos si existe alguna entrada en el fichero de configuracion shiro.ini que coincide con los credenciales recibidos */
        	String values = usersSection.get(usernamePasswordToken.getUsername());
        	if(values!=null){
		    	if(!values.equals(""))
		    	{
		        	String[] contraseñaRol = values.split(",");
		        	String contraseña = contraseñaRol[0];
		    		String hashedPasswordBase64 = new Sha256Hash(contraseña, "", 1024).toBase64(); //Sin salt. En el fichero no esta salteada ni nada
		    		SaltedAuthenticationInfo info = new MySaltedAuthenticationInfo(usernamePasswordToken.getUsername(), hashedPasswordBase64, "", getName());
		    		log.info("Si existe un usuario con tales credenciales en el fichero de configuracion shiro.ini. Username --> "+usernamePasswordToken.getUsername());
		 
		        		return info;
		        	}
        	}
        	throw new AuthenticationException("Authentication failed: usuario no identificado");
        }

        SaltedAuthenticationInfo info = new MySaltedAuthenticationInfo(usuario.getNameUser(), usuario.getPassword(), usuario.getSalt(), getName());
		return info;
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
			log.log(Level.INFO, "encontrada una ninguna entidad con clave primaria "+primaryKey +" en el tipo Usuario");
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
}
