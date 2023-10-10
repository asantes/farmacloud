package com.farmacloud.client.presenter.pharmaUsers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.FarmaCloud;
import com.farmacloud.client.services.ServicioGestionFarmacia;
import com.farmacloud.client.services.ServicioGestionFarmaciaAsync;
import com.farmacloud.client.ui.noticias.EscribirNoticiaView;
import com.farmacloud.client.ui.noticias.EscribirNoticiaView.Presenter;
import com.farmacloud.client.ui.widgets.NoticiaWidget;
import com.farmacloud.client.ui.widgets.PopupPreview;
import com.farmacloud.client.ui.widgets.UiHelper;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.HasRpcToken;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;

public class EscribirNoticiaPresenter extends AbstractActivity implements Presenter
{
	private EscribirNoticiaView view;
	private ClientFactory clientFactory;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	private final ServicioGestionFarmaciaAsync servicioGestionFarmacia= GWT.create(ServicioGestionFarmacia.class);
	
	public  EscribirNoticiaPresenter(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		this.view = clientFactory.getEscribirNoticiaView();
		this.view.setPresenter(this);
		((HasRpcToken)servicioGestionFarmacia).setRpcToken(FarmaCloud.token);
		bind();
		panel.setWidget(view.asWidget());
	}
	
	public void bind(){
		this.registrations.add(this.view.getAñadirNoticia().addClickHandler(new ManejadoraClickAñadirNoticia()));
		this.registrations.add(this.view.getPreviewNoticia().addClickHandler(new ManejadoraPreviewNoticia()));
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
    
    private class ManejadoraClickAñadirNoticia implements ClickHandler
    {
		@Override
		public void onClick(ClickEvent event) 
		{
			byte[] cuerpoNoticia = view.getTexto().getBytes();
			String titular = view.getTitular();
			
			if(!titular.equals("") && cuerpoNoticia!=null)
				añadirNoticia(cuerpoNoticia, titular);
		}	
    }

    public void añadirNoticia(byte[] cuerpoNoticia, String titularNoticia)
    {
		this.servicioGestionFarmacia.añadirNoticia(cuerpoNoticia, titularNoticia, new AsyncCallback<Boolean>() 
		{
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("failure at añadirNoticia "+caught.toString());
			}

			@Override
			public void onSuccess(Boolean result) 
			{
				System.out.println("success at añadirNoticia");
				String message;
				boolean exito = result.booleanValue();
				if(exito)
					message = "Noticia añadida con exito";
				else message = "No ha sido posible añadir la noticia";
				UiHelper.Notify(exito, "", message);
			}
		});
    }
    
    private class ManejadoraPreviewNoticia implements ClickHandler
    {
    	public void onClick(ClickEvent event)
    	{	
    		String cuerpoNoticia = view.getTexto();
    		String titular = view.getTitular();
    		String autor = Cookies.getCookie(FarmaCloud.COOKIE_USER_NAME);
    		if(autor==null)
    			autor = "autor";
    		
    		Date fecha = new Date();
    		
    		NoticiaWidget noticiaWidget = new NoticiaWidget(cuerpoNoticia, titular, autor, fecha);
    		noticiaWidget.getElement().setAttribute("tipo", "preview");
    		
    		final PopupPreview popup = (PopupPreview) view.getPreviewWidget();
    		popup.incorporarNoticia(noticiaWidget);
    		
    		((PopupPanel) popup).setPopupPositionAndShow(new PositionCallback() 
    		{
				@Override
				public void setPosition(int offsetWidth, int offsetHeight)
				{
					/* Situamos el popupPanel justo en el centro de la pantalla del usuario */
					int left = (Window.getClientWidth() - offsetWidth) / 2;
		            int top = (Window.getClientHeight() - offsetHeight) / 2;
		            popup.setPopupPosition(left, top);
		            popup.show();
				}
			});
    	}
    }
    
}
