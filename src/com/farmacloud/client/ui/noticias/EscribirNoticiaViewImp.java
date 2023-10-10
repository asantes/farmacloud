package com.farmacloud.client.ui.noticias;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.extras.summernote.client.ui.Summernote;
import org.gwtbootstrap3.extras.summernote.client.ui.base.SummernoteLanguage;

import com.farmacloud.client.ui.widgets.PopupPreview;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasAttachHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EscribirNoticiaViewImp extends Composite implements EscribirNoticiaView{

	private static EscribirNoticiaViewImpUiBinder uiBinder = GWT
			.create(EscribirNoticiaViewImpUiBinder.class);

	interface EscribirNoticiaViewImpUiBinder extends
			UiBinder<Widget, EscribirNoticiaViewImp> {
	}
	
	Presenter presenter;
	
	PopupPreview popUpPreview = new PopupPreview();
	@UiField
	Summernote summerNote;
	@UiField
	TextBox titular;
	@UiField
	Button preview;
	@UiField
	Button añadir;

	public EscribirNoticiaViewImp() {
		initWidget(uiBinder.createAndBindUi(this));
		summerNote.setLanguage(SummernoteLanguage.ES_ES);
		summerNote.reconfigure();
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public String getTitular() {
		return titular.getText();
	}
	
	@Override
	public String getTexto() {
		return summerNote.getCode();
	}
	
	@Override
	public HasClickHandlers getAñadirNoticia() {
		return añadir;
	}

	@Override
	public HasClickHandlers getPreviewNoticia() {
		return preview;
	}
	
	@Override
	public HasAttachHandlers getPreviewWidget() {
		return popUpPreview;
	}
}
