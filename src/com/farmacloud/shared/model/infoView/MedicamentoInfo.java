package com.farmacloud.shared.model.infoView;

public class MedicamentoInfo implements Comparable<MedicamentoInfo>
{	
	private String codigoNacional;
	private String nombre;
	private String laboratorio;
	private String principioActivo;
	private Float  precioPVP;
	private long numUnidades;
	
	public MedicamentoInfo(){
	}

	public MedicamentoInfo(String _codigoNacional,
					   String _nombre,
					   String _laboratorio,
					   String _principioActivo,
					   Float  _precioPVP){
		
			this.codigoNacional = _codigoNacional;
			this.laboratorio = _laboratorio;
			this.nombre = _nombre;
			this.principioActivo = _principioActivo;
			this.precioPVP = _precioPVP;
	}

	public String getCodigoNacional() {
		return codigoNacional;
	}

	public void setCodigoNacional(String codigoNacional) {
		this.codigoNacional = codigoNacional;
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

	public Float getPrecioPVP() {
		return precioPVP;
	}

	public void setPrecioPVP(Float precioPVP) {
		this.precioPVP = precioPVP;
	}
	
	public long getNumUnidades() {
		return numUnidades;
	}

	public void setNumUnidades(long numUnidades) {
		this.numUnidades = numUnidades;
	}

	@Override
	public boolean equals(Object o) {
	  if (o instanceof MedicamentoInfo) {
	    return codigoNacional == ((MedicamentoInfo) o).codigoNacional;
	  }
	  return false;
	}
	
	@Override
	public int compareTo(MedicamentoInfo o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}