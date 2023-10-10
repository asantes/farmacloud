package com.farmacloud.client.services;

import java.util.List;

import com.farmacloud.shared.model.LineaPedido;
import com.farmacloud.shared.model.Pedido;
import com.farmacloud.shared.model.Proveedor;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServicioGestionPedidoAsync 
{
	void crearPedido(Pedido pedido, String primaryKeyProveedor,
			AsyncCallback<Void> callback);

	void getNombreProveedoresPedidosNoCompletos(String tipo,
			AsyncCallback<List<Proveedor>> callback);

	void getPedidosNoCompletos(String primaryKeyProveedor, String tipo,
			AsyncCallback<List<Pedido>> callback);

	void recepcionarPedido(int primaryKeyPedido,
			List<LineaPedido> listaMedicamentos,
			AsyncCallback<List<MedicamentoAbstracto>> callback);

	void getPedidoFull(int primaryKeyPedido, AsyncCallback<Pedido> callback);	

}
