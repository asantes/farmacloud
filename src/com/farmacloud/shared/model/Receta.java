package com.farmacloud.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Receta implements Serializable
{
	private static final long serialVersionUID = -4412345898331803811L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	String encodedKey;
	
	@Persistent(mappedBy = "receta")
	private UnidadMedicamento unidadMedicamento;
	@Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
	private String parentEncodedKey;
	
	private String TSI;
	private float porcentajeAportacion;
	
	public Receta(){
	}
	
	/* G E T T E R S		&		S E T T E R S		*/

	public String getEncodedKey() {
		return encodedKey;
	}

	public String getParentEncodedKey() {
		return parentEncodedKey;
	}

	public UnidadMedicamento getUnidadMedicamento() {
		return unidadMedicamento;
	}

	public String getTSI() {
		return TSI;
	}

	public void setTSI(String tSI) {
		TSI = tSI;
	}

	public float getPorcentajeAportacion() {
		return porcentajeAportacion;
	}

	public void setPorcentajeAportacion(float porcentajeAportacion) {
		this.porcentajeAportacion = porcentajeAportacion;
	}
}
