package com.farmacloud.client.ui.registroPharma;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.gwtbootstrap3.client.ui.Button;

import com.farmacloud.client.ui.editors.EditorUsuarioPharma;
import com.farmacloud.shared.validation.UsuarioPharmaForm;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RegistroPharmaViewImp extends Composite implements RegistroPharmaView 
{
	Logger log = Logger.getLogger(RegistroPharmaViewImp.class.getSimpleName());
	
	private static RegistroPharmaViewUiBinder uiBinder = GWT
			.create(RegistroPharmaViewUiBinder.class);

	interface RegistroPharmaViewUiBinder extends UiBinder<Widget, RegistroPharmaViewImp> {}
	
	interface Driver extends SimpleBeanEditorDriver<UsuarioPharmaForm, EditorUsuarioPharma> {}
	
 	private static final Driver driver = GWT.create(Driver.class);
 	
 	Presenter presenter;
 	
 	@UiField
 	EditorUsuarioPharma editorUsuarioPharma;
 	@UiField
 	Button send;

	public RegistroPharmaViewImp() {
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(editorUsuarioPharma);
		//driver.edit(new usuarioForm());
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter; 
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		driver.edit(new UsuarioPharmaForm());
	}
	
	@UiHandler("send")
	void onSaveUser(ClickEvent event)
	{
		UsuarioPharmaForm usuarioPharmaForm = driver.flush();
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<UsuarioPharmaForm>> violations = validator.validate(usuarioPharmaForm, Default.class);
		
		if (violations.size() > 0) {
			driver.setConstraintViolations(new ArrayList<ConstraintViolation<?>>(violations));
		}
		
		if (!driver.hasErrors()) {
			presenter.onSaveUser(usuarioPharmaForm);
		}
		else
		{
			List<EditorError> listErrors = driver.getErrors();
			for(EditorError e: listErrors)
				log.info(e.getMessage());
		}
	}
}

