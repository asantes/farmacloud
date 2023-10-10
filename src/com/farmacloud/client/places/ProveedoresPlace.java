package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProveedoresPlace extends Place 
{
	String name;
	
	public ProveedoresPlace(String token){
		this.name = token;
	}
	
	public String getName(){
		return this.name;
	}

	   public static class Tokenizer implements PlaceTokenizer<ProveedoresPlace> {
	        @Override
	        public String getToken(ProveedoresPlace place) {
	            return place.getName();
	        }

	        @Override
	        public ProveedoresPlace getPlace(String token) {
	            return new ProveedoresPlace(token);
	        }
	    }
}
