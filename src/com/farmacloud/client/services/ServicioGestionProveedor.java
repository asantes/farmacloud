package com.farmacloud.client.services;

import java.util.List;

import com.farmacloud.shared.model.Proveedor;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.XsrfProtectedService;

@RemoteServiceRelativePath("gestionproveedor")
public interface ServicioGestionProveedor extends XsrfProtectedService 
{
	public Proveedor añadirProveedor(Proveedor proveedor);
	public List<Proveedor> obtenerProveedores();
	public List<MedicamentoAbstracto> getCatalogoMedicamentos(String proveedorNIF);
}
