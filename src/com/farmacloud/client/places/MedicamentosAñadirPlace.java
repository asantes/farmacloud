package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MedicamentosAñadirPlace extends Place
{
	String name;
	
	public MedicamentosAñadirPlace(String token){
		this.name = token;
	}
	
	public String getName(){
		return this.name;
	}
	
	   public static class Tokenizer implements PlaceTokenizer<MedicamentosAñadirPlace> {
	        @Override
	        public String getToken(MedicamentosAñadirPlace place) {
	            return place.getName();
	        }

	        @Override
	        public MedicamentosAñadirPlace getPlace(String token) {
	            return new MedicamentosAñadirPlace(token);
	        }
	    }
}
