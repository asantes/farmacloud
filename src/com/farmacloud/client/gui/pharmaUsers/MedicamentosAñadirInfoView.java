package com.farmacloud.client.gui.pharmaUsers;

import java.util.List;

import com.farmacloud.client.ui.widgetsold.InfoMedicamentoView;
import com.farmacloud.client.ui.widgetsold.InfoMedicamentoViewImp;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface MedicamentosAñadirInfoView extends IsWidget
{
	InfoMedicamentoViewImp getInfoMedicamentoViewImp();
	HasClickHandlers getAddButton();
	List<String> getInputData();
	void setPresenter(Presenter presenter);
	Widget asWidget();
	
	public interface Presenter{
	}
}
