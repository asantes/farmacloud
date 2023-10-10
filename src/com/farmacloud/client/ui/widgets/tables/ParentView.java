package com.farmacloud.client.ui.widgets.tables;


import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;

public abstract class ParentView extends Composite  {

	@UiField
	protected TextBox textBox;
	
	public ParentView() {
	}
}
