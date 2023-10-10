package com.farmacloud.client.ui.noticias;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasAttachHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface EscribirNoticiaView extends IsWidget{
	
	String getTexto();
	String getTitular();
	
	HasClickHandlers getAñadirNoticia();
	HasClickHandlers getPreviewNoticia();
	HasAttachHandlers getPreviewWidget();
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
	
	public interface Presenter{
	}
}
