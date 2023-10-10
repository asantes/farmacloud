package com.farmacloud.client.presenter.anonymousUser;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.FarmaCloud;
import com.farmacloud.client.gui.anonymousUser.MenuView;
import com.farmacloud.client.places.PharmaHomePlace;
import com.farmacloud.client.services.ServicioUsuario;
import com.farmacloud.client.services.ServicioUsuarioAsync;
import com.farmacloud.client.ui.modals.ModalRpcFailure;
import com.farmacloud.shared.Utiles;
import com.farmacloud.shared.model.DTO.UserRoleDTO;
import com.farmacloud.shared.validation.Credenciales;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.XsrfToken;
import com.google.gwt.user.client.rpc.XsrfTokenService;
import com.google.gwt.user.client.rpc.XsrfTokenServiceAsync;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class MenuPresenter extends AbstractActivity implements MenuView.Presenter
{
	private ClientFactory clientFactory;
	private MenuView view;
	private final ServicioUsuarioAsync servicioUsuario = GWT.create(ServicioUsuario.class);
    private final XsrfTokenServiceAsync xsrfTokenService = GWT.create(XsrfTokenService.class);
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	public MenuPresenter(ClientFactory clientFactory)
	{
		this.clientFactory = clientFactory;
	    ((ServiceDefTarget)xsrfTokenService).setServiceEntryPoint(GWT.getModuleBaseURL() + "xsrf");
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.view = this.clientFactory.getHomeView();
		this.view.setPresenter(this);
		isItAllowed(panel);
	}
	
	@Override
	public void onStop(){
		for (HandlerRegistration registration : registrations) {
		      registration.removeHandler();
		}
	}
	
    public void goTo(Place place){
    	clientFactory.getPlaceController().goTo(place);
    }

	@Override
	public void onLogin(final Credenciales creds, final boolean remember)
	{
		servicioUsuario.login(creds, remember, new AsyncCallback<UserRoleDTO>() 
		{
			@Override
			public void onSuccess(UserRoleDTO result) 
			{
				System.out.println("success at onLogin();");
				
				if(result!=null)
				{
					System.out.println("usuario "+result.getPrincipal()+" logueado");
					/* Exito a la hora de logearse. Existe dicho usuario y la password coincide */
					
					/* Una vez logueados solicitamos el token */
					getNewToken(result);
				}
				else
				{
					/* El usuario no existe o la contraseña introducida es correcta */
					System.out.println("usuario/contraseña incorrectos");
				}
			}
		
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("failure at onLogin()");
			}
		});
	}
	
	public void getNewToken(final UserRoleDTO usuario)
	{
		/* Obtenemos el nuevo token */
		xsrfTokenService.getNewXsrfToken(new AsyncCallback<XsrfToken>() 
		{
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("failure at getNewXsrfToken "+caught);
			}

			@Override
			public void onSuccess(XsrfToken result) 
			{
				System.out.println("success at getNewXsrfToken");
				if(result!=null)
				{
					System.out.println("token --> "+result.getToken());
					FarmaCloud.token = result;
					
					if(usuario.getRoles().contains(Utiles.Roles.PHARMA))
					{
						/* Redirigimos al home principal pharma */
						goTo(new PharmaHomePlace());
						System.out.println("usuario pharma login ok");
				
					}
					else if(usuario.getRoles().contains(Utiles.Roles.ADMIN));
						 {
							goTo(new PharmaHomePlace());
							System.out.println("usuario admin login ok");
						 }
				}
				else
					System.out.println("el token obtenido es null :S");
			}
		} );
	}
	
    public void isItAllowed(final AcceptsOneWidget panel)
    {
    	servicioUsuario.isActive(new AsyncCallback<UserRoleDTO>() 
    	{	
			@Override
			public void onSuccess(UserRoleDTO result) 
			{
				System.out.println("success at isItAllowed");
				
				if(result!=null)
				{
					/* El server nos devuelve el usuario de la sesion. Luego no es anonimo.
					 * Se le redirige a otro sitio
					 */
					System.out.println("Usuario con sesion. No autorizado a utilizar el menu anonimo");	
					if((clientFactory.getPlaceController().isPreviousNull()))
						goTo(new PharmaHomePlace());
					else
						clientFactory.getPlaceController().previous();
				}
				else	
				{
					/* Si el server no nos devuelve nada es porque el usuario no tiene session. Es decir, es anonimo */
					System.out.println("Usuario anonimo");
					panel.setWidget(view.asWidget());
				}
			}
			
			@Override
			public void onFailure(Throwable caught) 
			{
				System.out.println("failure at isItAllowed");
					new ModalRpcFailure(true);
				}
			});
   
    	/* No hace falta que esta llamada sea XSRF. No realiza ningun cambio en la BBDD ni nada */
    	/*else
    	{
    		 Si no existe token significa que no se ha iniciado sesion, luego el usuario es anonimo 
    		System.out.println("No existe token. Usuario anonimo, se le muestra el menu normal");
    		panel.setWidget(view.asWidget());
    	}*/
    }
}
