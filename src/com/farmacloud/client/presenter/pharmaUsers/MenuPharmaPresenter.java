package com.farmacloud.client.presenter.pharmaUsers;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.places.HomePlace;
import com.farmacloud.client.services.ServicioUsuario;
import com.farmacloud.client.services.ServicioUsuarioAsync;
import com.farmacloud.client.ui.menu.MenuPharmaView;
import com.farmacloud.client.ui.menu.MenuPharmaView.Presenter;
import com.farmacloud.client.ui.modals.ModalForbiddenAccess;
import com.farmacloud.client.ui.modals.ModalForbiddenAccess.ModalPresenter;
import com.farmacloud.client.ui.modals.ModalRpcFailure;
import com.farmacloud.shared.Utiles;
import com.farmacloud.shared.model.DTO.UserRoleDTO;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class MenuPharmaPresenter extends AbstractActivity implements Presenter, ModalPresenter
{
	private ClientFactory clientFactory;
	private MenuPharmaView view;
	private final ServicioUsuarioAsync servicioUsuario = GWT.create(ServicioUsuario.class);
	private List<HandlerRegistration> registrations = new ArrayList<>();

	public MenuPharmaPresenter(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.view = clientFactory.getMenuPharmaView();
		this.view.setPresenter(this);
		isItAllowed(panel, this);
	}
	
	@Override
	public void onStop(){
		for (HandlerRegistration registration : registrations) {
		      registration.removeHandler();
		}
	}
	
    public String mayStop(){
    	return null;
    }
    
    public void goTo(Place place){
    	clientFactory.getPlaceController().goTo(place);
    }
    
    @Override
 	public void onCloseModal() {
     	clientFactory.getPlaceController().previous();
 	}

    public void isItAllowed(final AcceptsOneWidget panel, final ModalPresenter presenter)
    {
    	servicioUsuario.isActive(new AsyncCallback<UserRoleDTO>() 
    	{	
			@Override
			public void onSuccess(UserRoleDTO result) 
			{
				System.out.println("success at isItAllowed");
				
				if(result!=null)
				{
					if(result.getRoles().contains(Utiles.Roles.PHARMA) || result.getRoles().contains(Utiles.Roles.ADMIN))
					{
						panel.setWidget(view.asWidget());	
					}
					else
					{
						System.out.println("Usuario no autorizado a utilizar el menuPharma");
						new ModalForbiddenAccess(true, presenter);
					}
				}
				else	
				{
					System.out.println("Usuario no autorizado a utilizar el menuPharma");
					new ModalForbiddenAccess(true, presenter);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) 
			{
				System.out.println("failure at isItAllowed");
				new ModalRpcFailure(true);
			}
		});
    }
    
    @Override
	public void logout() 
    {
    	servicioUsuario.logout(new AsyncCallback<Void>() 
    	{
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("failure at logout");
			}

			@Override
			public void onSuccess(Void result) {
				System.out.println("success at logout");
				goTo(new HomePlace());
			}
		});
	}

}
