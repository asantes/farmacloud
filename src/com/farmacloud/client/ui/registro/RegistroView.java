package com.farmacloud.client.ui.registro;

import com.farmacloud.shared.validation.UsuarioForm;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface RegistroView extends IsWidget{
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
	
	public interface Presenter{
		void onSaveUser(UsuarioForm user);
	}
}
