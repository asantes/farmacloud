package com.farmacloud.client.ui.widgetsold;

import java.util.List;

import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface InfoMedicamentoView extends IsWidget {
	void setMensaje(String msg, boolean encontrado);
	void setData(List<String> data);
	void setPresenter(Presenter presenter);
	Widget asWidget();
	
	public interface Presenter{
		public String verificarCodigoIntroducido(String cadenaEntrada);
		public void buscarMedicamento(String _codigoNacional);
	}
}
