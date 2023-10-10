package com.farmacloud.client.gui.layout;

import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Navbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Layout extends Composite {

	private static MainViewUiBinder uiBinder = GWT
			.create(MainViewUiBinder.class);

	interface MainViewUiBinder extends UiBinder<Widget, Layout> {
	}
	
	@UiField
	Navbar menu;
	@UiField
	Column mainContent;
	@UiField
	Column asideContent;

	public Layout() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Navbar getMenu() {
		return menu;
	}

	public void setMenu(Navbar menu) {
		this.menu = menu;
	}

	public Column getMainContent() {
		return mainContent;
	}

	public void setMainContent(Column mainContent) {
		this.mainContent = mainContent;
	}

	public Column getAsideContent() {
		return asideContent;
	}

	public void setAsideContent(Column asideContent) {
		this.asideContent = asideContent;
	}
}
