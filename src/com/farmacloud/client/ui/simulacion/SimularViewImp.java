package com.farmacloud.client.ui.simulacion;

import java.util.List;

import org.gwtbootstrap3.client.ui.gwt.CellTable;

import com.farmacloud.client.gui.helpers.RPCSuggestOracle;
import com.farmacloud.client.ui.widgets.tables.FixedPaginatedCellIterface;
import com.farmacloud.client.ui.widgets.tables.SuggestedCellInterface;
import com.farmacloud.client.ui.widgets.tables.TablaSugerenciasMedicamentos;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.farmacloud.shared.model.infoView.MedicamentoDataProvider;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionModel;

public class SimularViewImp extends Composite implements SimularView {

	private static SimularViewImpUiBinder uiBinder = GWT
			.create(SimularViewImpUiBinder.class);

	interface SimularViewImpUiBinder extends UiBinder<Widget, SimularViewImp> {
	}

	Presenter presenter;

	@UiField
	TablaSugerenciasMedicamentos tablaSugerencias;

	public SimularViewImp() {	
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	

	@Override
	public void setDataTable(List<MedicamentoAbstracto> result) {
		tablaSugerencias.setDataTable(result);
	}
	
	@Override
	public HasSelectionHandlers getHasSelectionHandlers() {
		return tablaSugerencias.getHasSelectionHandlers();
	}
	
	@Override
	public List<MedicamentoAbstracto> getFixedTableData(){
		return tablaSugerencias.getData();
	}
	
	@Override
	public void selectSuggestionInFixedCell(int index, MedicamentoAbstracto element){
		tablaSugerencias.selectRowAndPage(index, element);
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public HasClickHandlers getSimular() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HasClickHandlers getAñadir() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setDataSliceTable(List<MedicamentoAbstracto> result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RPCSuggestOracle getOracle() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getParamentro() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getSockMaximo() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getCodigo() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getPeriocidad() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SimplePager getPager() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public MedicamentoDataProvider getMedicamentoDataProvider() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SelectionModel getSelectionModel() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public CellTable getTable() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setPresenterOnDataProvider() {
		// TODO Auto-generated method stub
		
	}


}
