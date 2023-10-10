package com.farmacloud.client.ui.proveedores;

import com.farmacloud.shared.model.Proveedor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface AñadirProveedorView extends IsWidget{

	Widget asWidget();
	void setPresenter(Presenter presenter);
	
	public interface Presenter{
		void onSaveUser(Proveedor proveedor);
	}
}
