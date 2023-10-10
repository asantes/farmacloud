package com.farmacloud.server.shiro.guice;

import javax.servlet.ServletContext;

import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.text.IniRealm;

import com.farmacloud.server.shiro.MyRealm;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;

@SuppressWarnings("deprecation")
public class MyShiroWebModule extends ShiroWebModule 
{
    MyShiroWebModule(ServletContext sc) {
        super(sc);
    }
    
    protected void configureShiroWeb() 
    {
    	bindRealm().to(IniRealm.class);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(RequiresRoles.class), new ShiroMethodInterceptor());
    	bindInterceptor(Matchers.any(), Matchers.annotatedWith(RequiresAuthentication.class), new ShiroMethodInterceptor());
    	bindInterceptor(Matchers.any(), Matchers.annotatedWith(RequiresPermissions.class), new ShiroMethodInterceptor());	
    }

	@Provides
    @Singleton
    Ini loadShiroIni() {
        return Ini.fromResourcePath("file:WEB-INF/shiro.ini");
    }
    
	@Provides
    @Singleton
    IniRealm loadIniRealm(Ini ini) 
    {	
    		MyRealm myRealm = new MyRealm(ini);
	    	Sha256CredentialsMatcher credentialsMatcher = new Sha256CredentialsMatcher();
	    	credentialsMatcher.setHashIterations(1024);
	    	credentialsMatcher.setStoredCredentialsHexEncoded(false);
	    	myRealm.setCredentialsMatcher(credentialsMatcher);
	    	return myRealm;
    }   
}






/*
@Provides
@Singleton
Realm loadRealm() 
{	
	Sha256CredentialsMatcher credentialsMatcher = new Sha256CredentialsMatcher();
	credentialsMatcher.setHashIterations(1024);
	credentialsMatcher.setStoredCredentialsHexEncoded(false);
    return new MyRealm(credentialsMatcher);
}

bind(Realm.class).to(MyRealm.class);
 
bind(CredentialsMatcher.class).to(Sha256CredentialsMatcher.class);
bind(Sha256CredentialsMatcher.class);
bindConstant().annotatedWith(Names.named("shiro.hashIterations")).to(1024);
bindConstant().annotatedWith(Names.named("shiro.hashSalted")).to(true);
bindConstant().annotatedWith(Names.named("shiro.storedCredentialsHexEncoded")).to(false);
*/
