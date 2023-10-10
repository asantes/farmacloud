package com.farmacloud.client.ui.widgets.infoProveedor;

import com.google.gwt.user.client.ui.IsWidget;

public interface InfoProveedorView extends IsWidget  {

	void setPresenter(Presenter presenter);
	
	public interface Presenter{
		void onEdit(String nif);
	}
}

