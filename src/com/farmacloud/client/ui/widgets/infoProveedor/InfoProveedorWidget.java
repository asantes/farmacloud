package com.farmacloud.client.ui.widgets.infoProveedor;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Button;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class InfoProveedorWidget extends Composite implements InfoProveedorView {

	private static InfoPresenterWidgetUiBinder uiBinder = GWT.create(InfoPresenterWidgetUiBinder.class);

	interface InfoPresenterWidgetUiBinder extends UiBinder<Widget, InfoProveedorWidget> {}

	Presenter presenter;
	
	@UiField(provided=true)
	String nif;
	@UiField(provided=true)
	String name;
	@UiField(provided=true)
	String direccion;
	@UiField(provided=true)
	String telefono;
	@UiField(provided=true)
	String email;

	@UiField
	Anchor anchor;
	@UiField
	Button editar;

	public InfoProveedorWidget(String nif, String name, String direccion, String telefono, String email) {
		this.nif = nif;
		this.name = name;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("editar")
	public void onEdit(ClickEvent event){
		presenter.onEdit(nif);
	}
}
