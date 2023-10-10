package com.farmacloud.client.ui.menu;

import org.gwtbootstrap3.client.ui.NavbarButton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MenuPharmaViewImp extends Composite implements MenuPharmaView
{
	private static MenuViewImpUiBinder uiBinder = GWT
			.create(MenuViewImpUiBinder.class);

	interface MenuViewImpUiBinder extends UiBinder<Widget, MenuPharmaViewImp> {
	}
	
	private Presenter presenter;
	@UiField
	NavbarButton logout;
	
	public MenuPharmaViewImp() 
	{
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("logout")
	public void onLogout(ClickEvent event){
		presenter.logout();
	}
}
