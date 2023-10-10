package com.farmacloud.client.ui.proveedores;

import java.util.Set;

import com.farmacloud.client.ui.widgets.infoProveedor.InfoProveedorWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface VerProveedoresView extends IsWidget{

	Set<InfoProveedorWidget> getSet();
	void showInfo(String nif, String name, String direccion, String telefono, String email);
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
	
	public interface Presenter{
		void onEdit(String principal);
	}
}
