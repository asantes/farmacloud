package com.farmacloud.client.services;

import java.util.List;

import com.farmacloud.shared.model.simulacion.MedicamentoFarmaciaSimulacion;
import com.farmacloud.shared.model.simulacion.Simulacion;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServicioSimulacionAsync {

	void crearNuevaSimulacion(Simulacion simulacion,
			List<MedicamentoFarmaciaSimulacion> listaMeds,
			AsyncCallback<Simulacion> callback);

	void realizarVenta(String simulacionKey, String codigoNacional, int num,
			AsyncCallback<MedicamentoFarmaciaSimulacion> callback);

	void comprobarTiemposSimulacion(String simulacionKey,
			AsyncCallback<Void> callback);

}
