package com.farmacloud.client.services;

import java.util.List;

import com.farmacloud.shared.model.UnidadMedicamento;
import com.farmacloud.shared.model.Venta;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gestionventa")
public interface ServicioGestionVenta extends RemoteService
{

	List<UnidadMedicamento> buscarUnidad(String codigoNacional, int num);

	void cerrarVenta(Venta venta);

}
