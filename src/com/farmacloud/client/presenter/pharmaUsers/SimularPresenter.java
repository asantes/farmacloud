package com.farmacloud.client.presenter.pharmaUsers;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.places.SimulacionRunningPlace;
import com.farmacloud.client.services.ServicioGestionMedicamento;
import com.farmacloud.client.services.ServicioGestionMedicamentoAsync;
import com.farmacloud.client.services.ServicioGestionVenta;
import com.farmacloud.client.services.ServicioGestionVentaAsync;
import com.farmacloud.client.services.ServicioSimulacion;
import com.farmacloud.client.services.ServicioSimulacionAsync;
import com.farmacloud.client.ui.simulacion.SimularView;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.farmacloud.shared.model.simulacion.MedicamentoFarmaciaSimulacion;
import com.farmacloud.shared.model.simulacion.Simulacion;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class SimularPresenter<T> extends AbstractActivity implements SimularView.Presenter
{
	private SimularView view;
	private ClientFactory clientFactory;
	private List<HandlerRegistration> registrations = new ArrayList();
	private final ServicioGestionMedicamentoAsync servicioGestionMedicamento = GWT.create(ServicioGestionMedicamento.class);
	private final ServicioGestionVentaAsync servicioGestionVenta = GWT.create(ServicioGestionVenta.class);
	private final ServicioSimulacionAsync servicioSimulacion = GWT.create(ServicioSimulacion.class);
	private final ServicioSimulacionAsync servicioSimulacionCheck = GWT.create(ServicioSimulacion.class);
	
	private String lastCursor = "nada";
	private int row = -1;
	private List<MedicamentoAbstracto> listaMedicamentos = new ArrayList<MedicamentoAbstracto>();
	private List<MedicamentoFarmaciaSimulacion> listaMedicamentosASimular = new ArrayList<MedicamentoFarmaciaSimulacion>();
	
	public SimularPresenter(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{	
		this.view = clientFactory.getSimularView();
		this.view.setPresenter(this);
		//this.view.setPresenterOnDataProvider();
		
		/*	R E G I S T R A T I O N S	*/
		//this.registrations.add(this.view.getSimular().addClickHandler(new ManejadoraSimular()));
		//this.registrations.add(this.view.getAñadir().addClickHandler(new ManejadoraAñadir()));
		this.registrations.add(this.view.getHasSelectionHandlers().addSelectionHandler(new HasSelectionHandler()));
		
		if(listaMedicamentos.isEmpty()){
			this.servicioGestionMedicamento.getCatalogoMedicamentos(new GetCatalogoRellamada());
		}
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
    
    class GetCatalogoRellamada implements AsyncCallback<List<MedicamentoAbstracto>>
    {
		@Override
		public void onFailure(Throwable caught)
		{
			System.out.println("Client error: recibiendo Catalogo");
		}

		@Override
		public void onSuccess(List<MedicamentoAbstracto> result) 
		{
			System.out.println("Client ok: recibiendo Catalogo");
			
			if(!result.isEmpty()){
				listaMedicamentos = result;
				view.setDataTable(result);
			}
			else
				System.out.println("catalogo recibido vacio");
		}
    }
    
	class HasSelectionHandler implements SelectionHandler<SuggestOracle.Suggestion>
	{
		@Override
		public void onSelection(SelectionEvent<Suggestion> event) 
		{
			if(event.getSelectedItem()!=null)
			{
				String nombre = event.getSelectedItem().getReplacementString();
				System.out.println("se ha seleccionado la sugerencia --> "+nombre);
				
				MedicamentoAbstracto med = null;
				int index = -1;
				
				/* Obtenemos a partir de la sugerencia el objeto que queremos seleccionar en la tabla asi
				 * como el indice que ocupa en el modelo */
				List<MedicamentoAbstracto> lista = view.getFixedTableData();
				for(MedicamentoAbstracto m: lista){
					index++;
					if(m.getNombre().toLowerCase().equals(nombre)){
						med = m;
						break;
					}
				}

				if(med!=null && index!=-1){
					System.out.println("El nombre del med es "+med.getNombre()+" y el indice --> "+index);
					view.selectSuggestionInFixedCell(index, med);
				}
				else GWT.log("Objeto no encontrado en la tabla");
				//servicioGestionMedicamento.getPositionInCatalogue(nombre, new GetPositionCallback());
			}
		}	
	}    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	class GetPositionCallback implements AsyncCallback<Integer>
	{
		@Override
		public void onSuccess(Integer result) 
		{
			if(result!=-1)
			{
				int rangoStart = 0; 
				int decenas = 0;
				
				int position = result;
				String positionString = new Integer(result).toString();
				
				if(positionString.length()==1 || positionString.length()==2)
					rangoStart = 100;
				else if(positionString.length()>2)
					{
						decenas = position%100;
						rangoStart = position - decenas;
					}
			
				System.out.println("Me sale un rangostart de "+rangoStart +" y unas decenas de "+decenas);
				/* Obtenemos el numero de la pagina que tiene que cargar */
				int numPages = view.getPager().getPageCount();
				int pageToChange = rangoStart/100;
				
				/* Cambiamos a la pagina correspondiente. Al cambiar la pagina, se lanza un evento que provocara
				 * una llamada RCP para recuperar las filas a mostrar de la bbdd
				 */
				row = decenas;
				view.getMedicamentoDataProvider().setRow(row);
				view.getPager().setPage(pageToChange); // OnRageChanged event fired	
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			System.out.println("Client error:  buscando posicion");
		}
	}
	
	@Override
	public void setSelectedRowAfterRangeChanged(boolean exito, int _row) 
	{
		if(exito && (row == _row))
		{
			int cuenta = view.getPager().getPage() * view.getPager().getPageSize();
			/* Seleccionamos en la tabla el elemento seleccionado de las sugerencias */
			view.getTable().setKeyboardSelectedRow(row, true);
			System.out.println("Selecionamoes la colimna "+view.getTable().getKeyboardSelectedRow());
			view.getSelectionModel().setSelected(view.getMedicamentoDataProvider().getList().get(view.getTable().getKeyboardSelectedRow()+cuenta), true);
		}
		row = -1;
	}
	
	
    class ManejadoraAñadir implements ClickHandler
    {
    	@Override
		public void onClick(ClickEvent event) 
    	{
    		boolean repetido = false;
			String stockMaximo = view.getSockMaximo();
			String parametro = view.getParamentro();
			String codigoNacional = view.getCodigo();
			
			if(stockMaximo!="" && parametro!="")
			{
				for(MedicamentoFarmaciaSimulacion m : listaMedicamentosASimular)
					if(m.getCodigoNacional().equals(codigoNacional))
						repetido = true;
				
				if(!repetido)
				{
					MedicamentoFarmaciaSimulacion med = new MedicamentoFarmaciaSimulacion();
					med.setCodigoNacional(codigoNacional);
					med.setParametro(Integer.parseInt(parametro));
					med.setStockMaximo(Integer.parseInt(stockMaximo));
					med.setContadorVentas(0);
					med.setNumDisponibles(0);
					med.setContadorVentasTiempo(0);
					med.setMedia(0.0);
					listaMedicamentosASimular.add(med);
				}
			}
		}	
    }
    
    /*class ManejadoraSimular implements ClickHandler
    {
		@Override
		public void onClick(ClickEvent event) 
		{
			String periocidad = view.getPeriocidad();
			
			if(periocidad!="" && !listaMedicamentosASimular.isEmpty())
			{
				Simulacion simulacion = new Simulacion();
				simulacion.setFrecuenciaSimulacion(Integer.parseInt(periocidad));
				simulacion.setFrecuenciaLlegadaPedidos(simulacion.getFrecuenciaSimulacion()*3);
				
				List<MedicamentoFarmaciaSimulacion> listaAux = new ArrayList<MedicamentoFarmaciaSimulacion>();
				for(MedicamentoFarmaciaSimulacion m : listaMedicamentosASimular)
					listaAux.add(m);
				
				servicioSimulacion.crearNuevaSimulacion(simulacion, listaAux, new AsyncCallback<Simulacion>() 
				{
					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Cliente fallo: creando simulacion");
					}

					@Override
					public void onSuccess(Simulacion result)
					{
						System.out.println("Cliente ok: creando simulacion");
						if(result!=null)
						{
							System.out.println(result.getFrecuenciaSimulacion()+result.getListaMedicamentoFarmaciaSimulacion().get(0).getCodigoNacional());
							listaMedicamentosASimular.clear();
							simular(result);
						}
					}
				});
			}
		}
    }*/
    
    class ManejadoraSimular implements ClickHandler
    {
		@Override
		public void onClick(ClickEvent event) 
		{
			String periocidad = view.getPeriocidad();
			
			if(periocidad!="" && !listaMedicamentosASimular.isEmpty())
			{
				Simulacion simulacion = new Simulacion();
				simulacion.setFrecuenciaSimulacion(Integer.parseInt(periocidad));
				simulacion.setFrecuenciaLlegadaPedidos(simulacion.getFrecuenciaSimulacion()*3);
				
				List<MedicamentoFarmaciaSimulacion> listaAux = new ArrayList<MedicamentoFarmaciaSimulacion>();
				for(MedicamentoFarmaciaSimulacion m : listaMedicamentosASimular)
					listaAux.add(m);
				
				SimulacionRunningPlace simulacionRunningPlace = new SimulacionRunningPlace("ole");
				simulacionRunningPlace.setSimulacion(simulacion);
				simulacionRunningPlace.setListaAux(listaAux);
				/* AQUI DEBERIAMOS COMPROBAR QUE NO HAY NIGUNA EN CURSO */
				System.out.println("antes de cambiar de place");
				goTo(simulacionRunningPlace);
				listaMedicamentosASimular.clear();
			}
		}
    }
}
