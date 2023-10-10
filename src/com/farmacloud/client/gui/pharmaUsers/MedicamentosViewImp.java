package com.farmacloud.client.gui.pharmaUsers;

import com.farmacloud.client.ui.medicamentos.MedicamentosView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class MedicamentosViewImp extends Composite implements MedicamentosView
{
	private Presenter presenter;
	
	private FormPanel formPanel;
	private FileUpload fileUpload;
	private Button botonSubmit;
	
	private VerticalPanel vPanel;

	public MedicamentosViewImp() 
	{	
	    /* FormPanel solo acepta un widget */
	    formPanel = new FormPanel();
	    // form.setAction("/myFormHandler");
	    formPanel.setAction(GWT.getModuleBaseURL()+"fileupload");

	    // Because we're going to add a FileUpload widget, we'll need to set the
	    // form to use the POST method, and multipart MIME encoding.
	    formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
	    formPanel.setMethod(FormPanel.METHOD_POST);

	    /* Creamos un VerticalPanel al que añadimos todos los widgets.
	     * En ultima instancia añadiremos el vPanel al FormPanel
	     */
	    vPanel = new VerticalPanel();
	    formPanel.setWidget(vPanel);
	    
	    /* Creamos el widget FileUpload */
	    fileUpload = new FileUpload();
	    fileUpload.setName("fichero");
	    vPanel.add(fileUpload);

	    /* Añadimos el boton sumbit */
	    botonSubmit = new Button("Submit");
	    vPanel.add(botonSubmit);
	    
		initWidget(formPanel);
		setStylePrimaryName("lol");
	}

	@Override
	public HasClickHandlers getSubmit(){
		return this.botonSubmit;
	}@Override
	public void setPresenter(Presenter  presenter) {
		this.presenter = presenter;
	}

	@Override
	public String getFileName() {
		return this.fileUpload.getFilename();
	}

	@Override
	public void submit() {
		formPanel.submit();
	}

	@Override
	public void setSubmitButtonEnable(boolean bool) {
		this.botonSubmit.setEnabled(bool);
	}


	@Override
	public FormPanel getForm() {
		// TODO Auto-generated method stub
		return this.formPanel;
	}

}
