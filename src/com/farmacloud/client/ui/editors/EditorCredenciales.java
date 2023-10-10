package com.farmacloud.client.ui.editors;

import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.TextBox;

import com.farmacloud.client.ui.login.LoginInteractionWidget;
import com.farmacloud.shared.validation.Credenciales;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;

public class EditorCredenciales extends Composite implements Editor<Credenciales>{

	private static EditorCredencialesUiBinder uiBinder = GWT
			.create(EditorCredencialesUiBinder.class);

	interface EditorCredencialesUiBinder extends
			UiBinder<Form, EditorCredenciales> {
	}
	
	@UiField
	Form form;
	@UiField
	TextBox usuario;
	@UiField
	Input contraseña;

	public EditorCredenciales() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiChild(tagname="interaction")
	public void addLoginInteractionWidget(LoginInteractionWidget widget){
		form.add(widget);
	}
}
