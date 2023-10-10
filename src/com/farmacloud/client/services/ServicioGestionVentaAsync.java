package com.farmacloud.client.services;

import java.util.List;

import com.farmacloud.shared.model.UnidadMedicamento;
import com.farmacloud.shared.model.Venta;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServicioGestionVentaAsync {

	void buscarUnidad(String codigoNacional, int num,
			AsyncCallback<List<UnidadMedicamento>> callback);

	void cerrarVenta(Venta venta, AsyncCallback<Void> callback);


}
