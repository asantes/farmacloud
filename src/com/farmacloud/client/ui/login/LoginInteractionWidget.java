package com.farmacloud.client.ui.login;

import org.gwtbootstrap3.client.ui.Button;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LoginInteractionWidget extends Composite {

	private static LoginInteractionWidgetUiBinder uiBinder = GWT
			.create(LoginInteractionWidgetUiBinder.class);

	interface LoginInteractionWidgetUiBinder extends
			UiBinder<Widget, LoginInteractionWidget> {
	}

	@UiField
	Button login;
	@UiField 
	CheckBox recordarSesion;
	
	public LoginInteractionWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Button getLogin() {
		return login;
	}
	
	public CheckBox getRecordarSesion(){
		return recordarSesion;
	}
}
