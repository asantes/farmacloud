package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class RecepcionPlace extends Place{

	String name;
	
	public RecepcionPlace(String token){
		this.name = token;
	}
	
	public String getName(){
		return this.name;
	}
	
	   public static class Tokenizer implements PlaceTokenizer<RecepcionPlace> {
	        @Override
	        public String getToken(RecepcionPlace place) {
	            return place.getName();
	        }

	        @Override
	        public RecepcionPlace getPlace(String token) {
	            return new RecepcionPlace(token);
	        }
	    }
}
