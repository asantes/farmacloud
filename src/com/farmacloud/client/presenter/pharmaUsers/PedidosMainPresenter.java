package com.farmacloud.client.presenter.pharmaUsers;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.gui.pharmaUsers.PedidosMainView;
import com.farmacloud.client.gui.pharmaUsers.PedidosMainView.Presenter;
import com.farmacloud.client.places.RecepcionPlace;
import com.farmacloud.client.services.ServicioGestionPedido;
import com.farmacloud.client.services.ServicioGestionPedidoAsync;
import com.farmacloud.shared.model.Pedido;
import com.farmacloud.shared.model.Proveedor;
import com.farmacloud.shared.model.infoView.MedicamentoInfo;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.view.client.CellPreviewEvent;

public class PedidosMainPresenter extends AbstractActivity implements Presenter
{
	private ClientFactory clientFactory;
	private PedidosMainView view;
	private List<HandlerRegistration> registrations = new ArrayList();
	private final ServicioGestionPedidoAsync servicioGestionPedido= GWT.create(ServicioGestionPedido.class);

	private List<MedicamentoInfo> pedido;
	private long lastShowedOrder = -1;

	public PedidosMainPresenter(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.view = this.clientFactory.getPedidosMainView();
		this.view.setPresenter(this);
		this.registrations.add(this.view.getPendientesTree().addOpenHandler(new TreePendientesOpenHandler()));
		this.registrations.add(this.view.getIncompletosTree().addOpenHandler(new TreeIncompletosOpenHandler()));
		this.registrations.add(this.view.getPendientesTree().addSelectionHandler(new TreePendientesSelectionHandler()));
		this.registrations.add(this.view.getWidgetTablaPedido().getTablaPedido().addCellPreviewHandler(new TablaPedidoPreviewHandler()));
		this.registrations.add(this.view.getRecepcionarButton().addClickHandler(new ButtonRecepcionarHandler()));
		this.getNameProveedoresConPedidoNo("PENDIENTE");
		this.getNameProveedoresConPedidoNo("INCOMPLETO");
		panel.setWidget(view.asWidget());
	}
	
	@Override
	public void onStop(){
		for (HandlerRegistration registration : registrations) {
		      registration.removeHandler();
		}
	}
	
	@Override
	public void goTo(Place place) {
		this.clientFactory.getPlaceController().goTo(place);
	}

	public void getNameProveedoresConPedidoNo(final String tipo)
	{
		/*this.servicioGestionPedido.getProveedoresConPedidoNo(tipo, new AsyncCallback<List<Proveedor>>()
		{
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("CLIENT ERROR: RPC-GET-NAMEPROVEES");	
			}

			@Override
			public void onSuccess(List<Proveedor> result) 
			{
				System.out.println("CLIENT OK: RPC-GET-NAMEPROVEES");	
				if(!result.isEmpty())
				{
					System.out.println("Envio a vistas");
					if(tipo.equals("PENDIENTE"))
						view.setRootData(result, view.getPendientesTree());
					else if(tipo.equals("INCOMPLETO"))
							view.setRootData(result, view.getIncompletosTree());
				}
			}
		});*/
	}
	
	class TreePendientesOpenHandler implements OpenHandler<TreeItem>
	{
		@Override
		public void onOpen(OpenEvent<TreeItem> event) 
		{
            TreeItem itemSelected = event.getTarget();
		    if(itemSelected!=null)
		    {
		    	if(itemSelected.getUserObject() instanceof Proveedor)
		    	{
		    		Proveedor proveedor = (Proveedor)itemSelected.getUserObject();
			    	getPedidosNoCompletos(proveedor.getNombre(), "PENDIENTE");
		    	}
		    
		    	else if(itemSelected.getUserObject() instanceof Boolean)
		    		{
		    			//nothing yet
		    		}				
		    }	
		}
	}
	
	class TreeIncompletosOpenHandler implements OpenHandler<TreeItem>
	{
		@Override
		public void onOpen(OpenEvent<TreeItem> event) 
		{
            TreeItem itemSelected = event.getTarget();
		    if(itemSelected!=null)
		    {
		    	if(itemSelected.getUserObject() instanceof Proveedor)
		    	{
		    		Proveedor proveedor = (Proveedor)itemSelected.getUserObject();
		    		getPedidosNoCompletos(proveedor.getNombre(), "PENDIENTE");
		    	}
		    
		    	else if(itemSelected.getUserObject() instanceof Boolean)
		    		{
		    			//nothing yet
		    		}				
		    }	
		}
	}
	
	public void getPedidosNoCompletos(String nameProveedor, final String tipo)
	{
		this.servicioGestionPedido.getPedidosNoCompletos(nameProveedor, tipo, new AsyncCallback<List<Pedido>>()
		{
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("CLIENT ERROR: RPC-GET-PEDIDOSPENDIENTES");	
			}

			@Override
			public void onSuccess(List<Pedido> result) 
			{
				System.out.println("CLIENT OK: RPC-GET-PEDIDOSPENDIENTES");	
				if(!result.isEmpty())
				{
					if(tipo.equals("PENDIENTE"))
						view.setChildData(result, view.getPendientesTree());
					else if(tipo.equals("INCOMPLETO"))
							view.setChildData(result, view.getIncompletosTree());
				}
			}	
		});
	}
	
	class TreePendientesSelectionHandler implements SelectionHandler
	{
		@Override
		public void onSelection(SelectionEvent event) 
		{
			if(event.getSelectedItem()!=null)
			{
				TreeItem itemSelected = (TreeItem) event.getSelectedItem();
		
				if(itemSelected.getUserObject()!=null)
				{
					if(itemSelected.getUserObject() instanceof Pedido)
					{
						Pedido pedidoDetallar = (Pedido) itemSelected.getUserObject();
						System.out.println(+pedidoDetallar.getIdInterno());
				//		for(MedicamentoPedido m : pedidoDetallar.getListaMedicamentos())
						//	System.out.println(m.getCodigoNacional());
						//boolean valid = view.getWidgetTablaPedido().setDataTabla(pedidoDetallar.getListaMedicamentos());
						//if(valid)
							//lastShowedOrder = pedidoDetallar.getIdInterno();
					}
				}
			}
		}
	}
	
	/*public void getPedidoConcreto(final Pedido pedidoDTO)
	{
		servicioGestionPedido.getPedidoFull(pedidoDTO, new AsyncCallback<List<MedicamentoDTO>>() 
		{
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("CLIENT ERROR: RPC-GET-PEDIDOSFULL");				
			}

			@Override
			public void onSuccess(List<MedicamentoDTO> result) 
			{
				System.out.println("CLIENT OK: RPC-GET-PEDIDOSFULL");
				if(!result.isEmpty())
				{
					pedido = new ArrayList<MedicamentoInfo>();
					for(MedicamentoDTO mDTO : result)
					{
						MedicamentoInfo medicamentoInfo = new MedicamentoInfo();
						medicamentoInfo.setCodigoNacional(mDTO.getCodigoNacional());
						medicamentoInfo.setNumUnidades(mDTO.getNumUnidades());
						pedido.add(medicamentoInfo);
						System.out.println("CLIENTE " +medicamentoInfo.getCodigoNacional()+medicamentoInfo.getNumUnidades());

					}
					boolean valid = view.getWidgetTablaPedido().setDataTabla(pedido);
					if(valid)
						lastShowedOrder = pedidoDTO.getIdInterno();
				}	
			}	
		});
	}*/
	
	class TablaPedidoPreviewHandler implements CellPreviewEvent.Handler<MedicamentoInfo>
	{
		@Override
		public void onCellPreview(CellPreviewEvent<MedicamentoInfo> event) 
		{
			 if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_UP || event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DOWN)
			    {
		            view.getWidgetTablaPedido().getSelectionModel().setSelected(
		            		pedido.get(view.getWidgetTablaPedido().getTablaPedido().getKeyboardSelectedRow()), true);
			    }
			    
		    if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER && event.getNativeEvent().getType().equals("keydown")){
		    	
		    }
		}
	}
	
	class ButtonRecepcionarHandler implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) 
		{
			clientFactory.getPlaceController().goTo(new RecepcionPlace(String.valueOf(lastShowedOrder)));
		}
	}
}
