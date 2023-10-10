package com.farmacloud.client.gui.loggedUser;

import com.farmacloud.client.presenter.LoggedHeaderPresenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface LoggedHeaderView extends IsWidget{
	HasClickHandlers getExitButton();
	void setUserName(String name);
	public void setPresenter(LoggedHeaderPresenter presenter);
	Widget asWidget();
	
	public interface Presenter{
	}
}
