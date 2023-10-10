package com.farmacloud.shared;

import javax.validation.Validator;

import com.farmacloud.shared.model.Proveedor;
import com.farmacloud.shared.validation.Credenciales;
import com.farmacloud.shared.validation.UsuarioForm;
import com.farmacloud.shared.validation.UsuarioPharmaForm;
import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;

public class MyValidatorFactory extends AbstractGwtValidatorFactory {

	@GwtValidation({Credenciales.class, UsuarioForm.class, UsuarioPharmaForm.class, Proveedor.class})
	public interface GWTValidator extends Validator{
	}
	
	@Override
	public AbstractGwtValidator createValidator() {
		return GWT.create(GWTValidator.class);
	}
}
