package com.farmacloud.shared.model.ver;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class UnidadMedicamentoPadre implements Serializable
{
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	String encodedKey;
	String medicamentoProveedorKey;
	
	String codigoNacional;
	Date fechaCaducidad;
	Date fechaVenta;
	String estado;

	public UnidadMedicamentoPadre() {
	}
	
	/* G E T T E R S		&		S E T T E R S		*/

	public String getEncodedKey() {
		return encodedKey;
	}

	public String getMedicamentoProveedorKey() {
		return medicamentoProveedorKey;
	}

	public void setMedicamentoProveedorKey(String medicamentoProveedorKey) {
		this.medicamentoProveedorKey = medicamentoProveedorKey;
	}

	public String getCodigoNacional() {
		return codigoNacional;
	}

	public void setCodigoNacional(String codigoNacional) {
		this.codigoNacional = codigoNacional;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
