package com.farmacloud.shared.model.simulacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;

@PersistenceCapable(detachable="true")
public class MedicamentoPedidoSimulacion extends MedicamentoAbstracto implements Serializable
{
	int unidadesPedidas;
	int unidadesRecibidas;
	
	PedidoSimulacion pedidoSimulacion;
	@Persistent(mappedBy="medicamentoPedidoSimulacion")
	List<UnidadMedicamentoSimulacion> listaUnidadMedicamentoSimulacion = new ArrayList<UnidadMedicamentoSimulacion>();
	
	public MedicamentoPedidoSimulacion(){
	}
	
	public int getUnidadesPedidas() {
		return unidadesPedidas;
	}
	public void setUnidadesPedidas(int unidadesPedidas) {
		this.unidadesPedidas = unidadesPedidas;
	}
	public int getUnidadesRecibidas() {
		return unidadesRecibidas;
	}
	public void setUnidadesRecibidas(int unidadesRecibidas) {
		this.unidadesRecibidas = unidadesRecibidas;
	}

	public List<UnidadMedicamentoSimulacion> getListaUnidadMedicamentoSimulacion() {
		return listaUnidadMedicamentoSimulacion;
	}

	public void setListaUnidadMedicamentoSimulacion(
			List<UnidadMedicamentoSimulacion> listaUnidadMedicamentoSimulacion) {
		this.listaUnidadMedicamentoSimulacion = listaUnidadMedicamentoSimulacion;
	}
	
}
