package com.farmacloud.client.gui.loggedUser;

import com.farmacloud.client.presenter.LoggedHeaderPresenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class LoggedHeaderViewImp extends Composite implements LoggedHeaderView{

	LoggedHeaderPresenter presenter;
	HorizontalPanel hPanel;
	
	Label nameUser;
	Button cerrarSesion;
	
	public LoggedHeaderViewImp() {
		hPanel = new HorizontalPanel();
		
		nameUser = new Label();
		hPanel.add(nameUser);
		
		cerrarSesion = new Button("Salir");
		hPanel.add(cerrarSesion);
		
		initWidget(hPanel);
		hPanel.setStylePrimaryName("loggedHeader");
		
	}
	
	@Override
	public HasClickHandlers getExitButton() {
		return this.cerrarSesion;
	}

	@Override
	public void setPresenter(LoggedHeaderPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setUserName(String name) {
		this.nameUser.setText(name);
		
	}

}
