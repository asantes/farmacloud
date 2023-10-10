package com.farmacloud.client.gui.pharmaUsers;

import java.util.List;

import com.farmacloud.client.presenter.pharmaUsers.PedidosAñadirPresenter;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

public class PedidosAñadirViewImp extends Composite implements PedidosAñadirView
{
	private PedidosAñadirPresenter presenter;
	
	private VerticalPanel vPanel;
	private ListBox listaProveedores;
	private Label texto;
	private Button confirmarPedido;
	
	/* Tabla catalogo proveedores */
	private CellTable<MedicamentoAbstracto> table;
	private SingleSelectionModel<MedicamentoAbstracto> selectionModelProveedor;
	private ScrollPanel scroll;

	/* Tabla con los medicamentos del pedido */
	private CellTable<MedicamentoAbstracto> tablaMedicamentosPedido;
	private SingleSelectionModel<MedicamentoAbstracto> selectionModelPedido;
	private ScrollPanel scrollMedicamentosPedido;
	
	public PedidosAñadirViewImp() 
	{
		vPanel = new VerticalPanel();
		
		texto = new Label("Seleccione un proveedor");
		vPanel.add(texto);
		
		listaProveedores = new ListBox();
		vPanel.add(listaProveedores);
		
		/* Creamos la tabla-catalogo de medicamentos del Proveedor */
	    table = new CellTable<MedicamentoAbstracto>();
	    table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	    prepararTabla();
	    
	    // Add a selection model to handle user selection.
	    selectionModelProveedor = new SingleSelectionModel<MedicamentoAbstracto>();
	    table.setSelectionModel(selectionModelProveedor);

	    /* Creamos y añadimos el scroll a la tabla-catalogo de medicamentos del Proveedor */
	    scroll = new ScrollPanel(table);
	    scroll.setWidth("100%");
	    scroll.setHeight("20em");
	    
        /* Añadimos el scroll(con la tabla) al panel */
        vPanel.add(scroll);
        
		/* Creamos la tabla con los medicamentos que forman el pedido */
	    tablaMedicamentosPedido = new CellTable<MedicamentoAbstracto>();
	    tablaMedicamentosPedido.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	    prepararTablaPedido();
	    
	    // Add a selection model to handle user selection.
	    selectionModelPedido = new SingleSelectionModel<MedicamentoAbstracto>();
	    tablaMedicamentosPedido.setSelectionModel(selectionModelPedido);
        
	    /* Creamos y añadimos el scroll a la tabla con los medicamentos que forman el pedido*/
	    scrollMedicamentosPedido = new ScrollPanel(tablaMedicamentosPedido);
	    scrollMedicamentosPedido.setWidth("100%");
	    scrollMedicamentosPedido.setHeight("20em");
	    
        /* Añadimos el scroll(con la tabla) al panel */
        vPanel.add(scrollMedicamentosPedido);
                
        /* Añadimos estilo propio */
        scroll.setStylePrimaryName("scrollPanel");
        scrollMedicamentosPedido.setStylePrimaryName("scrollPanel");
        
        confirmarPedido = new Button("Confirmar pedido");
        vPanel.add(confirmarPedido);
        
		initWidget(vPanel);
		setStylePrimaryName("pedidosAñadir");	
	}
	
	@Override
	public void setPresenter(PedidosAñadirPresenter presenter) {
		this.presenter = presenter;	
	}
	
	@Override
	public void setListaProveedores(List<String> lista){
		listaProveedores.clear();
		for(String k : lista)
		{
				this.listaProveedores.addItem(k);
				System.out.println(k);
		}
	}
	
	@Override
	public AbstractCellTable getTablaCatalogoProveedores() {
		return this.table;
	}
	
	@Override
	public SingleSelectionModel getSelectionModeProveedor() {
		return this.selectionModelProveedor;
	}

	@Override
	public AbstractCellTable getTablaMedicamentosPedido() {
		return this.tablaMedicamentosPedido;
	} 
	
	@Override
	public SingleSelectionModel getSelectionModePedido() {
		return this.selectionModelPedido;
	}
	
	@Override
	public void setDataTable(List<MedicamentoAbstracto> data) 
	{
        // Set the total row count. This isn't strictly necessary,
        // but it affects paging calculations, so its good habit to
        // keep the row count up to date.
	    table.setPageSize(data.size());
        table.setRowCount(data.size(), true);
        System.out.println("TAMAÑO DATOS " +data.size());
        // Push the data into the widget.
        table.setRowData(0, data);
	}

	@Override
	public HasClickHandlers getListProveedores() {
		return this.listaProveedores;
	}
	
	@Override
	public HasClickHandlers getConfirmButton() {
		return this.confirmarPedido;
	}

	@Override
	public int getProveedorSeleccionado(ClickEvent event) {
		return this.listaProveedores.getSelectedIndex();
	}
	
	public void prepararTabla() 
	{
		TextColumn<MedicamentoAbstracto> codigoColumn = new TextColumn<MedicamentoAbstracto>() {
			@Override
			public String getValue(MedicamentoAbstracto object) {
				return object.getCodigoNacional();
			}
	      };
	    table.addColumn(codigoColumn, "Codigo nacional");

	    //
	   TextColumn<MedicamentoAbstracto> nameColumn = new TextColumn<MedicamentoAbstracto>() {
	         @Override
	         public String getValue(MedicamentoAbstracto object) {
	            return object.getNombre();
	         }
	      };
	    table.addColumn(nameColumn, "Nombre");

	    //
	    TextColumn<MedicamentoAbstracto> laboratorioColumn = new TextColumn<MedicamentoAbstracto>(){
	         @Override
	         public String getValue(MedicamentoAbstracto object) {
	            return object.getLaboratorio();
	         }
	      };
	    table.addColumn(laboratorioColumn, "Laboratorio");

	    //
	    TextColumn<MedicamentoAbstracto> principioColumn = new TextColumn<MedicamentoAbstracto>(){
	         @Override
	         public String getValue(MedicamentoAbstracto object) {
	            return object.getPrincipioActivo();
	         }
	      };
	    table.addColumn(principioColumn, "Principio activo");
	    
	    //
	    TextColumn<MedicamentoAbstracto> pvpColumn = new TextColumn<MedicamentoAbstracto>(){
	         @Override
	         public String getValue(MedicamentoAbstracto object) {
	            return String.valueOf(object.getPrecioPVP());
	         }
	      };
	    table.addColumn(pvpColumn, "PVP");
	}   
	
	public void prepararTablaPedido() 
	{
		TextColumn<MedicamentoAbstracto> codigoColumn = new TextColumn<MedicamentoAbstracto>() {
			@Override
			public String getValue(MedicamentoAbstracto object) {
				return object.getCodigoNacional();
			}
	      };
	    tablaMedicamentosPedido.addColumn(codigoColumn, "Codigo nacional");

	   TextColumn<MedicamentoAbstracto> nameColumn = new TextColumn<MedicamentoAbstracto>() {
	         @Override
	         public String getValue(MedicamentoAbstracto object) {
	            return object.getNombre();
	         }
	      };
	    tablaMedicamentosPedido.addColumn(nameColumn, "Nombre");
	    
	    
	    Column<MedicamentoAbstracto, String> numUnidades = new Column<MedicamentoAbstracto, String>(
	        new EditTextCell()) {
	      @Override
	      public String getValue(MedicamentoAbstracto object) {
	       // return String.valueOf(object.getUnidadesPedidas());
	    	  return "";
	      }
	    };
	    tablaMedicamentosPedido.addColumn(numUnidades, "Unidades");
	    
	    numUnidades.setFieldUpdater(new FieldUpdater<MedicamentoAbstracto, String>(){
	      @Override
	      public void update(int index, MedicamentoAbstracto object, String value) {
	        // Called when the user changes the value.
	     //   object.setUnidadesPedidas((Integer.parseInt(value)));
	        presenter.refrescar();
	      }
	    });
	}
}
