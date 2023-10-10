package com.farmacloud.client.ui.modals;

import org.gwtbootstrap3.client.ui.Modal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class ModalRpcFailure
{
	private static RpcFailureUiBinder uiBinder = GWT.create(RpcFailureUiBinder.class);
	interface RpcFailureUiBinder extends UiBinder<Widget, ModalRpcFailure> {}

	@UiField
	Modal myModal;
	
	public ModalRpcFailure() {
		uiBinder.createAndBindUi(this);
		myModal.show();
	}
	
	public ModalRpcFailure(boolean show) {
		uiBinder.createAndBindUi(this);
		if(show)
			myModal.show();
	}
}
