package com.farmacloud.client.ui.medicamentos;

import org.gwtbootstrap3.client.ui.ProgressBar;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface MedicamentosView extends IsWidget
{
	HasClickHandlers getSubmit();
	FormPanel getForm();
	ProgressBar getProgressBar();
	void upDateInfoProgress(String infoProgreso);
	String getFileName();
	void setSubmitButtonEnable(boolean bool);
	void submit();
	
	void setFormAction(String action);
	void setFileUploadsName(String fieldName);
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
	
	public interface Presenter{
	}
}
