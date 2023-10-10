package com.farmacloud.shared.model.abstracts;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class MedicamentoAbstracto implements Serializable
{
	private static final long serialVersionUID = -124886686566164498L;
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String codigoNacional;
    
    private String parentKey;
	private String nombre;
	private String laboratorio;
	private String principioActivo;
	private float  precioPVP;
	
	private boolean cicero;
	private float precioReferencia;
	private float precioMenor;
	private String grupoHomogeneo;
	private String codigoGrupoHomogeneo;
	
	@NotPersistent
	private boolean existe;
	@NotPersistent
	private boolean existeMedicamentoFarmacia;
	
	public MedicamentoAbstracto(){
	}
	
	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public String getCodigoNacional() {
		return codigoNacional;
	}

	public void setCodigoNacional(String codigoNacional) {
		this.codigoNacional = codigoNacional;
	}
	
	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLaboratorio() {
		return laboratorio;
	}


	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}


	public String getPrincipioActivo() {
		return principioActivo;
	}

	public void setPrincipioActivo(String principioActivo) {
		this.principioActivo = principioActivo;
	}

	public float getPrecioPVP() {
		return precioPVP;
	}

	public void setPrecioPVP(float precioPVP) {
		this.precioPVP = precioPVP;
	}
	
	public boolean isCicero() {
		return cicero;
	}

	public void setCicero(boolean cicero) {
		this.cicero = cicero;
	}

	public float getPrecioReferencia() {
		return precioReferencia;
	}

	public void setPrecioReferencia(float precioReferencia) {
		this.precioReferencia = precioReferencia;
	}

	public float getPrecioMenor() {
		return precioMenor;
	}

	public void setPrecioMenor(float precioMenor) {
		this.precioMenor = precioMenor;
	}

	public String getGrupoHomogeneo() {
		return grupoHomogeneo;
	}

	public void setGrupoHomogeneo(String grupoHomogeneo) {
		this.grupoHomogeneo = grupoHomogeneo;
	}

	public String getCodigoGrupoHomogeneo() {
		return codigoGrupoHomogeneo;
	}

	public void setCodigoGrupoHomogeneo(String codigoGrupoHomogeneo) {
		this.codigoGrupoHomogeneo = codigoGrupoHomogeneo;
	}
	

	/* ********************************************************* */


	public boolean isExiste() {
		return existe;
	}

	public void setExiste(boolean existe) {
		this.existe = existe;
	}

	public boolean isExisteMedicamentoFarmacia() {
		return existeMedicamentoFarmacia;
	}

	public void setExisteMedicamentoFarmacia(boolean existeMedicamentoFarmacia) {
		this.existeMedicamentoFarmacia = existeMedicamentoFarmacia;
	}

}
