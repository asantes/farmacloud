package com.farmacloud.client.presenter.anonymousUser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.gui.anonymousUser.NoticiasView;
import com.farmacloud.client.gui.anonymousUser.NoticiasView.Presenter;
import com.farmacloud.client.places.NoticiasPlace;
import com.farmacloud.client.presenter.anonymousUser.MyNavigator.MyNavigatorHelper;
import com.farmacloud.client.services.ServicioGestionFarmacia;
import com.farmacloud.client.services.ServicioGestionFarmaciaAsync;
import com.farmacloud.shared.model.DTO.NoticiaDTO;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class NoticiasPresenter extends AbstractActivity implements Presenter
{
	private static final int NUM_NOTICIAS_PAGINA = 3;
	private static final int NUM_MAX_INDICES = 10; //Que sea par
	
	private NoticiasView view;
	private ClientFactory clientFactory;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	private final ServicioGestionFarmaciaAsync servicioGestionFarmacia= GWT.create(ServicioGestionFarmacia.class);
	private MyNavigator myNavigator = new MyNavigator(NUM_MAX_INDICES, NUM_NOTICIAS_PAGINA);
	
	public NoticiasPresenter(ClientFactory clientFactory) 
	{
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		this.view = this.clientFactory.getNoticiaView();
		this.view.setPresenter(this);
		
		/* Escuchamos los cambios de lugar para refrescar */
		registrations.add(eventBus.addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() 
		{	
			@Override
			public void onPlaceChange(PlaceChangeEvent event)
			{
				Place place =  event.getNewPlace();
				comprobarPlace(place);
			}
		}));
		
		comprobarPlace(clientFactory.getPlaceController().getWhere());
		panel.setWidget(view.asWidget());
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
    
	public void comprobarPlace(Place place)
	{
		NoticiasPlace noticiasPlace;
		if(place instanceof NoticiasPlace)
		{
			noticiasPlace = (NoticiasPlace) place;
			int numPag = Integer.parseInt((noticiasPlace.getPageNumber()));
			obtenerNoticias(numPag);	
		}
	}
	
    public void obtenerNoticias(final int indice)
    {
    	this.servicioGestionFarmacia.obtenerNoticias(NUM_NOTICIAS_PAGINA, indice, new AsyncCallback<List<NoticiaDTO>>() 
    	{	
			@Override
			public void onSuccess(List<NoticiaDTO> result) 
			{
				System.out.println("Cliente --> obtenerNoticias(); exito RPC");
				if(result!=null)
				{
					if(!result.isEmpty())
					{
						view.cleanView();
						procesarNoticias(result, indice);
					}
					else
					{
						/* Sin noticias en el rango indicado*/
						System.out.println("No tenemos tantas noticias tronco...");
						//goTo(new NoticiasPlace("page", "1"));
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Cliente --> obtenerNoticias(); fallo RPC");
			}
		});
    }
    
    public void procesarNoticias(List<NoticiaDTO> listaNoticias, int indice)
    {
		System.out.println("tenemos "+listaNoticias.size());
		System.out.println("en total "+listaNoticias.get(0).getContador());
		
		/* Iteramos para cada una de las noticias recibidas */
		for(NoticiaDTO noticia : listaNoticias)
		{
			/* Obtenemos el texto plano a partir de los bytes */
			String texto = null;
			try {
				texto = new String(noticia.getCuerpoBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			/* Obtenemos una fraccion del texto que sera el que mostraremos */
			String textoParcial;
			StringBuilder sb = new StringBuilder();
			
			if(texto.length()>650){
				textoParcial = texto.substring(0, 650);
				sb.append(textoParcial);
				sb.append("[...]");
			}
			else 
			{
				textoParcial = texto;
				sb.append(textoParcial);
			}
				
			/* Mandamos a la vista añadir la noticia */
			view.showNoticia(sb.toString(), noticia.getTitular(), noticia.getKeyUsuario(), noticia.getFecha());
		}
		
		/* Despues de operar con las noticias ahora tenemos que trabajar con paginador */
		int indiceActual = indice;
		int contadorNoticias = listaNoticias.get(0).getContador();

		MyNavigatorHelper mnh = myNavigator.refresh(indiceActual, contadorNoticias, listaNoticias.size());
		if(mnh!=null)
			view.buildAndAddNavigator(mnh.getIndiceStart(), mnh.getIndiceEnd(), mnh.getIndiceActual());
    }

	@Override
	public String getToken(int i) 
	{
		NoticiasPlace noticiasPlace = new NoticiasPlace("page", String.valueOf(i));
		String token = clientFactory.getPlaceHistoryMapper().getToken(noticiasPlace);
		return token;
	}
}
