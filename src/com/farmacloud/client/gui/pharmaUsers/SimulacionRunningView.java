package com.farmacloud.client.gui.pharmaUsers;

import com.farmacloud.shared.model.infoView.SimulacionRunningDataProvider;
import com.google.gwt.user.client.ui.Widget;

public interface SimulacionRunningView {
	
	//SimulacionRunningDataProvider getDataProvider();
	void setPresenter(Presenter presenter);
	Widget asWidget();
	
	public interface Presenter{
	}
}
