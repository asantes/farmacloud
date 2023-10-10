package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class VentaPlace extends Place
{
	private String name;

	public VentaPlace(String token){
		this.name = token;
	}
	
	public String getName(){
		return this.name;
	}
		
	   public static class Tokenizer implements PlaceTokenizer<VentaPlace> {
	        @Override
	        public String getToken(VentaPlace place) {
	            return place.getName();
	        }

	        @Override
	        public VentaPlace getPlace(String token) {
	            return new VentaPlace(token);
	        }
	    }
}
