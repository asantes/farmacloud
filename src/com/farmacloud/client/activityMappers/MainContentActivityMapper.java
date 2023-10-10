package com.farmacloud.client.activityMappers;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.places.EscribirNoticiaPlace;
import com.farmacloud.client.places.HomePlace;
import com.farmacloud.client.places.MedicamentosAñadirPlace;
import com.farmacloud.client.places.MedicamentosPlace;
import com.farmacloud.client.places.NoticiasPlace;
import com.farmacloud.client.places.PedidosMenuPlace;
import com.farmacloud.client.places.PedidosNuevoPlace;
import com.farmacloud.client.places.PharmaHomePlace;
import com.farmacloud.client.places.AñadirProveedor;
import com.farmacloud.client.places.RecepcionPlace;
import com.farmacloud.client.places.RegistroPharmaPlace;
import com.farmacloud.client.places.RegistroPlace;
import com.farmacloud.client.places.SimulacionRunningPlace;
import com.farmacloud.client.places.SimularPlace;
import com.farmacloud.client.places.VentaPlace;
import com.farmacloud.client.places.VerProveedoresPlace;
import com.farmacloud.client.presenter.anonymousUser.NoticiasPresenter;
import com.farmacloud.client.presenter.anonymousUser.RegistroPresenter;
import com.farmacloud.client.presenter.pharmaUsers.AñadirProveedorPresenter;
import com.farmacloud.client.presenter.pharmaUsers.EscribirNoticiaPresenter;
import com.farmacloud.client.presenter.pharmaUsers.MedicamentosAñadirInfoPresenter;
import com.farmacloud.client.presenter.pharmaUsers.MedicamentosPresenter;
import com.farmacloud.client.presenter.pharmaUsers.PedidosAñadirPresenter;
import com.farmacloud.client.presenter.pharmaUsers.PedidosMainPresenter;
import com.farmacloud.client.presenter.pharmaUsers.RecepcionPresenter;
import com.farmacloud.client.presenter.pharmaUsers.RegistroPharmaPresenter;
import com.farmacloud.client.presenter.pharmaUsers.SimulacionRunningPresenter;
import com.farmacloud.client.presenter.pharmaUsers.SimularPresenter;
import com.farmacloud.client.presenter.pharmaUsers.VentaPresenter;
import com.farmacloud.client.presenter.pharmaUsers.VerProveedoresPresenter;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class MainContentActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;
	private RegistroPresenter registroPresenter;
	
	/* Proveedores */
	private AñadirProveedorPresenter añadirProveedorPresenter;
	private VerProveedoresPresenter verProveedoresPresenter;
	
	private PedidosAñadirPresenter pedidosAñadirPresenter;
	private MedicamentosPresenter medicamentosPresenter;
	private PedidosMainPresenter pedidosMainPresenter;
	private RecepcionPresenter recepcionPresenter;
	private MedicamentosAñadirInfoPresenter medicamentosAñadirInfoPresenter;
	private VentaPresenter ventaPresenter;
	private SimularPresenter simularPresenter;
	private SimulacionRunningPresenter simulacionRunningPresenter;
	private EscribirNoticiaPresenter escribirNoticiaPresenter;
	private NoticiasPresenter noticiaPresenter;
	private RegistroPharmaPresenter registroPharmaPresenter;
	
	public MainContentActivityMapper(ClientFactory clientFactory){
		this.clientFactory = clientFactory;
	}
	
	@Override
	public Activity getActivity(Place place) 
	{
		System.out.println("me activo "+clientFactory.getPlaceHistoryMapper().getToken(place));
		if(place instanceof HomePlace)
		{
			if(noticiaPresenter!=null)
				return noticiaPresenter;
			else return noticiaPresenter = new NoticiasPresenter(clientFactory);
		}
		else if(place instanceof RegistroPlace)
			 {
				System.out.println("soy el activity");
				if(registroPresenter!=null)
					return registroPresenter;
				else{
					registroPresenter = new RegistroPresenter(clientFactory);
					return registroPresenter;
				}
			 }	
			
			else if(place instanceof AñadirProveedor)
				{
					if(añadirProveedorPresenter!=null)
						return añadirProveedorPresenter;
					else{
						añadirProveedorPresenter = new AñadirProveedorPresenter(clientFactory);
						return añadirProveedorPresenter;
					}
				}
				else if(place instanceof PedidosNuevoPlace)
					{
						if(pedidosAñadirPresenter!=null)
							return pedidosAñadirPresenter;
						else{
							pedidosAñadirPresenter = new PedidosAñadirPresenter(clientFactory);
							return pedidosAñadirPresenter;
						}
					}
					else if(place instanceof MedicamentosPlace)
						{
							if(medicamentosPresenter!=null)
								return medicamentosPresenter;
							else{
								medicamentosPresenter = new MedicamentosPresenter(clientFactory);
								return medicamentosPresenter;
							}
						}
						else if(place instanceof PedidosMenuPlace)
						{
							if(pedidosMainPresenter!=null)
								return pedidosMainPresenter;
							else{
								pedidosMainPresenter = new PedidosMainPresenter(clientFactory);
								return pedidosMainPresenter;
							}
						}
							else if(place instanceof RecepcionPlace)
							{
								if(recepcionPresenter!=null)
									return recepcionPresenter;
								else{
									recepcionPresenter = new RecepcionPresenter(clientFactory);
									return recepcionPresenter;
								}	
							}
								else if(place instanceof MedicamentosAñadirPlace)
								{
									System.out.println("NO PUEDE SETRRRRRR");
									if(medicamentosAñadirInfoPresenter!=null)	
										return medicamentosAñadirInfoPresenter;
									else{
										medicamentosAñadirInfoPresenter = new MedicamentosAñadirInfoPresenter(clientFactory);
										return medicamentosAñadirInfoPresenter;
									}
								}
								else if(place instanceof VentaPlace)
									{
										if(ventaPresenter!=null)
											return ventaPresenter;
										else{
											ventaPresenter = new VentaPresenter(clientFactory);
											return ventaPresenter;
											
										}
									}
									else if(place instanceof SimularPlace)
										 {
											if(simularPresenter!=null)
												return simularPresenter;
											else return 
												simularPresenter = new SimularPresenter(clientFactory);
										 }
										else if(place instanceof SimulacionRunningPlace)
											{
												if(simulacionRunningPresenter!=null)
													return simulacionRunningPresenter;
												else return simulacionRunningPresenter = new SimulacionRunningPresenter(clientFactory);
											}
											else if(place instanceof EscribirNoticiaPlace)
											 	{
													if(escribirNoticiaPresenter!=null)
														return escribirNoticiaPresenter;
													else
														return escribirNoticiaPresenter = new EscribirNoticiaPresenter(clientFactory);
											 	}
											else if(place instanceof PharmaHomePlace || place instanceof NoticiasPlace)
												{
													if(noticiaPresenter!=null)
														return noticiaPresenter;
													else
														return noticiaPresenter = new NoticiasPresenter(clientFactory);
												}
												else if(place instanceof RegistroPharmaPlace)
													{
														if(registroPharmaPresenter!=null)
															return registroPharmaPresenter;
														else 
															return registroPharmaPresenter = new RegistroPharmaPresenter(clientFactory);
													}
													else if(place instanceof VerProveedoresPlace)
														{
															if(verProveedoresPresenter!=null)
																return verProveedoresPresenter;
															else return verProveedoresPresenter = new VerProveedoresPresenter(clientFactory);
														}
										 
		return null;
	}
}
