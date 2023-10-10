package com.farmacloud.client.gui.anonymousUser;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.farmacloud.shared.validation.Credenciales;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface MenuView extends IsWidget{
	
	HasClickHandlers getRegisterButton();
	void setPresenter(Presenter presenter);
	Widget asWidget();
	
	public interface Presenter{
		void onLogin(Credenciales creds, boolean remember);
	}
}
