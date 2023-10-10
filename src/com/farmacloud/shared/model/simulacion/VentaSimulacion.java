package com.farmacloud.shared.model.simulacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import com.farmacloud.shared.model.abstracts.VentaAbstracta;

@PersistenceCapable(detachable="true")
public class VentaSimulacion extends VentaAbstracta implements Serializable
{
	String simulacionKey;
	
	List<String> listaClavesUnidadMedicamentoSimulacion = new ArrayList<String>();

	public List<String> getListaClavesUnidadMedicamentoSimulacion() {		
		return listaClavesUnidadMedicamentoSimulacion;
	}
	public void setListaClavesUnidadMedicamentoSimulacion(
			List<String> listaClavesUnidadMedicamentoSimulacion) {
		this.listaClavesUnidadMedicamentoSimulacion = listaClavesUnidadMedicamentoSimulacion;
	}
	public String getSimulacionKey() {
		return simulacionKey;
	}
	public void setSimulacionKey(String simulacionKey) {
		this.simulacionKey = simulacionKey;
	}
}
