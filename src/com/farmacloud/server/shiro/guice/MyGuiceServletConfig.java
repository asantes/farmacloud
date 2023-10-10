package com.farmacloud.server.shiro.guice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.shiro.guice.aop.ShiroAopModule;
import org.apache.shiro.guice.web.ShiroWebModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;

public class MyGuiceServletConfig extends GuiceServletContextListener
{
	private ServletContext servletContext;
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		this.servletContext = servletContextEvent.getServletContext();
		super.contextInitialized(servletContextEvent);
	}
	
    @Override
    protected Injector getInjector() {
 
        // get list of generic modules, must be List and not Set since ordering counts
        final List<Module> guiceModules = new ArrayList<Module>();
 
        // add Google Guice servlet integration first
        guiceModules.add(0, new MyServletModule());
 
        // add the Shiro Web Module and ShiroFilterModule last
        guiceModules.add(new MyShiroWebModule(this.servletContext));
        guiceModules.add(new ShiroAopModule());
        guiceModules.add(ShiroWebModule.guiceFilterModule());
        
        final Injector injector = Guice.createInjector(guiceModules);
        return injector;
    }
}
