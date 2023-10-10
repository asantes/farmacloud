package com.farmacloud.client.places;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.shared.model.simulacion.MedicamentoFarmaciaSimulacion;
import com.farmacloud.shared.model.simulacion.Simulacion;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SimulacionRunningPlace extends Place
{
	List<MedicamentoFarmaciaSimulacion> listaAux = new ArrayList<MedicamentoFarmaciaSimulacion>();
	private Simulacion simulacion;
	private String name;

	public SimulacionRunningPlace(String token){
		this.name = token;
	}
	
	public String getName(){
		return this.name;
	}
		
	   public static class Tokenizer implements PlaceTokenizer<SimulacionRunningPlace> {
	        @Override
	        public String getToken(SimulacionRunningPlace place) {
	            return place.getName();
	        }

	        @Override
	        public SimulacionRunningPlace getPlace(String token) {
	            return new SimulacionRunningPlace(token);
	        }
	    }

	public Simulacion getSimulacion() {
		return simulacion;
	}

	public void setSimulacion(Simulacion simulacion) {
		this.simulacion = simulacion;
	}

	public List<MedicamentoFarmaciaSimulacion> getListaAux() {
		return listaAux;
	}

	public void setListaAux(List<MedicamentoFarmaciaSimulacion> listaAux) {
		this.listaAux = listaAux;
	} 
}
