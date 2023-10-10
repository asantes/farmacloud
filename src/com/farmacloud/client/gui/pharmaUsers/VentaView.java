package com.farmacloud.client.gui.pharmaUsers;

import com.farmacloud.shared.model.LineaVenta;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Widget;

public interface VentaView
{
	CellTable<LineaVenta> getSellTable();
	void setMensaje(String msg, boolean visibilidad);
	void setPrecioTotal(String precio);
	HasClickHandlers getCerrarVenta();
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
	
	public interface Presenter{
		String verificarCodigoIntroducido(String cadena);
		void buscarMedicamentoVenta(String cadenaVerificada);
		void actualizarPrecioUnidad(int index);
	}
}

