package com.farmacloud.client.ui.widgets.fileUpload;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ProgressBar;
import org.gwtbootstrap3.client.ui.html.Paragraph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;

public class MyFileUploadViewImp extends Composite implements MyFileUploadView{

	private static MyFileUploadViewImpUiBinder uiBinder = GWT
			.create(MyFileUploadViewImpUiBinder.class);

	interface MyFileUploadViewImpUiBinder extends
			UiBinder<Widget, MyFileUploadViewImp> {
	}
	
	@UiField(provided=true)
	FormPanel form;
	@UiField 	
	FileUpload fileUpload;
	@UiField
	Button send;
	@UiField
	ProgressBar progressBar;
	@UiField 
	Paragraph unidadesDatosSubidas;

	public MyFileUploadViewImp() {
		form = new FormPanel();
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public FormPanel getForm() {
		return form;
	}
	
	@Override
	public HasClickHandlers getSubmit() {
		return send;
	}

	@Override
	public ProgressBar getProgressBar() {
		return progressBar;
	}

	@Override
	public String getFileName() {
		return fileUpload.getFilename();
	}

	@Override
	public void upDateInfoProgress(String infoProgreso) {
		unidadesDatosSubidas.setHTML(infoProgreso);
	}

	@Override
	public void submit() {	
		form.submit(); 
	}

	@Override
	public void setAction(String action) {
		form.setAction(GWT.getModuleBaseURL()+action);
	}

	@Override
	public void setFileUploadName(String fieldName) {
		fileUpload.setName(fieldName);
	}
}
