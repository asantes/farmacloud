package com.farmacloud.shared.model.simulacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.InstanceCallbacks;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class Simulacion implements Serializable
{
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	String encodedKey;
	
	@Persistent(mappedBy="simulacion")
	List<MedicamentoFarmaciaSimulacion> listaMedicamentoFarmaciaSimulacion = new ArrayList<MedicamentoFarmaciaSimulacion>();
	List<String> pedidosRecepcionadosKeys = new ArrayList<String>();
	List<String> pedidosEsperandoRecepcionKeys = new ArrayList<String>();
	String pedidFormandoseKey;
	Date fechaInicio;
	Date siguienteLlegada;
	Date siguienteSiguienteLlegada;
	String estado;
	int frecuenciaSimulacion;
	int frecuenciaLlegadaPedidos;
	int num;
	
	public Simulacion(){
	}
	
	public String getEncodedKey() {
		return encodedKey;
	}
	
	public List<MedicamentoFarmaciaSimulacion> getListaMedicamentoFarmaciaSimulacion() {
		return listaMedicamentoFarmaciaSimulacion;
	}

	public void setListaMedicamentoFarmaciaSimulacion(
			List<MedicamentoFarmaciaSimulacion> listaMedicamentoFarmaciaSimulacion) {
		this.listaMedicamentoFarmaciaSimulacion = listaMedicamentoFarmaciaSimulacion;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getSiguienteLlegada() {
		return siguienteLlegada;
	}

	public void setSiguienteLlegada(Date siguienteLlegada) {
		this.siguienteLlegada = siguienteLlegada;
	}

	public Date getSiguienteSiguienteLlegada() {
		return siguienteSiguienteLlegada;
	}

	public void setSiguienteSiguienteLlegada(Date siguienteSiguienteLlegada) {
		this.siguienteSiguienteLlegada = siguienteSiguienteLlegada;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getFrecuenciaSimulacion() {
		return frecuenciaSimulacion;
	}

	public void setFrecuenciaSimulacion(int frecuenciaSimulacion) {
		this.frecuenciaSimulacion = frecuenciaSimulacion;
	}

	public int getFrecuenciaLlegadaPedidos() {
		return frecuenciaLlegadaPedidos;
	}

	public void setFrecuenciaLlegadaPedidos(int frecuenciaLlegadaPedidos) {
		this.frecuenciaLlegadaPedidos = frecuenciaLlegadaPedidos;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPedidFormandoseKey() {
		return pedidFormandoseKey;
	}

	public void setPedidFormandoseKey(String pedidFormandoseKey) {
		this.pedidFormandoseKey = pedidFormandoseKey;
	}

	public List<String> getPedidosRecepcionadosKeys() {
		return pedidosRecepcionadosKeys;
	}

	public void setPedidosRecepcionadosKeys(List<String> pedidosRecepcionadosKeys) {
		this.pedidosRecepcionadosKeys = pedidosRecepcionadosKeys;
	}

	public List<String> getPedidosEsperandoRecepcionKeys() {
		return pedidosEsperandoRecepcionKeys;
	}

	public void setPedidosEsperandoRecepcionKeys(
			List<String> pedidosEsperandoRecepcionKeys) {
		this.pedidosEsperandoRecepcionKeys = pedidosEsperandoRecepcionKeys;
	}
}
