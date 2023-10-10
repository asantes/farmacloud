package com.farmacloud.client.presenter.pharmaUsers;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.gui.pharmaUsers.PedidosAñadirView;
import com.farmacloud.client.presenter.Presenter;
import com.farmacloud.client.services.ServicioGestionPedido;
import com.farmacloud.client.services.ServicioGestionPedidoAsync;
import com.farmacloud.client.services.ServicioGestionProveedor;
import com.farmacloud.client.services.ServicioGestionProveedorAsync;
import com.farmacloud.shared.model.Pedido;
import com.farmacloud.shared.model.Proveedor;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.farmacloud.shared.model.infoView.ConstruyendoPedido;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;


public class PedidosAñadirPresenter extends AbstractActivity implements Presenter
{
	private ClientFactory clientFactory;
	private PedidosAñadirView view;
	private List<HandlerRegistration> registrations = new ArrayList();
	private final ServicioGestionProveedorAsync servicioGestionProveedor = GWT.create(ServicioGestionProveedor.class);
	private final ServicioGestionPedidoAsync servicioGestionPedido= GWT.create(ServicioGestionPedido.class);
	
	/* Listado de proveedores disponibles en el sistema. Se actualiza
	 * en el metodo start()
	 */
	private List<String> proveedores;
	
	/* Catalogo de medicamentos a mostrar del Proveedor seleccionado. Si
	 * se selecciona otro proveedor se pedira al servidor su catalogo y se mostrara
	 */
	private List<MedicamentoAbstracto> catalogoProveedor;
	private ConstruyendoPedido construyendoPedido;
	
	/* Controlamos cual ha sido el ultimo proveedor seleccionado,
	 * para en caso de clickear el mismo no volver a cargar la tabla again 
	 */
	private int proveedorSeleccionado = 0;
	private int ultimoProveedorSeleccionado = 0;
	
	public PedidosAñadirPresenter(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.construyendoPedido = new ConstruyendoPedido();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		this.view = clientFactory.getPedidosAñadirView();
		this.view.setPresenter(this);
		
		/*	R E G I S T R A T I O N S	*/
		this.registrations.add(this.view.getListProveedores().addClickHandler(new ListadoProveedoresHandler()));
		this.registrations .add(this.view.getConfirmButton().addClickHandler(new ConfirmButtonHandler()));
		this.registrations.add(this.view.getTablaCatalogoProveedores().addDomHandler(new TablaCatalogoDoubleClickHandler(),DoubleClickEvent.getType()));
		this.registrations.add(this.view.getTablaCatalogoProveedores().addCellPreviewHandler(new TablaCatalogoProveedorPreviewHandler()));
		this.registrations.add(this.view.getTablaMedicamentosPedido().addCellPreviewHandler(new TablaCatalogoMedicamentosPreviewHandler()));
	    
	    construyendoPedido.addDataDisplay(view.getTablaMedicamentosPedido());
	    
	//	this.servicioGestionProveedor.listarProveedores(new ListadoProveedoresCallback());
		panel.setWidget(view.asWidget());
	}
	
	@Override
	public void onStop()
	{
		for (HandlerRegistration registration : registrations) {
		      registration.removeHandler();
		}
		construyendoPedido.getDataProvider().removeDataDisplay(view.getTablaMedicamentosPedido());
	}

	@Override
	public void goTo(Place place){
		this.clientFactory.getPlaceController().goTo(place);	
	}
	
	class TablaCatalogoProveedorPreviewHandler implements CellPreviewEvent.Handler<MedicamentoAbstracto>
	{
		@Override
		public void onCellPreview(CellPreviewEvent<MedicamentoAbstracto> event) 
		{
			 if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_UP || event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DOWN)
			    {
		            view.getSelectionModeProveedor().setSelected(catalogoProveedor.get(view.getTablaCatalogoProveedores().getKeyboardSelectedRow()), true);
			    }
			    
		    if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER && event.getNativeEvent().getType().equals("keydown"))
		    {
		    	  MedicamentoAbstracto selected = (MedicamentoAbstracto) view.getSelectionModeProveedor().getSelectedObject();
	              if (selected != null) 
	              {
	            	  MedicamentoAbstracto medicamentoPedido = creaMedicamentoPedido(selected);
	                 // construyendoPedido.addMedicament(medicamentoPedido);
	                  construyendoPedido.refreshDisplays(); 
	              }
		    }
		}
	}
	
	class TablaCatalogoMedicamentosPreviewHandler implements CellPreviewEvent.Handler<MedicamentoAbstracto>
	{
		@Override
		public void onCellPreview(CellPreviewEvent<MedicamentoAbstracto> event) 
		{
			 if( (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_UP && event.getNativeEvent().getType().equals("keydown"))
					 || (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DOWN && event.getNativeEvent().getType().equals("keydown")))
			    {
		            view.getSelectionModePedido().setSelected(
		            		construyendoPedido.getDataProvider().getList().get(view.getTablaMedicamentosPedido().getKeyboardSelectedRow()),true);
			    }
		 
			 if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE && event.getNativeEvent().getType().equals("keydown"))
			 {
				 MedicamentoAbstracto selected = (MedicamentoAbstracto) view.getSelectionModePedido().getSelectedObject();
			     if (selected != null) 
			     {
			        // construyendoPedido.removeMedicament(selected);
			         refrescar();
			     }		
		     }
		}
	}
	
	public void refrescar()
	{		
		/*this.construyendoPedido.refreshDisplays();
		List<MedicamentoPedido> l = construyendoPedido.getDataProvider().getList();
		for(MedicamentoPedido m: l)
		{
			System.out.println(m.getCodigoNacional()+m.getNombre()+" "+m.getUnidadesPedidas());
		} */
	}
	
	class TablaCatalogoDoubleClickHandler implements DoubleClickHandler
	{
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			 /* MedicamentoProveedor selected = (MedicamentoProveedor) view.getSelectionModeProveedor().getSelectedObject();
              if (selected != null) 
              {
            	  MedicamentoPedido medicamentoPedido = creaMedicamentoPedido(selected);
            	  construyendoPedido.addMedicament(medicamentoPedido);
            	  construyendoPedido.refreshDisplays();
              }*/
		}
	}
	
	public MedicamentoAbstracto creaMedicamentoPedido(MedicamentoAbstracto m){
		
		/*MedicamentoPedido medicamentoPedido = new MedicamentoPedido();
		medicamentoPedido.setCodigoNacional(m.getCodigoNacional());
		medicamentoPedido.setLaboratorio(m.getLaboratorio());
		medicamentoPedido.setNombre(m.getNombre());
		medicamentoPedido.setPrecioPVP(m.getPrecioPVP());
		medicamentoPedido.setPrincipioActivo(m.getPrincipioActivo());
		medicamentoPedido.setUnidadesPedidas(0);
		medicamentoPedido.setUnidadesRecibidas(0);
		
		return medicamentoPedido;*/
		return null;
	}

	class ListadoProveedoresHandler implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) 
		{
			proveedorSeleccionado = view.getProveedorSeleccionado(event);
			/* Si se ha seleccionado un proveedor de la lista y es distinto al ultimo clikeado,
			 * pedimos el listado al servidor
			 */
			if(proveedorSeleccionado!=-1 && proveedorSeleccionado!=ultimoProveedorSeleccionado)
			{
				/* Pedimos el catalogo del proveedor seleccionado */
				/*servicioGestionProveedor.getCatalogo(proveedores.get(proveedorSeleccionado), new CatalogoMedicamentosCallback());
				
				/* Como vamos a mostrar el catalogo de otro proveedor, limpiamos la anterior tabla de pedido */
				/*construyendoPedido.getDataProvider().removeDataDisplay(view.getTablaMedicamentosPedido());
				construyendoPedido.setDataProvider(new ListDataProvider<MedicamentoPedido>());
				construyendoPedido.getDataProvider().addDataDisplay(view.getTablaMedicamentosPedido());		 */
			}
		}
	}
	
	class ConfirmButtonHandler implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event)
		{
			/*Pedido pedido = new Pedido();
			pedido.setNombreProveedor(proveedores.get(ultimoProveedorSeleccionado));
			pedido.ini();
			for(MedicamentoPedido m: construyendoPedido.getDataProvider().getList())
				pedido.getListaMedicamentos().add(m);
			
			servicioGestionPedido.añadirPedido(pedido, new AsyncCallback<Void>() 
			{
				@Override
				public void onFailure(Throwable caught) {
					System.out.println("CLIENT ERROR: RPC-REALIZARPEDIDO");
					
				}

				@Override
				public void onSuccess(Void result) {
					System.out.println("CLIENT OK: RPC-REALIZARPEDIDO");
				}
			});*/
		}	
	}

	class ListadoProveedoresCallback implements AsyncCallback<List<Proveedor>>
	{
		@Override
		public void onFailure(Throwable caught) {
			System.out.println("CLIENTE ERROR: RPC-LISTADO-PROVEEDORES");
		}

		@Override
		public void onSuccess(List<Proveedor> result) 
		{
			System.out.println("CLIENTE OK: RPC-LISTADO-PROVEEDORES");
						
			if(result!=null)
			{
				proveedores = new ArrayList<String>();
				int i=0;
				for(Proveedor k : result)
				{
					proveedores.add(k.getNombre());
					i++;
				}
		
				view.setListaProveedores(proveedores);	
				
				/* Mostramos por defecto el catalogo del proveedor de indice 0 de la lista */
				//servicioGestionProveedor.getCatalogo(proveedores.get(0), new CatalogoMedicamentosCallback());
			}
		}
	}
	
	class CatalogoMedicamentosCallback implements AsyncCallback<List<MedicamentoAbstracto>>
	{
		@Override
		public void onFailure(Throwable caught) {
			System.out.println("CLIENTE ERROR: RPC-CATALOGO-PROVEEDOR-RECIBIDO");
		}

		@Override
		public void onSuccess(List<MedicamentoAbstracto> result) 
		{	
			System.out.println("CLIENTE OK: RPC-CATALOGO-PROVEEDOR-RECIBIDO");
			if(result!=null)
			{
				/* Pasamos a la tabla los datos que tiene que mostrar */
				catalogoProveedor = result;
				view.setDataTable(result);
				ultimoProveedorSeleccionado = proveedorSeleccionado;
			}
		}
	}
}
