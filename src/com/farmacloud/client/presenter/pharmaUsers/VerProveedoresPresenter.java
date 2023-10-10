package com.farmacloud.client.presenter.pharmaUsers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.FarmaCloud;
import com.farmacloud.client.services.ServicioGestionProveedor;
import com.farmacloud.client.services.ServicioGestionProveedorAsync;
import com.farmacloud.client.ui.modals.ModalRpcFailure;
import com.farmacloud.client.ui.proveedores.VerProveedoresView;
import com.farmacloud.client.ui.proveedores.VerProveedoresView.Presenter;
import com.farmacloud.shared.model.Proveedor;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.HasRpcToken;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class VerProveedoresPresenter extends AbstractActivity implements Presenter
{
	Logger log = Logger.getLogger(this.getClass().getSimpleName());
	private ClientFactory clientFactory;
	private VerProveedoresView view;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	
	private final ServicioGestionProveedorAsync servicioGestionProveedor = GWT.create(ServicioGestionProveedor.class);
	Set<Proveedor> setProveedores = new HashSet<Proveedor>();
	
	public VerProveedoresPresenter(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		((HasRpcToken)servicioGestionProveedor).setRpcToken(FarmaCloud.token);
		this.view = clientFactory.getVerProveedoresView();
		this.view.setPresenter(this);
		servicioGestionProveedor.obtenerProveedores(new ObtenerProveedores());
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

    class ObtenerProveedores implements AsyncCallback<List<Proveedor>>
    {
		@Override
		public void onFailure(Throwable caught) {
			log.severe(caught.getMessage());
			new ModalRpcFailure();
		}

		@Override
		public void onSuccess(List<Proveedor> result) 
		{
			if(!result.isEmpty())
			{
				log.info("Exito");
				/* Mostramos los proveedores */
				for(Proveedor p : result)
				{
					if(!view.getSet().contains(p)) /* Comprobacion para no mostrar repetidos */
					{
						/* Comprobacion para no mostrar dos veces el mismo elemento por el mero hecho de haber sufrido
						 * una actualizacion en alguno/s de su/s atributo/s
						 */
						for(Proveedor pSet : setProveedores)
							if(!pSet.getNIF().equals(p)){
								view.showInfo(	p.getNIF(),
												p.getNombre(),
												p.getDireccion(),
												p.getTelefono(), 
												p.getEmail());
							
								setProveedores.remove(pSet);
								setProveedores.add(p);
						}
					}
				}
				
				/* Para dejar de mostrar eliminados vamos a probar eventBus */
			}
			else{
				log.info("Hemos recibido una lista vacia");
			}
		}
    	
    }
	@Override
	public void onEdit(String principal) {
	}
}