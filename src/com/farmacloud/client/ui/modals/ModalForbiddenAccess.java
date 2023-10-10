package com.farmacloud.client.ui.modals;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Modal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;

public class ModalForbiddenAccess extends Modal
{
	private static ForbiddenAccessUiBinder uiBinder = GWT.create(ForbiddenAccessUiBinder.class);
	interface ForbiddenAccessUiBinder extends UiBinder<Modal, ModalForbiddenAccess> {}
	
	public interface ModalPresenter{
		void onCloseModal();
	}

	ModalPresenter presenter;
	
	@UiField
	Modal myModal;
	@UiField
	Button closeModal;
	
	public ModalForbiddenAccess() {
		uiBinder.createAndBindUi(this);
		myModal.show();
	}
	
	public ModalForbiddenAccess(boolean show, ModalPresenter presenter) {
		uiBinder.createAndBindUi(this);
		setPresenter(presenter);
		if(show)
			myModal.show();
	}
	
	public void setPresenter(ModalPresenter presenter){
		this.presenter = presenter;
	}
	
	@UiHandler({"closeModal"})
	public void onCloseModal(ClickEvent event){
		this.presenter.onCloseModal();
	}
}
