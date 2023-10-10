package com.farmacloud.client.services;

import java.util.List;

import com.farmacloud.shared.model.Proveedor;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServicioGestionProveedorAsync
{
	void añadirProveedor(Proveedor proveedor, AsyncCallback<Proveedor> callback);
	void obtenerProveedores(AsyncCallback<List<Proveedor>> callback);
	void getCatalogoMedicamentos(String proveedorName,
			AsyncCallback<List<MedicamentoAbstracto>> callback);
}
