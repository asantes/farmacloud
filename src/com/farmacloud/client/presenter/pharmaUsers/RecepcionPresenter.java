package com.farmacloud.client.presenter.pharmaUsers;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.gui.pharmaUsers.RecepcionView;
import com.farmacloud.client.gui.pharmaUsers.RecepcionView.Presenter;
import com.farmacloud.client.places.RecepcionPlace;
import com.farmacloud.client.services.ServicioGestionPedido;
import com.farmacloud.client.services.ServicioGestionPedidoAsync;
import com.farmacloud.shared.model.Pedido;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.farmacloud.shared.model.infoView.MedicamentoInfo;
import com.farmacloud.shared.model.infoView.RecepcionandoPedido;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class RecepcionPresenter extends InfoMedicamentoPresenter implements Presenter
{
	private RecepcionView view;
	private RecepcionandoPedido recepcionandoPedido;
	private final ServicioGestionPedidoAsync servicioGestionPedido= GWT.create(ServicioGestionPedido.class);
	
	private List<MedicamentoInfo> pedido;
	private int lastShowedOrder = -1;
	private static String medicamentoNoEnPedido = "Este medicamento no se encuentra en el pedido que esta recepcionando";
	
	public RecepcionPresenter(ClientFactory clientFactory) {
		super(clientFactory);
		this.recepcionandoPedido = new RecepcionandoPedido();
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.view = getClientFactory().getRecepcionView();
		this.view.setPresenter(this);
		/* Obtenemos el custom widget para establecer comunicacion widget-presenter */
		this.setView(this.view.getInfoMedicamentoView());
		this.view.getInfoMedicamentoView().setPresenter(this);
		
		/*	R E G I S T R A T I O N S	*/
		super.getListHandlerRegistration().add(this.view.getConfirmButton().addClickHandler(new ConfirmButtonHandler()));
		
		recepcionandoPedido.addDataDisplay(view.getTablaRecepcion());
		panel.setWidget(view.asWidget());
		
		if(super.getClientFactory().getPlaceController().getWhere() instanceof RecepcionPlace)
		{
			RecepcionPlace myPlace = (RecepcionPlace) super.getClientFactory().getPlaceController().getWhere();
			String token = myPlace.getName();
			if(!token.equals(""))
			{
				int idInterno = Integer.parseInt(token);
				getPedidoConcreto(idInterno);
			}
		}
	}
	
	@Override
	public void onStop(){
		super.onStop();
		recepcionandoPedido.getDataProvider().removeDataDisplay(view.getTablaRecepcion());
	}
		
	@Override
	public void exitoBuscarMedicamento(MedicamentoAbstracto result){
		super.exitoBuscarMedicamento(result);
		this.añadirATablaRecepcion(result);
	}
	
	public void añadirATablaRecepcion(MedicamentoAbstracto result)
	{	
			/* Si el medicamento pasado por el scanner ya ha sido pasado con anterioridad
			 * en la presente recepcion lo que hacemos es incrementar el numero de unidades
			 * en la tabla
			 */
			boolean repetido = false;
			int i = 0;
			/*for(MedicamentoAbstracto m : recepcionandoPedido.getDataProvider().getList())
			{
				if(result.getCodigoNacional().equals(m.getCodigoNacional()))
				{
					recepcionandoPedido.getDataProvider().getList().get(i).setUnidadesRecibidas(
							recepcionandoPedido.getDataProvider().getList().get(i).getUnidadesRecibidas() + 1);
					repetido = true;
					this.refrescar();
				}
				i++;
			}*/
			
			/* Si no esta repetido lo añadimos a la tabla con numero de unidades = 1 */
			if(!repetido)
			{
				MedicamentoAbstracto medicamentoPedido = new MedicamentoAbstracto();
				medicamentoPedido.setCodigoNacional(result.getCodigoNacional());
				medicamentoPedido.setNombre(result.getNombre());
				/*medicamentoPedido.setUnidadesRecibidas(1);
				recepcionandoPedido.addMedicament(medicamentoPedido);*/
				this.refrescar();
			}
	}
	
	public void refrescar(){
		this.recepcionandoPedido.refreshDisplays();
	}
	
	public void getPedidoConcreto(final int idInterno)
	{
		this.servicioGestionPedido.getPedidoFull(idInterno, new AsyncCallback<Pedido>() 
		{
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("CLIENT ERROR: RPC-GET-PEDIDOSFULL");				
			}

			@Override
			public void onSuccess(Pedido result) 
			{
				System.out.println("CLIENT OK: RPC-GET-PEDIDOSFULL");
				if(result!=null)
				{
					/* Si estamos recepcionando otro pedido, limpiamos la tabla de recepcion */
					if(result.getIdInterno()!=lastShowedOrder)
					{
						recepcionandoPedido.getDataProvider().getList().clear();
						refrescar();
					}
				/*	boolean valid = view.getTablaInfoPedidoWidget().setDataTabla(result.getListaMedicamentos());
					if(valid)
						lastShowedOrder = result.getIdInterno();*/
				}
				//else
					System.out.println("Cliente error: pedido no encontrado");	
			}	
		});
	}
	
	class ConfirmButtonHandler implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) 
		{	
		/*	List<MedicamentoPedido> listaMedicamentosPedido = new ArrayList<MedicamentoPedido>();
			for(MedicamentoPedido m : recepcionandoPedido.getDataProvider().getList()){
				MedicamentoPedido mPedido = m;
						listaMedicamentosPedido.add(mPedido);
			}*/
			/*servicioGestionPedido.recepcionarPedido(lastShowedOrder, listaMedicamentosPedido, new AsyncCallback<List<Medicamento>>() 
			{
				@Override
				public void onFailure(Throwable caught){
					System.out.println("Cliente error: rpc recepcion pedido");
				}

				@Override
				public void onSuccess(List<Medicamento> result) {
					System.out.println("Cliente ok: rpc recepcion pedido");
				}
			});*/
		}
	}
}
