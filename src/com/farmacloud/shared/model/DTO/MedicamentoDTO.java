package com.farmacloud.shared.model.DTO;

import com.farmacloud.shared.model.Medicamento;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;

public class MedicamentoDTO 
{
	/* Atributos MedicamentoAbstracto */
    private String codigoNacional;
    private String parentKey;
	private String nombre;
	private String laboratorio;
	private String principioActivo;
	private float  precioPVP;
	
	/* Atributos MedicamentoFinanciado */
	private boolean cicero;
	private float precioReferencia;
	private float precioMenor;
	private String grupoHomogeneo;
	private String codigoGrupoHomogeneo;
	
	/* Atributos MedicamentoNoFinanciado */
	//none right now
	
	/* Atributos propios */
	private boolean financiado;
	
	public MedicamentoDTO(){
	}
	
	public MedicamentoDTO(Medicamento medFinanciado)
	{
		setSharedAttributes(medFinanciado);
		this.cicero = medFinanciado.isCicero();
		this.precioReferencia = medFinanciado.getPrecioReferencia();
		this.precioMenor = medFinanciado.getPrecioMenor();
		this.grupoHomogeneo = medFinanciado.getGrupoHomogeneo();
		this.codigoGrupoHomogeneo = medFinanciado.getCodigoGrupoHomogeneo();
		
		this.financiado = true;
	}
	
	public void setSharedAttributes(MedicamentoAbstracto med)
	{
		this.codigoNacional = med.getCodigoNacional();
		this.parentKey = med.getParentKey();
		this.nombre = med.getNombre();
		this.laboratorio = med.getLaboratorio();
		this.principioActivo = med.getPrincipioActivo();
		this.precioPVP = med.getPrecioPVP();
	}

	public String getCodigoNacional() {
		return codigoNacional;
	}

	public void setCodigoNacional(String codigoNacional) {
		this.codigoNacional = codigoNacional;
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
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

	public boolean isFinanciado() {
		return financiado;
	}

	public void setFinanciado(boolean financiado) {
		this.financiado = financiado;
	}
}
