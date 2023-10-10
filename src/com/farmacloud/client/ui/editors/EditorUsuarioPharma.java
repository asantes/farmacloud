package com.farmacloud.client.ui.editors;

import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.TextBox;

import com.farmacloud.shared.validation.UsuarioPharmaForm;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EditorUsuarioPharma extends Composite implements Editor<UsuarioPharmaForm>{
	
	private static EditorUsuarioPharmaUiBinder uiBinder = GWT
			.create(EditorUsuarioPharmaUiBinder.class);

	interface EditorUsuarioPharmaUiBinder extends
			UiBinder<Widget, EditorUsuarioPharma> {
	}

	@UiField
	TextBox usuario;
	@UiField
	Input contraseña;
	@UiField
	TextBox email;
	
	public EditorUsuarioPharma() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
