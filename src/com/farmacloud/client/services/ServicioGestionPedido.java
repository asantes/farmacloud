package com.farmacloud.client.services;

import java.util.List;

import com.farmacloud.shared.model.LineaPedido;
import com.farmacloud.shared.model.Pedido;
import com.farmacloud.shared.model.Proveedor;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gestionpedido")
public interface ServicioGestionPedido extends RemoteService 
{
	void crearPedido(Pedido pedido, String primaryKeyProveedor);

	List<Proveedor> getNombreProveedoresPedidosNoCompletos(String tipo);

	List<Pedido> getPedidosNoCompletos(String primaryKeyProveedor, String tipo);

	List<MedicamentoAbstracto> recepcionarPedido(int primaryKeyPedido,
			List<LineaPedido> listaMedicamentos);

	Pedido getPedidoFull(int primaryKeyPedido);

}
