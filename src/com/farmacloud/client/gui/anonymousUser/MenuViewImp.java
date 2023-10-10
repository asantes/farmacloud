package com.farmacloud.client.gui.anonymousUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.gwtbootstrap3.client.ui.Button;

import com.farmacloud.client.ui.editors.EditorCredenciales;
import com.farmacloud.client.ui.login.LoginInteractionWidget;
import com.farmacloud.shared.validation.Credenciales;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MenuViewImp extends Composite implements MenuView{

	private static MenuViewImpUiBinder uiBinder = GWT
			.create(MenuViewImpUiBinder.class);

	interface MenuViewImpUiBinder extends UiBinder<Widget, MenuViewImp> {
	}
	
	interface DriverCredenciales extends SimpleBeanEditorDriver<Credenciales, EditorCredenciales> {
	}
	
 	private static final DriverCredenciales DRIVER = GWT.create(DriverCredenciales.class);
	
	@UiField
	LoginInteractionWidget loginInteraction;
	@UiField
	EditorCredenciales editorCredenciales;
	
	private Presenter presenter;
	private Button login;
	
	public MenuViewImp() 
	{
		initWidget(uiBinder.createAndBindUi(this));
		DRIVER.initialize(editorCredenciales);
		login = loginInteraction.getLogin();
		login.addClickHandler(new OnLoginClick());
	
	}

	@Override
	public HasClickHandlers getRegisterButton() {
		return null;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		DRIVER.edit(new Credenciales());
	}
	
	class OnLoginClick implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) 
		{
			Credenciales creds = DRIVER.flush();

			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<Credenciales>> violations = validator.validate(creds, Default.class);
			
			if (violations.size() > 0) {
				DRIVER.setConstraintViolations(new ArrayList<ConstraintViolation<?>>(violations));
			}
			
			if (!DRIVER.hasErrors()) {
				presenter.onLogin(creds, loginInteraction.getRecordarSesion().getValue());
			}
		}
	}
}
