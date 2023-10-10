package com.farmacloud.client.ui.widgets.tables;

import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class TablaSugerenciasMedicamentos extends SuggestedCell<MedicamentoAbstracto>
{
	private static TablaSugerenciasMedicamentosUiBinder uiBinder = GWT.create(TablaSugerenciasMedicamentosUiBinder.class);

	@UiTemplate("SuggestedCell.ui.xml")
	interface  TablaSugerenciasMedicamentosUiBinder extends UiBinder<Widget, TablaSugerenciasMedicamentos> {}
	
	public TablaSugerenciasMedicamentos() {
		initWidget(uiBinder.createAndBindUi(this));	
		initTable();
		initColumns();
	}

	@Override
	protected void initColumns() 
	{
		TextColumn<MedicamentoAbstracto> codigoColumn = new TextColumn<MedicamentoAbstracto>() {
			@Override
			public String getValue(MedicamentoAbstracto object) {
				return object.getCodigoNacional();
			}
		};
		table.addColumn(codigoColumn, "Codigo nacional");
		table.setColumnWidth(codigoColumn, "12%");
		
		TextColumn<MedicamentoAbstracto> nameColumn = new TextColumn<MedicamentoAbstracto>() {
		   @Override
		   public String getValue(MedicamentoAbstracto object) {
		      return object.getNombre();
		   }
		};
		table.addColumn(nameColumn, "Nombre");
		table.setColumnWidth(nameColumn, "30%");

		TextColumn<MedicamentoAbstracto> laboratorioColumn = new TextColumn<MedicamentoAbstracto>(){
		   @Override
		   public String getValue(MedicamentoAbstracto object) {
		      return object.getLaboratorio();
		   }
		};
		table.addColumn(laboratorioColumn, "Laboratorio");
		table.setColumnWidth(laboratorioColumn, "25%");

		TextColumn<MedicamentoAbstracto> principioColumn = new TextColumn<MedicamentoAbstracto>(){
		   @Override
		   public String getValue(MedicamentoAbstracto object) {
		      return object.getPrincipioActivo();
		   }
		};
		table.addColumn(principioColumn, "Principio activo");
		table.setColumnWidth(principioColumn, "21%");
		
		TextColumn<MedicamentoAbstracto> pvpColumn = new TextColumn<MedicamentoAbstracto>(){
		   @Override
		   public String getValue(MedicamentoAbstracto object) {
		      return String.valueOf(object.getPrecioPVP());
		   }
		};
		table.addColumn(pvpColumn, "PVP");
		table.setColumnWidth(pvpColumn, "12%");
		
	}

}
