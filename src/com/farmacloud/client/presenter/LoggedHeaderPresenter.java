package com.farmacloud.client.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.FarmaCloud;
import com.farmacloud.client.gui.loggedUser.LoggedHeaderView;
import com.farmacloud.client.places.HomePlace;
import com.farmacloud.client.services.ServicioUsuario;
import com.farmacloud.client.services.ServicioUsuarioAsync;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LoggedHeaderPresenter extends AbstractActivity implements LoggedHeaderView.Presenter
{
	public static final Logger log = Logger.getLogger(LoggedHeaderPresenter.class.getName());
	
	private ClientFactory clientFactory;
	private LoggedHeaderView view;
	private List<HandlerRegistration> registrations = new ArrayList();

	private final ServicioUsuarioAsync servicioUsuario = GWT.create(ServicioUsuario.class);
	
	public LoggedHeaderPresenter(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		this.view = this.clientFactory.getLoggedView();
		this.view.setPresenter(this);
		
		/*	R E G I S T R A T I O N S	*/
		this.registrations.add(this.view.getExitButton().addClickHandler(new ExitButtonHandler()));
		this.view.setUserName(Cookies.getCookie(FarmaCloud.COOKIE_USER_NAME));
		panel.setWidget(view.asWidget());
	}
	
	@Override
	public void onStop(){
		for (HandlerRegistration registration : registrations) {
		      registration.removeHandler();
		}
	}
	
    @Override
    public String mayStop() {
        return null;
    }

    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
    
    class ExitButtonHandler implements ClickHandler
    {
    	@Override
		public void onClick(ClickEvent event) 
		{
			String nameUser = Cookies.getCookie(FarmaCloud.COOKIE_USER_NAME);
			String sessionId = Cookies.getCookie(FarmaCloud.COOKIE_SESSION_ID);
		
			System.out.println("logout "+nameUser);
			servicioUsuario.logout(new AsyncCallback<Void>() 
			{	
				@Override
				public void onSuccess(Void result) 
				{
					System.out.println("Cliente --> logout(); exito");
					
					/* Dado que la llamada ha sido satisfactoria, actuamos de igual forma para ambos casos */
					

					
					/* Redirigimos home de cliente no identificado */
					goTo(new HomePlace());
				}
				
				@Override
				public void onFailure(Throwable caught) 
				{
					System.out.println("Cliente --> logout(); fallo");
				}
			});
		}	
    }
}
