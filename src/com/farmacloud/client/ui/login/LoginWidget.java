package com.farmacloud.client.ui.login;

import org.gwtbootstrap3.client.ui.Button;

import com.farmacloud.client.ui.editors.EditorCredenciales;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class LoginWidget extends Composite {

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	interface LoginUiBinder extends UiBinder<HTMLPanel, LoginWidget> {
	}

	@UiField
	EditorCredenciales editorCredenciales;
	
	public LoginWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public EditorCredenciales getEditorCredenciales() {
		return editorCredenciales;
	}

	public Button getLogin() {
		return new Button();
	}

}
