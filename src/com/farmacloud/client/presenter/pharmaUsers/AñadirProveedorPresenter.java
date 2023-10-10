package com.farmacloud.client.presenter.pharmaUsers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.FarmaCloud;
import com.farmacloud.client.services.ServicioGestionProveedor;
import com.farmacloud.client.services.ServicioGestionProveedorAsync;
import com.farmacloud.client.ui.proveedores.AñadirProveedorView;
import com.farmacloud.client.ui.proveedores.AñadirProveedorView.Presenter;
import com.farmacloud.shared.model.Proveedor;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.HasRpcToken;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AñadirProveedorPresenter extends AbstractActivity implements Presenter
{
	Logger log = Logger.getLogger(this.getClass().getSimpleName());
	private ClientFactory clientFactory;
	private AñadirProveedorView view;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	
	private final ServicioGestionProveedorAsync servicioGestionProveedor = GWT.create(ServicioGestionProveedor.class);

	public AñadirProveedorPresenter(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		((HasRpcToken)servicioGestionProveedor).setRpcToken(FarmaCloud.token);
		this.view = clientFactory.getProveedoresAñadirView();
		this.view.setPresenter(this);
		panel.setWidget(view.asWidget());
	}
	
	@Override
	public void onStop() {
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
	public void onSaveUser(Proveedor proveedor) {
		servicioGestionProveedor.añadirProveedor(proveedor, new AsyncCallback<Proveedor>() 
		{
			@Override
			public void onSuccess(Proveedor result) {
				if(result!=null)
				{
					log.info("exito");
				}
				else log.info("fallo");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				log.severe(caught.getMessage());
			}
		});
	}
}
