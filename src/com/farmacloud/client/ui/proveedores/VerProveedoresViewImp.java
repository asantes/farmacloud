package com.farmacloud.client.ui.proveedores;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.gwtbootstrap3.client.ui.PanelGroup;

import com.farmacloud.client.ui.widgets.infoProveedor.InfoProveedorView;
import com.farmacloud.client.ui.widgets.infoProveedor.InfoProveedorWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class VerProveedoresViewImp extends Composite implements VerProveedoresView, InfoProveedorView.Presenter
{
	Logger logger = Logger.getLogger(AñadirProveedorViewImp.class.getSimpleName());
	private static VerProveedoresUiBinder uiBinder = GWT.create(VerProveedoresUiBinder.class);
	interface VerProveedoresUiBinder extends UiBinder<Widget, VerProveedoresViewImp> {}

	Presenter presenter;
	Set<InfoProveedorWidget> set = new HashSet<InfoProveedorWidget>();
	
	@UiField
	PanelGroup accordion;
	
	public VerProveedoresViewImp() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void showInfo(String nif, String name, String direccion, String telefono, String email){
		InfoProveedorWidget infoProveedor = new InfoProveedorWidget(nif,
																	name,
																	direccion, 
																	telefono, 
																	email);
		infoProveedor.setPresenter(this);
		accordion.add(infoProveedor);
		set.add(infoProveedor);
	}

	@Override
	public void onEdit(String nif) {
		presenter.onEdit(nif);
	}

	@Override
	public Set<InfoProveedorWidget> getSet() {
		return set;
	}
}
