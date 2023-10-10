package com.farmacloud.client.ui.editors;

import org.gwtbootstrap3.client.ui.TextBox;

import com.farmacloud.shared.model.Proveedor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EditorProveedor extends Composite implements Editor<Proveedor>{

	private static EditorProveedorUiBinder uiBinder = GWT
			.create(EditorProveedorUiBinder.class);

	interface EditorProveedorUiBinder extends UiBinder<Widget, EditorProveedor> {
	}
	
	@UiField
	@Path(value="NIF")
    TextBox nif;
	@UiField
	TextBox nombre;
	@UiField
	TextBox direccion;
	@UiField
	TextBox telefono;
	@UiField
	TextBox email;

	public EditorProveedor() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
