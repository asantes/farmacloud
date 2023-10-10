package com.farmacloud.client.gui.pharmaUsers;

import java.util.List;

import com.farmacloud.client.gui.helpers.RPCSuggestOracle;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.farmacloud.shared.model.infoView.MedicamentoDataProvider;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionModel;

public interface SimularView {
   
	HasClickHandlers getSimular();
	HasClickHandlers getAñadir();
	void setDataTable(List<MedicamentoAbstracto> result);
	void setDataSliceTable(List<MedicamentoAbstracto> result);

	RPCSuggestOracle getOracle();
	String getParamentro();
	String getSockMaximo();
	String getCodigo();
	String getPeriocidad();
	SimplePager getPager();
	SuggestBox getSuggestBox();
	MedicamentoDataProvider getMedicamentoDataProvider();
	SelectionModel getSelectionModel();
	CellTable getTable();
	
	void setPresenter(Presenter presenter);
	void setPresenterOnDataProvider();
	Widget asWidget();
	
	public interface Presenter{
		void setSelectedRowAfterRangeChanged(boolean exito, int row);
	}
}
