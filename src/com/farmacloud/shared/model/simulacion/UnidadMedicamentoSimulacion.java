package com.farmacloud.shared.model.simulacion;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.farmacloud.shared.model.ver.UnidadMedicamentoPadre;

@PersistenceCapable(detachable="true")
public class UnidadMedicamentoSimulacion extends UnidadMedicamentoPadre implements Serializable
{	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
	String parentEncodedKey;
	
	MedicamentoPedidoSimulacion medicamentoPedidoSimulacion; //JDO padre
	String simulacionKey;
	String ventaSimulacionParentKey; //KEY
	
	Date fechaCreacion;

	public UnidadMedicamentoSimulacion(){
	}

	public String getVentaSimulacionParentKey() {
		return ventaSimulacionParentKey;
	}

	public void setVentaSimulacionParentKey(String ventaSimulacionParentKey) {
		this.ventaSimulacionParentKey = ventaSimulacionParentKey;
	}

	public String getSimulacionKey() {
		return simulacionKey;
	}

	public void setSimulacionKey(String simulacionKey) {
		this.simulacionKey = simulacionKey;
	}

	public String getParentEncodedKey() {
		return parentEncodedKey;
	}

	public void setParentEncodedKey(String parentEncodedKey) {
		this.parentEncodedKey = parentEncodedKey;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public void setParentEncodedKey(long idInterno) {
		// TODO Auto-generated method stub
		
	}
	
	
}
	
