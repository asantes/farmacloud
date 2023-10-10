package com.farmacloud.client.ui.editors;

import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.TextBox;

import com.farmacloud.shared.validation.UsuarioForm;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EditorUsuarioForm extends Composite implements Editor<UsuarioForm>{

	private static EditorUsuarioFormUiBinder uiBinder = GWT.create(EditorUsuarioFormUiBinder.class);

	interface EditorUsuarioFormUiBinder extends UiBinder<Widget, EditorUsuarioForm> {
	}
	
	@UiField
	TextBox usuario;
	@UiField
	Input contraseña;
	@UiField
	TextBox email;

	public EditorUsuarioForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
