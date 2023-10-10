package com.farmacloud.client.ui.registro;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.gwtbootstrap3.client.ui.Button;

import com.farmacloud.client.ui.editors.EditorUsuarioForm;
import com.farmacloud.shared.validation.UsuarioForm;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RegistroViewImp extends Composite implements RegistroView 
{
	Logger logger = Logger.getLogger(RegistroViewImp.class.getSimpleName());
	
	private static RegistroViewUiBinder uiBinder = GWT.create(RegistroViewUiBinder.class);

	interface RegistroViewUiBinder extends UiBinder<Widget, RegistroViewImp> {}
	
	interface Driver extends SimpleBeanEditorDriver<UsuarioForm, EditorUsuarioForm> {}
	
 	private static final Driver driver = GWT.create(Driver.class);
 	
 	Presenter presenter;
 	Validator validator;
 	
 	@UiField
 	EditorUsuarioForm editorUsuarioForm;
 	@UiField
 	Button send;

	public RegistroViewImp() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(editorUsuarioForm);
		//driver.edit(new usuarioForm());
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter; 
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		driver.edit(new UsuarioForm());
	}
	
	@UiHandler("send")
	void onSaveUser(ClickEvent event)
	{
		UsuarioForm usuarioForm = driver.flush();
		
		Set<ConstraintViolation<UsuarioForm>> violations = validator.validate(usuarioForm, Default.class);
		
		if (violations.size() > 0) {
			driver.setConstraintViolations(new ArrayList<ConstraintViolation<?>>(violations));
		}
		
		if (!driver.hasErrors()) {
			presenter.onSaveUser(usuarioForm);
		}
	}
}

