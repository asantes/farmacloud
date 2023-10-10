package com.farmacloud.client.presenter.anonymousUser;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.services.ServicioUsuario;
import com.farmacloud.client.services.ServicioUsuarioAsync;
import com.farmacloud.client.ui.registro.RegistroView;
import com.farmacloud.shared.model.UsuarioPharma;
import com.farmacloud.shared.validation.UsuarioForm;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class RegistroPresenter extends AbstractActivity implements RegistroView.Presenter
{
	private ClientFactory clientFactory;
	private RegistroView view;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	
	private final ServicioUsuarioAsync servicioUsuario = GWT.create(ServicioUsuario.class);
	
	public RegistroPresenter(ClientFactory clientFactory){
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.view = clientFactory.getRegistroView();
		this.view.setPresenter(this);
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
	
	@Override
	public void onSaveUser(UsuarioForm user) 
	{
		UsuarioPharma usuario = new UsuarioPharma();
		usuario.setNameUser(user.getUsuario());
		usuario.setPassword(user.getContraseña());
		usuario.setDireccion(user.getEmail());
		
		servicioUsuario.crearUsuario(usuario, new AsyncCallback<Void>()
		{
			@Override
			public void onSuccess(Void result) {
				System.out.println("CLIENT OK: rpc crear usuario");
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("CLIENTE ERROR: rpc crear usuario");
			}
		});
	}
}
