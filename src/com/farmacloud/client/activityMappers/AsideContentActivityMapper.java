package com.farmacloud.client.activityMappers;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.presenter.pharmaUsers.EscribirNoticiaPresenter;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AsideContentActivityMapper implements ActivityMapper{

	private ClientFactory clientFactory;
	private EscribirNoticiaPresenter escribirNoticiaPresenter;
	
	public AsideContentActivityMapper(ClientFactory _clientFactory) {
		super();
		this.clientFactory = _clientFactory;
	}
	
	@Override
	public Activity getActivity(Place place) {
		
		/*if(place instanceof ProveedoresPlace || 
			place instanceof ProveedoresAñadirPlace)
		{
			if(proveedoresPresenter!=null)
				return proveedoresPresenter;
			else{
				proveedoresPresenter = new ProveedoresMenuPresenter(clientFactory);
				return proveedoresPresenter;
			}
		}
		
			else if(place instanceof PedidosMenuPlace ||
					place instanceof PedidosNuevoPlace ||
					place instanceof RecepcionPlace)
				{
					if(pedidosMenuPresenter!=null)
						return pedidosMenuPresenter;
					else{
						pedidosMenuPresenter = new PedidosMenuPresenter(clientFactory);
						return pedidosMenuPresenter;
					}
				}
				else if(place instanceof MedicamentosMenuPlace ||
						place instanceof MedicamentosAñadirPlace ||
						place instanceof MedicamentosPlace)
						{
							if(medicamentosMenuPresenter!=null)
								return medicamentosMenuPresenter;
							else{
								medicamentosMenuPresenter = new MedicamentosMenuPresenter(clientFactory);
								return medicamentosMenuPresenter;
							}
						}
						else if(place instanceof VentaPlace || place instanceof SimularPlace ||place instanceof SimulacionRunningPlace)
							 {
								if(ventaMenuPresenter!=null)
									return ventaMenuPresenter;
								else
									return ventaMenuPresenter = new VentaMenuPresenter(clientFactory);
							 }
							 else if(place instanceof PharmaHomePlace || place instanceof EscribirNoticiaPlace || place instanceof NoticiasPlace)
								  {
								 	if(asideMenuPharmaPresenter!=null)
										return asideMenuPharmaPresenter;
									else
										return asideMenuPharmaPresenter = new AsideMenuPharmaPresenter(clientFactory);
								   }
					
						*/
		
		return null;
	}
}	