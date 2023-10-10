package com.farmacloud.client.ui.widgets.fileUpload;

import org.gwtbootstrap3.client.ui.ProgressBar;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface MyFileUploadView extends IsWidget
{
	FormPanel getForm();
	HasClickHandlers getSubmit();
	ProgressBar getProgressBar();
	String getFileName();
	void upDateInfoProgress(String infoProgreso);
	void submit();
	
	void setAction(String action);
	void setFileUploadName(String fieldName);
	Widget asWidget();
}
