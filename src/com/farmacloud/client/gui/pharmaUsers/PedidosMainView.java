package com.farmacloud.client.gui.pharmaUsers;

import java.util.List;

import com.farmacloud.client.ui.widgetsold.TablaInfoPedidoView;
import com.farmacloud.shared.model.Pedido;
import com.farmacloud.shared.model.Proveedor;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.Widget;

public interface PedidosMainView{
	Tree getPendientesTree();
	Tree getIncompletosTree();
	TablaInfoPedidoView getWidgetTablaPedido();
	HasClickHandlers getRecepcionarButton();
	
	void setRootData(List<Proveedor> data, Tree arbol);
	void setChildData(List<Pedido> pedido, Tree arbol);

	Widget asWidget();
	void setPresenter(Presenter presenter);
	
    public interface Presenter {
        void goTo(Place place);
    }
}
