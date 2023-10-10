package com.farmacloud.client.gui.anonymousUser;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public interface NoticiasView {
	
	void buildAndAddNavigator(int indiceStart, int indiceEnd, int indiceActual);
	void cleanView();
	void setFooter(Widget footerNavigation);
	void showNoticia(String cuerpoNoticia, String titular, String autor, Date fecha);
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
	
	public interface Presenter{
		String getToken(int i);
	}
}
