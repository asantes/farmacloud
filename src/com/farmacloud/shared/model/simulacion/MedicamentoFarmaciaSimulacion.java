package com.farmacloud.shared.model.simulacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import com.farmacloud.shared.model.abstracts.MedicamentoFarmaciaAbstracto;

@PersistenceCapable(detachable="true")
public class MedicamentoFarmaciaSimulacion extends MedicamentoFarmaciaAbstracto implements Serializable {

	Simulacion simulacion;
	double varianza;
	List<Integer> listaVentasTiempo = new ArrayList<Integer>();
	int contadorVentasTiempo;
	boolean esperandoUnidades;
	
	public int getContadorVentasTiempo() {
		return contadorVentasTiempo;
	}
	
	public void setContadorVentasTiempo(int contadorVentasTiempo) {
		this.contadorVentasTiempo = contadorVentasTiempo;
	}
	
	public double getVarianza() {
		return varianza;
	}

	public void setVarianza(double varianza) {
		this.varianza = varianza;
	}

	public boolean isEsperandoUnidades() {
		return esperandoUnidades;
	}
	
	public void setEsperandoUnidades(boolean esperandoUnidades) {
		this.esperandoUnidades = esperandoUnidades;
	}
	
	public List<Integer> getListaVentasTiempo() {
		return listaVentasTiempo;
	}
	public void setListaVentasTiempo(List<Integer> listaVentasTiempo) {
		this.listaVentasTiempo = listaVentasTiempo;
	}
	
	Simulacion getSimulacion()
	{
		return this.simulacion;
	}
}
