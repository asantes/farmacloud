package com.farmacloud.client.gui.pharmaUsers;

import com.farmacloud.client.ui.widgetsold.InfoMedicamentoView;
import com.farmacloud.client.ui.widgetsold.InfoMedicamentoViewImp;
import com.farmacloud.client.ui.widgetsold.TablaInfoPedidoView;
import com.farmacloud.client.ui.widgetsold.TablaInfoPedidoViewImp;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

public class RecepcionViewImp extends Composite implements RecepcionView
{
	private Presenter presenter;
	private InfoMedicamentoView infoMedicamentoView;
	private TablaInfoPedidoView  tablaInfoPedidoWidget;

	/* Tabla */
	private CellTable<MedicamentoAbstracto> tablaRecepcion;
	private SingleSelectionModel selectionRecepcion;
	private ScrollPanel scrollRecepcion;
	
	private Button confirmarRecepcion;
	
	public RecepcionViewImp()
	{
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		infoMedicamentoView = new InfoMedicamentoViewImp();
		vPanel.add(infoMedicamentoView);
		
		/*		T A B L A		*/
		/* Creamos la tabla con los medicamentos que forman el pedido */
	    tablaRecepcion= new CellTable<MedicamentoAbstracto>();
	    tablaRecepcion.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	    prepararTablaPedido();
	    
	    // Add a selection model to handle user selection.
	    selectionRecepcion = new SingleSelectionModel<MedicamentoAbstracto>();
	    tablaRecepcion.setSelectionModel(selectionRecepcion);
        
	    /* Creamos y añadimos el scroll a la tabla con los medicamentos que forman el pedido*/
	    scrollRecepcion = new ScrollPanel(tablaRecepcion);
	    scrollRecepcion.setWidth("100%");
	    scrollRecepcion.setHeight("20em");
	    
        /* Añadimos el scroll(con la tabla) al panel */
        vPanel.add(scrollRecepcion);
                
        /* Añadimos estilo propio */
        scrollRecepcion.setStylePrimaryName("scrollPanel");
        
        /* Tabla widget */
        tablaInfoPedidoWidget = new TablaInfoPedidoViewImp();
        vPanel.add(tablaInfoPedidoWidget);
		
        confirmarRecepcion = new Button("Confirmar recepcion");
        vPanel.add(confirmarRecepcion);
        
        initWidget(vPanel);
		//setStylePrimaryName("infoMedicamento");
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;	
	}
	
	public void prepararTablaPedido() 
	{

		TextColumn<MedicamentoAbstracto> codigoColumn = new TextColumn<MedicamentoAbstracto>() {
			@Override
			public String getValue(MedicamentoAbstracto object) {
				return object.getCodigoNacional();
			}
	      };
	    tablaRecepcion.addColumn(codigoColumn, "Codigo nacional");

	    
	    TextColumn<MedicamentoAbstracto> nameColumn = new TextColumn<MedicamentoAbstracto>() {
	         @Override
	         public String getValue(MedicamentoAbstracto object) {
	            return object.getNombre();
	         }
	      };
	    tablaRecepcion.addColumn(nameColumn, "Nombre");
	    
	    
	    Column<MedicamentoAbstracto, String> numUnidades = new Column<MedicamentoAbstracto, String>(
	        new EditTextCell()) {
	      @Override
	      public String getValue(MedicamentoAbstracto object) {
	       // return String.valueOf(object.getUnidadesRecibidas());
	    	  return "";
	      }
	    };
	    tablaRecepcion.addColumn(numUnidades, "Unidades");
	    
	    numUnidades.setFieldUpdater(new FieldUpdater<MedicamentoAbstracto, String>(){
	      @Override
	      public void update(int index, MedicamentoAbstracto object, String value) {
	        // Called when the user changes the value.
	       // object.setUnidadesRecibidas(Integer.parseInt(value));
	        presenter.refrescar();
	      }
	    });
	}

	@Override
	public AbstractCellTable getTablaRecepcion() {
		return this.tablaRecepcion;
	}

	@Override
	public InfoMedicamentoView getInfoMedicamentoView() {
		return this.infoMedicamentoView;
	}

	@Override
	public TablaInfoPedidoView getTablaInfoPedidoWidget() {
		return this.tablaInfoPedidoWidget;
	}

	@Override
	public HasClickHandlers getConfirmButton() {
		return this.confirmarRecepcion;
	}
}
