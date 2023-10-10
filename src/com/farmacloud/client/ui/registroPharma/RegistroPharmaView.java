package com.farmacloud.client.ui.registroPharma;

import com.farmacloud.shared.validation.UsuarioPharmaForm;
import com.google.gwt.user.client.ui.Widget;

public interface RegistroPharmaView {
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
	
	public interface Presenter{
		void onSaveUser(UsuarioPharmaForm user);
	}
}
