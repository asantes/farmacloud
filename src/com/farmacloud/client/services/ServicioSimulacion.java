package com.farmacloud.client.services;

import java.util.List;

import com.farmacloud.shared.model.simulacion.MedicamentoFarmaciaSimulacion;
import com.farmacloud.shared.model.simulacion.Simulacion;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("simulacion")
public interface ServicioSimulacion extends RemoteService{
	Simulacion crearNuevaSimulacion(Simulacion simulacion, List<MedicamentoFarmaciaSimulacion> listaMeds);
	MedicamentoFarmaciaSimulacion realizarVenta(String simulacionKey, String codigoNacional, int num);
	void comprobarTiemposSimulacion(String simulacionKey);
}
