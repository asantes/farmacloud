package com.farmacloud.client;

import java.util.logging.Logger;

import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Navbar;

import com.farmacloud.client.activityMappers.AsideContentActivityMapper;
import com.farmacloud.client.activityMappers.MainContentActivityMapper;
import com.farmacloud.client.activityMappers.MenuActivityMapper;
import com.farmacloud.client.gui.layout.Layout;
import com.farmacloud.client.places.HomePlace;
import com.farmacloud.client.services.ServicioUsuario;
import com.farmacloud.client.services.ServicioUsuarioAsync;
import com.farmacloud.shared.model.DTO.UserRoleDTO;
import com.farmacloud.shared.model.abstracts.UsuarioAbstracto;
import com.google.api.server.spi.auth.common.User;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.XsrfToken;
import com.google.gwt.user.client.rpc.XsrfTokenService;
import com.google.gwt.user.client.rpc.XsrfTokenServiceAsync;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.binder.EventBinder;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FarmaCloud implements EntryPoint
{
	/* C O N S T A N T E S */
	public static final String COOKIE_USER_NAME = "username";
	public static final String COOKIE_SESSION_ID = "sessionid";
	
	public static final double HEADER_SIZE = 3;
	public static final double MENU_SIZE = 5;
	public static final double ASIDE_MENU_SIZE = 18;
	public static final int ANIMATION_TIME = 500;
	/* C O N S T A N T E S */

	/* N E W	L A Y O U T */
	Navbar menu;
	Column mainContent;
	Column asideContent;
	
	public static final Logger log = Logger.getLogger(FarmaCloud.class.getName());
	private final ServicioUsuarioAsync servicioUsuario = GWT.create(ServicioUsuario.class);
	
	/* E V E N T	T H I N G S  */
	private EventBus eventBus;
	interface MyEventBinder extends EventBinder<FarmaCloud> {};
	private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);
	
	XsrfTokenServiceAsync xsrfTokenService;
	public static XsrfToken token = null;
	PlaceHistoryHandler placeHistoryHandler;
	
	ClientFactory clientFactory = GWT.create(ClientFactoryImp.class);  
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() 
	{	
		xsrfTokenService = GWT.create(XsrfTokenService.class);
		((ServiceDefTarget)xsrfTokenService).setServiceEntryPoint(GWT.getModuleBaseURL() + "xsrf");
		
		/* */
		//ClientFactory clientFactory = GWT.create(ClientFactoryImp.class);  
		eventBus = clientFactory.getEventBus();
		eventBinder.bindEventHandlers(this, eventBus);
		final PlaceControllerExt placeController = clientFactory.getPlaceController();
		final PlaceHistoryMapper historyMapper = clientFactory.getPlaceHistoryMapper();		
		
		/* Creamos el Layout de la aplicacion */
		Layout layout = new Layout();
		menu = layout.getMenu();
		mainContent = layout.getMainContent();
		asideContent = layout.getAsideContent();
		
		AcceptsOneWidget menuDisplay = new AcceptsOneWidget() {
			   public void setWidget(IsWidget activityWidget) {
			      Widget widget = Widget.asWidgetOrNull(activityWidget);
			      if(widget!=null)
			      {
			    	  menu.clear();
			    	  menu.add(widget);
			      }
			   }
			};
			
		AcceptsOneWidget mainContentDisplay = new AcceptsOneWidget() {
			   public void setWidget(IsWidget activityWidget) {
			      Widget widget = Widget.asWidgetOrNull(activityWidget);
			      if(widget!=null){
				    	  mainContent.clear();
				    	  System.out.println("limpiando main "+mainContent.getWidgetCount());
				    	  mainContent.add(widget);
				
				    	  System.out.println("limpiando main "+mainContent.getWidgetCount());
			      }
			     // else mainContent.setSize("LG_0, MS_0");
			   }
			};
		
		AcceptsOneWidget asideContentDisplay = new AcceptsOneWidget() {
			   public void setWidget(IsWidget activityWidget) {
			      Widget widget = Widget.asWidgetOrNull(activityWidget);
			      if(widget!=null)
			       asideContent.add(widget);
			      else asideContent.setSize("LG_0, MS_0");
			   }
			};
			
		MenuActivityMapper menuActivityMapper = new MenuActivityMapper(clientFactory);
		MainContentActivityMapper mainContentMapper= new MainContentActivityMapper(clientFactory);
		AsideContentActivityMapper asideContentActivityMapper = new AsideContentActivityMapper(clientFactory);
		
		ActivityManager menuActivityManager = new ActivityManager(menuActivityMapper, eventBus);
		menuActivityManager.setDisplay(menuDisplay);
		ActivityManager mainContentActivityManager = new ActivityManager(mainContentMapper, eventBus);
		mainContentActivityManager.setDisplay(mainContentDisplay);
		ActivityManager asideContentActivityManager = new ActivityManager(asideContentActivityMapper, eventBus);
		asideContentActivityManager.setDisplay(asideContentDisplay);
		
		/* */
		final Place defaultPlace = new HomePlace();
        placeHistoryHandler = new PlaceHistoryHandler(historyMapper);
        placeHistoryHandler.register(placeController, eventBus, defaultPlace);
        //placeHistoryHandler.handleCurrentHistory();

        RootPanel.get().add(layout);
       
        if(Cookies.getCookie("JSESSIONID")==null)
        	System.out.println("No existe cookie JSESSIONID al iniciar la aplicacion");
        else
        	System.out.println("Existe cookie JSESSIONID al iniciar la aplicacion. Su valor es --> "+Cookies.getCookie("JSESSIONID"));
        
        sessionAgreement();
	}
	
	public void sessionAgreement()
	{
		servicioUsuario.dumbRequest(new AsyncCallback<Void>() 
		{
			@Override
			public void onFailure(Throwable caught) 
			{
				System.out.println("failure at dumbRequest 1");
			}

			@Override
			public void onSuccess(Void result) 
			{
				System.out.println("success at dumbRequest 1");
				System.out.println("La JSESSIONID es "+Cookies.getCookie("JSESSIONID"));
				servicioUsuario.dumbRequest(new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) 
					{
						System.out.println("failure at dumbRequest 2");
					}

					@Override
					public void onSuccess(Void result) 
					{
						System.out.println("success at dumbRequest 2");
						System.out.println("La JSESSIONID es "+Cookies.getCookie("JSESSIONID"));
						getNewToken();
					}
				});
			}
		});
	}
	
	public static void borrarCookies(){
		Cookies.removeCookie("farmacloud");
	}
	
	public void getNewToken()
	{
		/* Obtenemos el nuevo token */
		xsrfTokenService.getNewXsrfToken(new AsyncCallback<XsrfToken>() 
		{
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("failure at getNewXsrfToken");
			}

			@Override
			public void onSuccess(XsrfToken result) 
			{
				System.out.println("success at getNewXsrfToken");
			
				if(result!=null)
				{
					System.out.println("token --> "+result.getToken());
					token = result;
					placeHistoryHandler.handleCurrentHistory();
				}
				else
					System.out.println("el token obtenido es null :S");
			}
		} );
	}
}






