package com.farmacloud.client.gui.pharmaUsers;

import java.util.List;

import com.farmacloud.client.presenter.pharmaUsers.RecepcionPresenter;
import com.farmacloud.client.ui.widgetsold.InfoMedicamentoView;
import com.farmacloud.client.ui.widgetsold.TablaInfoPedidoView;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.client.ui.Widget;

public interface RecepcionView {
	InfoMedicamentoView getInfoMedicamentoView();
	TablaInfoPedidoView getTablaInfoPedidoWidget();
	AbstractCellTable getTablaRecepcion();
	HasClickHandlers getConfirmButton();
	void setPresenter(Presenter recepcionPresenter);
	Widget asWidget();
	
	public interface Presenter extends InfoMedicamentoView.Presenter{
		void refrescar();
	}
}
