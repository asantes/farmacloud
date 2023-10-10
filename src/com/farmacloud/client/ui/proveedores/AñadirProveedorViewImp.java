package com.farmacloud.client.ui.proveedores;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.gwtbootstrap3.client.ui.Button;

import com.farmacloud.client.ui.editors.EditorProveedor;
import com.farmacloud.shared.model.Proveedor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AñadirProveedorViewImp extends Composite implements AñadirProveedorView
{
	Logger logger = Logger.getLogger(AñadirProveedorViewImp.class.getSimpleName());
	private static AñadirProveedorViewImpUiBinder uiBinder = GWT.create(AñadirProveedorViewImpUiBinder.class);
	interface AñadirProveedorViewImpUiBinder extends UiBinder<Widget, AñadirProveedorViewImp> {}
	
	interface Driver extends SimpleBeanEditorDriver<Proveedor, EditorProveedor> {}
 	private static final Driver driver = GWT.create(Driver.class);
	
 	Presenter presenter;
 	Validator validator;
 	
 	@UiField
 	EditorProveedor editorProveedor;
 	@UiField
 	Button send;

	public AñadirProveedorViewImp() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(editorProveedor);
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter; 
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		driver.edit(new Proveedor());
	}
	
	@UiHandler("send")
	void onSaveUser(ClickEvent event)
	{
		Proveedor bean = driver.flush();
		
		Set<ConstraintViolation<Proveedor>> violations = validator.validate(bean, Default.class);
		
		if (violations.size() > 0) {
			driver.setConstraintViolations(new ArrayList<ConstraintViolation<?>>(violations));
		}
		
		if (!driver.hasErrors()) {
			presenter.onSaveUser(bean);
		}
	}
}
