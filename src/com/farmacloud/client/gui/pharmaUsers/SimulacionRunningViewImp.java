package com.farmacloud.client.gui.pharmaUsers;

import com.farmacloud.shared.model.simulacion.MedicamentoFarmaciaSimulacion;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

public class SimulacionRunningViewImp extends Composite implements SimulacionRunningView 
{
	Presenter presenter;
	CellTable<MedicamentoFarmaciaSimulacion> table;	
	//SimulacionRunningDataProvider dataProvider;
	SingleSelectionModel<MedicamentoFarmaciaSimulacion> selectionModel;
	ScrollPanel scroll;
	
	public SimulacionRunningViewImp()
	{
		VerticalPanel vPanel = new VerticalPanel();
		
	    table = new CellTable<MedicamentoFarmaciaSimulacion>();
	    table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	    prepararTabla();
	    
	    // Add a selection model to handle user selection.
	    selectionModel= new SingleSelectionModel<MedicamentoFarmaciaSimulacion>();
	    table.setSelectionModel(selectionModel);

	    /* Creamos y añadimos el scroll a la tabla */
	    scroll = new ScrollPanel(table);
	    scroll.setWidth("100%");
	    scroll.setHeight("20em");
	    vPanel.add(scroll);
	    
	    /* Creamos un DataProvider */ 
	    //dataProvider = new SimulacionRunningDataProvider();
	   // dataProvider.addDataDisplay(table);
	    
		initWidget(vPanel);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	//@Override
	/*public SimulacionRunningDataProvider getDataProvider(){
		return this.dataProvider;
	}*/
	
	public void prepararTabla() 
	{
		TextColumn<MedicamentoFarmaciaSimulacion> codigoColumn = new TextColumn<MedicamentoFarmaciaSimulacion>() {
			@Override
			public String getValue(MedicamentoFarmaciaSimulacion object) {
				return object.getCodigoNacional();
			}
	      };
	    table.addColumn(codigoColumn, "Codigo nacional");

	  TextColumn<MedicamentoFarmaciaSimulacion> disponiblesColum = new TextColumn<MedicamentoFarmaciaSimulacion>() {
	         @Override
	         public String getValue(MedicamentoFarmaciaSimulacion object) {
	            return String.valueOf(object.getNumDisponibles());
	         }
	      };
	    table.addColumn(disponiblesColum, "Disponibles");

	    
	    TextColumn<MedicamentoFarmaciaSimulacion> vendidosColum = new TextColumn<MedicamentoFarmaciaSimulacion>(){
	         @Override
	         public String getValue(MedicamentoFarmaciaSimulacion object) {
	            return String.valueOf(object.getContadorVentas());
	         }
	      };
	    table.addColumn(vendidosColum, "Vendidos");

	    TextColumn<MedicamentoFarmaciaSimulacion> mediaColum = new TextColumn<MedicamentoFarmaciaSimulacion>(){
	         @Override
	         public String getValue(MedicamentoFarmaciaSimulacion object) {
	            return String.valueOf(object.getParametro());
	         }
	      };
	    table.addColumn(mediaColum, "Media");
	    
	    /*
	    TextColumn<MedicamentoFarmaciaSimulacion> pvpColumn = new TextColumn<MedicamentoFarmaciaSimulacion>(){
	         @Override
	         public String getValue(MedicamentoFarmaciaSimulacion object) {
	            return String.valueOf(object.getPrecioPVP());
	         }
	      };
	    table.addColumn(pvpColumn, "PVP");*/
	}
	
}