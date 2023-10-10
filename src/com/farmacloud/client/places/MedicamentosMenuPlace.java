package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MedicamentosMenuPlace extends Place
{
	String name;
	
	public MedicamentosMenuPlace(String token){
		this.name = token;
	}
	
	public String getName(){
		return this.name;
	}
	
	   public static class Tokenizer implements PlaceTokenizer<MedicamentosMenuPlace> {
	        @Override
	        public String getToken(MedicamentosMenuPlace place) {
	            return place.getName();
	        }

	        @Override
	        public MedicamentosMenuPlace getPlace(String token) {
	            return new MedicamentosMenuPlace(token);
	        }
	    }
}
