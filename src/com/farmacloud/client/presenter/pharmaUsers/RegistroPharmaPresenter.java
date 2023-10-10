package com.farmacloud.client.presenter.pharmaUsers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.services.ServicioUsuario;
import com.farmacloud.client.services.ServicioUsuarioAsync;
import com.farmacloud.client.ui.registroPharma.RegistroPharmaView;
import com.farmacloud.client.ui.registroPharma.RegistroPharmaViewImp;
import com.farmacloud.shared.model.UsuarioPharma;
import com.farmacloud.shared.validation.UsuarioPharmaForm;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class RegistroPharmaPresenter extends AbstractActivity implements RegistroPharmaView.Presenter
{
	private ClientFactory clientFactory;
	private RegistroPharmaView view;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	Logger log = Logger.getLogger(RegistroPharmaPresenter.class.getSimpleName());
	
	private final ServicioUsuarioAsync servicioUsuario = GWT.create(ServicioUsuario.class);
	
	public RegistroPharmaPresenter(ClientFactory clientFactory){
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.view = clientFactory.getRegistroPharmaView();
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
	public void onSaveUser(UsuarioPharmaForm user) 
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
