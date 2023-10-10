package com.farmacloud.client.ui.menu;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface MenuPharmaView extends IsWidget{

	void setPresenter(Presenter presenter);
	Widget asWidget();
	
	public interface Presenter{
		void logout();
	}
}
