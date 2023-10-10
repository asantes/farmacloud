package com.farmacloud.client.ui.medicamentos;

import org.gwtbootstrap3.client.ui.ProgressBar;

import com.farmacloud.client.ui.widgets.fileUpload.MyFileUploadViewImp;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;

public class MedicamentosViewImp extends Composite implements MedicamentosView{

	private static MedicamentosViewImpUiBinder uiBinder = GWT
			.create(MedicamentosViewImpUiBinder.class);

	interface MedicamentosViewImpUiBinder extends
			UiBinder<Widget, MedicamentosViewImp> {
	}
	
	public static final String NAME_FIELD = "catalogo-farmacia";
	public static final String ACTION = "fileUpload";
	
	Presenter presenter;

	@UiField
	MyFileUploadViewImp myFileUpload;
	
	public MedicamentosViewImp() {
		initWidget(uiBinder.createAndBindUi(this));
		myFileUpload.setAction(ACTION);
		myFileUpload.setFileUploadName(NAME_FIELD);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasClickHandlers getSubmit() {
		return myFileUpload.getSubmit();
	}

	@Override
	public FormPanel getForm(){
		return myFileUpload.getForm();
	}
	
	@Override
	public ProgressBar getProgressBar() {
		return myFileUpload.getProgressBar();
	}
	
	@Override
	public String getFileName() {
		return myFileUpload.getFileName();
	}

	@Override
	public void submit() {
		myFileUpload.submit();
	}
	
	@Override
	public void upDateInfoProgress(String infoProgreso){
		myFileUpload.upDateInfoProgress(infoProgreso);
	}
	
	@Override
	public void setSubmitButtonEnable(boolean bool) {	
	}

	@Override
	public void setFormAction(String action) {
		myFileUpload.setFileUploadName(NAME_FIELD);
	}

	@Override
	public void setFileUploadsName(String fieldName) {
		myFileUpload.setAction(ACTION);
	}
}
