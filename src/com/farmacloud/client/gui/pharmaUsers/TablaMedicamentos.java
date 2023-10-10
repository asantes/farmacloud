package com.farmacloud.client.gui.pharmaUsers;

import java.util.List;

import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

public class TablaMedicamentos extends Composite
{
	/*  */
	private CellTable<MedicamentoAbstracto> table;
	private SingleSelectionModel<MedicamentoAbstracto> selectionModel;
	private ScrollPanel scroll;

	public TablaMedicamentos()
	{
		VerticalPanel vPanel = new VerticalPanel();
		
		/* Creamos la tabla-catalogo de medicamentos del Proveedor */
	    table = new CellTable<MedicamentoAbstracto>();
	    table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	    prepararTabla();
	    
	    // Add a selection model to handle user selection.
	    selectionModel= new SingleSelectionModel<MedicamentoAbstracto>();
	    table.setSelectionModel(selectionModel);

	    /* Creamos y añadimos el scroll a la tabla */
	    scroll = new ScrollPanel(table);
	    scroll.setWidth("100%");
	    scroll.setHeight("20em");
	    
	    vPanel.add(scroll);
	    initWidget(vPanel);
	}
	
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
}
