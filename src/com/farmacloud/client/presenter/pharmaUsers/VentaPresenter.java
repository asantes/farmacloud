package com.farmacloud.client.presenter.pharmaUsers;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.gui.pharmaUsers.VentaView;
import com.farmacloud.client.gui.pharmaUsers.VentaView.Presenter;
import com.farmacloud.client.services.ServicioGestionVenta;
import com.farmacloud.client.services.ServicioGestionVentaAsync;
import com.farmacloud.shared.model.Venta;
import com.farmacloud.shared.model.infoView.FormandoVenta;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class VentaPresenter extends AbstractActivity implements Presenter
{
	private VentaView view;
	private FormandoVenta formandoVenta;
	private Venta venta;
	private ClientFactory clientFactory;
	private List<HandlerRegistration> registrations = new ArrayList();
	private final ServicioGestionVentaAsync servicioGestionVenta = GWT.create(ServicioGestionVenta.class);

	public VentaPresenter(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		
		this.view = clientFactory.getVentasView();
		this.view.setPresenter(this);
		this.formandoVenta = new FormandoVenta();
		this.formandoVenta.addDataDisplay(this.view.getSellTable());
		this.venta = new  Venta();
		
		/*	R E G I S T R A T I O N S	*/
		this.registrations.add(this.view.getCerrarVenta().addClickHandler(new ManejadoraCerrarVenta()));
		panel.setWidget(view.asWidget());
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
    
    public  String verificarCodigoIntroducido(String cadenaEntrada)
	{
    	/* Mensajes de aviso a mostrar */
    	String noEncontrado = "Codigo nacional erroneo o medicamento fuera de catalogo";
    	String cadenaNoReconocida = "Introduzca el codigo nacional del medicamento(6 digitos)"
    											+ " o el codigo EAN13(13 digitos)";
    	String encontradoNuevo = "Medicamento no incluido en la farmacia";
    	String encontradoViejo = "Medicamento disponible en farmacia";
    	String ok ="";
    	
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
				this.view.setMensaje(cadenaNoReconocida, true);
			}
		
		return cadenaFormateada;
	}
    
    public void buscarMedicamentoVenta(String cadenaVerificada)
    {
    	/*servicioGestionVenta.buscarUnidad(cadenaVerificada, 1, new AsyncCallback<List<InfoVenta>>() {
			@Override
			public void onSuccess(List<InfoVenta> result) 
			{
				System.out.println("Cliente ok: buscar medicamento venta");
				if(!result.isEmpty())
				{
					formandoVenta.addMedicament(result.get(0));
					refrescarDatosVenta();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Cliente error: buscar medicamento venta");
			}
		});*/
    }
    
    public void refrescarDatosVenta()
    {
    	double nuevoImporte = 0;
    	/*for(InfoVenta i : this.formandoVenta.getDataProvider().getList()){
    		nuevoImporte = nuevoImporte + i.getPrecioFinal();
    	}*/
    	String nuevoImporteTexto = String.valueOf(nuevoImporte).concat(" €");
		this.venta.setImporte(nuevoImporte);
		this.view.setPrecioTotal(nuevoImporteTexto);
    }
    
   class ManejadoraCerrarVenta implements ClickHandler
   {
		@Override
		public void onClick(ClickEvent event)
		{
			/*List<InfoVenta> listaInfoVenta = new ArrayList<InfoVenta>();
			for(InfoVenta i : formandoVenta.getDataProvider().getList())
					listaInfoVenta.add(i);
			
			servicioGestionVenta.cerrarVenta(listaInfoVenta, venta, new AsyncCallback<Void>()
			{
				@Override
				public void onSuccess(Void result) {
					System.out.println("Cliente ok: cerrando venta");
				}
				
				@Override
				public void onFailure(Throwable caught) {
					System.out.println("Cliente error: cerrando venta");
				}
			}); */
		} 
   }

	@Override
	public void actualizarPrecioUnidad(int index)
	{
	
		this.formandoVenta.getDataProvider().refresh();
		//List<InfoVenta> lista = this.formandoVenta.getDataProvider().getList();
		//String tsi = lista.get(index).getTSI();
		
		/* Cada TSI tiene una aportacion distinta. En funcion del TSI establecemos la aportacion */
		/*
		switch (lista.get(index).getTSI())
		{
			case "ninguno":
				lista.get(index).setAportacion(100.0);
	        	break;
	        
			case "TSI001":
				lista.get(index).setAportacion(30.0);
				break;
	        
			case "TSI002":
				lista.get(index).setAportacion(40.0);
		        break;
		        
			case "TSI003":
				lista.get(index).setAportacion(50.0);
		        break;
		        
			case "TSI004":
				lista.get(index).setAportacion(60.0);
		        break;
		        
			case "TSI005":
				lista.get(index).setAportacion(70.0);
		        break;
	    
			case "TSI006":
				lista.get(index).setAportacion(80.0);
		        break;
		} */
		
		//System.out.println("tengo este pvp "+lista.get(index).getUnidadMedicamento().getMedicamento().getPrecioPVP());
		
		/* Establecemos el nuevo precio en funcion de la aportacion */
		//lista.get(index).setPrecioFinal(lista.get(index).getUnidadMedicamento().getMedicamento().getPrecioPVP()*
			//							lista.get(index).getAportacion()*0.01);
		
		//System.out.println("Se ha cambiado un TSI, se ha marcado "+this.formandoVenta.getDataProvider().getList().get(index).getTSI()+" y va a aportar "+
	//	lista.get(index).getPrecioFinal()+" euritos");
		
		this.refrescarDatosVenta();
	}
}
