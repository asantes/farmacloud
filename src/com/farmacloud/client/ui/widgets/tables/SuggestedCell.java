package com.farmacloud.client.ui.widgets.tables;

import org.gwtbootstrap3.client.ui.SuggestBox;

import com.farmacloud.client.ui.simulacion.OracleMedicamentos;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public abstract class SuggestedCell<T> extends FixedPaginatedCell<T> implements SuggestedCellInterface<T>{

	interface SuggestedCellUiBinder extends UiBinder<Widget, SuggestedCell> {
	}

	OracleMedicamentos oracle;

	@UiField(provided=true)
	SuggestBox suggest;
	
	public SuggestedCell() 
	{
		oracle = new OracleMedicamentos();
		suggest = new SuggestBox(oracle);
		oracle.setSuggestWidget(suggest); 
		suggest.setAutoSelectEnabled(true);
	}
	
	@Override
	public HasSelectionHandlers<T> getHasSelectionHandlers() {
		return (HasSelectionHandlers<T>) suggest;
	}
}
