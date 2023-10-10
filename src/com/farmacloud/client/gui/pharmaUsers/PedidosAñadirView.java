package com.farmacloud.client.gui.pharmaUsers;

import java.util.List;

import com.farmacloud.client.presenter.pharmaUsers.PedidosAñadirPresenter;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;

public interface PedidosAñadirView 
{
	AbstractCellTable getTablaCatalogoProveedores();
	SingleSelectionModel getSelectionModeProveedor();
	AbstractCellTable getTablaMedicamentosPedido();
	SingleSelectionModel getSelectionModePedido();
	HasClickHandlers getListProveedores();
	HasClickHandlers getConfirmButton();
	int getProveedorSeleccionado(ClickEvent event);
	void setListaProveedores(List<String> lista);
	void setDataTable(List<MedicamentoAbstracto> result);
	Widget asWidget();
	void setPresenter(PedidosAñadirPresenter presenter);
}
