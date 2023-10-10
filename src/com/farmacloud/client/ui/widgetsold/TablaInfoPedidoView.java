package com.farmacloud.client.ui.widgetsold;

import java.util.List;

import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;

public interface TablaInfoPedidoView extends IsWidget
{
	CellTable getTablaPedido();
	SingleSelectionModel getSelectionModel();
	boolean setDataTabla(List<MedicamentoAbstracto> list);
	
	//void setPresenter(Presenter presenter);
	Widget asWidget();
	
	public interface Presenter{
	}
}

