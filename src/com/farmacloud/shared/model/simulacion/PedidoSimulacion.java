package com.farmacloud.shared.model.simulacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.farmacloud.shared.model.abstracts.PedidoAbstracto;

@PersistenceCapable(detachable="true")
public class PedidoSimulacion extends PedidoAbstracto implements Serializable 
{
	String simulacionKey;
	Date fehaLlegada;
	
	@Persistent(mappedBy="pedidoSimulacion")
	List<MedicamentoPedidoSimulacion> listaMedicametoPedidoSimulacion = new ArrayList<MedicamentoPedidoSimulacion>();

	public String getSimulacionKey() {
		return simulacionKey;
	}

	public void setSimulacionKey(String simulacionKey) {
		this.simulacionKey = simulacionKey;
	}

	public List<MedicamentoPedidoSimulacion> getListaMedicametoPedidoSimulacion() {
		return listaMedicametoPedidoSimulacion;
	}

	public void setListaMedicametoPedidoSimulacion(
			List<MedicamentoPedidoSimulacion> listaMedicametoPedidoSimulacion) {
		this.listaMedicametoPedidoSimulacion = listaMedicametoPedidoSimulacion;
	}

	public Date getFehaLlegada() {
		return fehaLlegada;
	}

	public void setFehaLlegada(Date fehaLlegada) {
		this.fehaLlegada = fehaLlegada;
	}
}
