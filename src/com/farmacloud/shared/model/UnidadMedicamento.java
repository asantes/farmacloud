package com.farmacloud.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.farmacloud.shared.model.ver.UnidadMedicamentoPadre;

@PersistenceCapable(detachable="true")
public class UnidadMedicamento extends UnidadMedicamentoPadre implements Serializable
{
	private static final long serialVersionUID = 377902017552823919L;
	
	@Persistent(mappedBy = "listaUnidadMedicamento")
	private LineaPedido lineaPedido;
	@Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
	private String parentEncodedKey;
	
	private String medProveedorKey;
	       
	@NotPersistent
	private MedicamentoAbstracto medicamento;
	@NotPersistent
	private Float aportacion;
	@NotPersistent
	private Float precioFinal;
	
	public UnidadMedicamento(){
	}
	
	/* G E T T E R S		&		S E T T E R S		*/
	
	public LineaPedido getLineaPedido() {
		return lineaPedido;
	}

	public String getParentEncodedKey() {
		return parentEncodedKey;
	}
	
	public String getMedProveedorKey() {
		return medProveedorKey;
	}

	public void setMedProveedorKey(String medProveedorKey) {
		this.medProveedorKey = medProveedorKey;
	}

	/* */
	public MedicamentoAbstracto getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(MedicamentoAbstracto medicamento) {
		this.medicamento = medicamento;
	}
	
	public Float getAportacion() {
		return aportacion;
	}
	
	public void setAportacion(Float aportacion) {
		this.aportacion = aportacion;
	}
	
	public Float getPrecioFinal() {
		return precioFinal;
	}
	
	public void setPrecioFinal(Float precioFinal) {
		this.precioFinal = precioFinal;
	}
}
