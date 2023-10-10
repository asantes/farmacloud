package com.farmacloud.client.presenter.pharmaUsers;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.services.ServicioGestionMedicamento;
import com.farmacloud.client.services.ServicioGestionMedicamentoAsync;
import com.farmacloud.client.ui.widgetsold.InfoMedicamentoView;
import com.farmacloud.client.ui.widgetsold.InfoMedicamentoView.Presenter;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class InfoMedicamentoPresenter extends AbstractActivity implements Presenter
{
	private ClientFactory clientFactory;
	private InfoMedicamentoView view;
	private List<HandlerRegistration> registrations = new ArrayList();
	private final ServicioGestionMedicamentoAsync servicioGestionMedicamento = GWT.create(ServicioGestionMedicamento.class);
	
	/* Mensajes de aviso a mostrar */
	private static String noEncontrado = "Codigo nacional erroneo o medicamento fuera de catalogo";
	private static String cadenaNoReconocida = "Introduzca el codigo nacional del medicamento(6 digitos)"
											+ " o el codigo EAN13(13 digitos)";
	private static String encontradoNuevo = "Medicamento no incluido en la farmacia";
	private static String encontradoViejo = "Medicamento disponible en farmacia";
	private static String ok ="";
												
	
	public InfoMedicamentoPresenter(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
	}

	@Override
	public void onStop(){
		for (HandlerRegistration registration : registrations) {
		      registration.removeHandler();
		}
	}
	
	public void goTo(Place place) {
		this.clientFactory.getPlaceController().goTo(place);
	}
	
	public  String verificarCodigoIntroducido(String cadenaEntrada)
	{
		String cadenaFormateada = null;
		
		if(cadenaEntrada.startsWith("847000") && cadenaEntrada.length()==13)
		{
			System.out.println("Has introducido numero de barras");
			cadenaFormateada = cadenaEntrada.substring(6, 12); //Cadena == Codigo de barras
			System.out.println(cadenaFormateada);
			this.view.setMensaje(ok, false);
		}
		else if(cadenaEntrada.length()==6)
			{	
				cadenaFormateada = cadenaEntrada;//Cadena == Codigo nacional
				this.view.setMensaje(ok, false);
			}
			else
			{
				this.view.setMensaje(this.cadenaNoReconocida, true);
			}
		
		return cadenaFormateada;
	}
	
	public void buscarMedicamento(String _codigoNacional)
	{
		servicioGestionMedicamento.getMedicamento(_codigoNacional, new AsyncCallback<MedicamentoAbstracto>() 
		{
			@Override
			public void onSuccess(MedicamentoAbstracto result){
				System.out.println("CLIENTE OK: RPC-INFO-MEDICAMENTO");
			
				if(result!=null)
				{
					System.out.println("me llega "+result.getCodigoNacional());
					exitoBuscarMedicamento(result);
				}
					
				else System.out.println("vaya cagad");
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("CLIENTE ERROR: RPC-INFO-MEDICAMENTO");				
			}
		});		
	}
	
	void exitoBuscarMedicamento(MedicamentoAbstracto result)
	{
		/*if(result.isNuevo()==true)
		{
			List<String> data = new ArrayList<String>();
			data.add(result.getCodigoNacional());
			data.add(result.getNombre());
			data.add(result.getLaboratorio());
			data.add(result.getPrincipioActivo());
			data.add(String.valueOf(result.getPrecioPVP()));
			
			System.out.println("Cliente: recibo "+data.get(0)+data.get(1)); */
			/* Mostramos el mensaje de si es un medicamento ya añadido o no */
			/*if(result.isNuevo())
				view.setMensaje(encontradoNuevo, true);
			else view.setMensaje(encontradoViejo, true);*/
			/* Mostramos la informacion del medicamento */
			/*view.setData(data);
		}*/
		
		/*else
		{
			System.out.println("Medicamento no incluido en el Catalogo");
			List<String> data = new ArrayList<String>();
			data.add(result.getCodigoNacional());
			data.add(result.getNombre());
			data.add(result.getLaboratorio());
			data.add(result.getPrincipioActivo());
			data.add(String.valueOf(result.getPrecioPVP()));
			
			System.out.println("Cliente: recibo "+result.getCodigoNacional());*/
			
			/* Mostramos el mensaje de si es un medicamento ya añadido o no */
			/*if(result.isNuevo())
				view.setMensaje(encontradoNuevo, true);
			else view.setMensaje(encontradoViejo, true);
			view.setData(data);

		}*/
		
	}
	
	void setView(InfoMedicamentoView view){
		this.view = view;
	}
	
	/* G E T T E R S */
	public ClientFactory getClientFactory() {
		return clientFactory;
	}
	
	public List<HandlerRegistration> getListHandlerRegistration(){
		return registrations;
	}
	
	public ServicioGestionMedicamentoAsync getServicioGestionMedicamento(){
		return servicioGestionMedicamento;
	}
}
