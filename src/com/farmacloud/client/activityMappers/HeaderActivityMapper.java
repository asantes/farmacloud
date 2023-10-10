package com.farmacloud.client.activityMappers;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.places.EscribirNoticiaPlace;
import com.farmacloud.client.places.LoggedHomePlace;
import com.farmacloud.client.places.MedicamentosAñadirPlace;
import com.farmacloud.client.places.MedicamentosMenuPlace;
import com.farmacloud.client.places.MedicamentosPlace;
import com.farmacloud.client.places.NoticiasPlace;
import com.farmacloud.client.places.PedidosMenuPlace;
import com.farmacloud.client.places.PedidosNuevoPlace;
import com.farmacloud.client.places.PharmaHomePlace;
import com.farmacloud.client.places.AñadirProveedor;
import com.farmacloud.client.places.ProveedoresPlace;
import com.farmacloud.client.places.RecepcionPlace;
import com.farmacloud.client.places.SimulacionRunningPlace;
import com.farmacloud.client.places.SimularPlace;
import com.farmacloud.client.places.VentaPlace;
import com.farmacloud.client.presenter.LoggedHeaderPresenter;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class HeaderActivityMapper implements ActivityMapper{

	private ClientFactory clientFactory;

	private LoggedHeaderPresenter loggedHeaderPresenter;
	
	public HeaderActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public Activity getActivity(Place place) {
		

		/*if (place instanceof LoggedHomePlace ||
					place instanceof PharmaHomePlace ||
					place instanceof RecepcionPlace ||
					place instanceof ProveedoresPlace ||
					place instanceof ProveedoresAñadirPlace ||
					place instanceof PedidosMenuPlace ||
					place instanceof PedidosNuevoPlace ||
					place instanceof MedicamentosMenuPlace ||
					place instanceof MedicamentosAñadirPlace ||
					place instanceof MedicamentosPlace ||
					place instanceof RecepcionPlace || 
					place instanceof MedicamentosAñadirPlace ||
					place instanceof VentaPlace ||
					place instanceof SimularPlace ||
					place instanceof SimulacionRunningPlace ||
					place instanceof EscribirNoticiaPlace ||
					place instanceof NoticiasPlace)
				{
					if(loggedHeaderPresenter!=null)
						return loggedHeaderPresenter;
					else{
						loggedHeaderPresenter = new LoggedHeaderPresenter(clientFactory);
						return loggedHeaderPresenter;
					}
				}*/
			
		return null;
	}

}
