package com.farmacloud.client.ui.widgetsold;

import java.util.List;

import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.SingleSelectionModel;

public class TablaInfoPedidoViewImp extends Composite implements TablaInfoPedidoView
{
	private Presenter presenter;
	
	/* Tabla con los medicamentos del pedido */
	private CellTable<MedicamentoAbstracto> tablaMedicamentosPedido;
	private SingleSelectionModel<MedicamentoAbstracto> selectionModelPedido;
	private ScrollPanel scrollMedicamentosPedido;
	
	public TablaInfoPedidoViewImp()
	{
		SimplePanel sPanel = new SimplePanel();
		
	     /*		T A B L A		*/
		
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
	    sPanel.add(scrollMedicamentosPedido);
	            
	    /* Añadimos estilo propio */
	    scrollMedicamentosPedido.setStylePrimaryName("scrollPanel");
	    
	    /* Iniciamos el widget */
	    initWidget(sPanel);
	}
	
	@Override
	public CellTable<MedicamentoAbstracto> getTablaPedido() {
		return this.tablaMedicamentosPedido;
	}

	@Override
	public SingleSelectionModel getSelectionModel() {
		return this.selectionModelPedido;
	}
	
	@Override
	public boolean setDataTabla(List<MedicamentoAbstracto> data) 
	{
		if(!data.isEmpty())
		{
	        // Set the total row count. This isn't strictly necessary,
	        // but it affects paging calculations, so its good habit to
	        // keep the row count up to date.
		    tablaMedicamentosPedido.setPageSize(data.size());
	        tablaMedicamentosPedido.setRowCount(data.size(), true);
	        System.out.println("TAMAÑO DATOS " +data.size());
	        // Push the data into the widget.
	        tablaMedicamentosPedido.setRowData(0, data);
	        //this.recepcionar.setEnabled(true);
	        return true;
		}
		else 
		{
			//this.recepcionar.setEnabled(false);
			return false;
		}
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
	    
		TextColumn<MedicamentoAbstracto> numUnidades = new TextColumn<MedicamentoAbstracto>() {
			@Override
			public String getValue(MedicamentoAbstracto object) {
				//return String.valueOf(object.getUnidadesPedidas());
				return "";
			}
	      };
	    tablaMedicamentosPedido.addColumn(numUnidades, "Unidades");

	    /*
	   TextColumn<MedicamentoInfo> nameColumn = new TextColumn<MedicamentoInfo>() {
	         @Override
	         public String getValue(MedicamentoInfo object) {
	            return object.getNombre();
	         }
	      };
	    tablaMedicamentosPedido.addColumn(nameColumn, "Nombre");

	    
	    TextColumn<MedicamentoInfo> laboratorioColumn = new TextColumn<MedicamentoInfo>(){
	         @Override
	         public String getValue(MedicamentoInfo object) {
	            return object.getLaboratorio();
	         }
	      };
	    tablaMedicamentosPedido.addColumn(laboratorioColumn, "Laboratorio");

	    
	    TextColumn<MedicamentoInfo> principioColumn = new TextColumn<MedicamentoInfo>(){
	         @Override
	         public String getValue(MedicamentoInfo object) {
	            return object.getPrincipioActivo();
	         }
	      };
	    tablaMedicamentosPedido.addColumn(principioColumn, "Principio activo");
	    
	    
	    TextColumn<MedicamentoInfo> pvpColumn = new TextColumn<MedicamentoInfo>(){
	         @Override
	         public String getValue(MedicamentoInfo object) {
	            return String.valueOf(object.getPrecioPVP());
	         }
	      };
	    tablaMedicamentosPedido.addColumn(pvpColumn, "PVP");*/
	}
}
