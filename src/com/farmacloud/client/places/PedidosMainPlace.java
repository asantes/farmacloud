package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PedidosMainPlace extends Place {

	String name;
	
	public PedidosMainPlace(String token){
		this.name = token;
	}
	
	public String getName(){
		return this.name;
	}

	   public static class Tokenizer implements PlaceTokenizer<PedidosMainPlace> {
	        @Override
	        public String getToken(PedidosMainPlace place) {
	            return place.getName();
	        }

	        @Override
	        public PedidosMainPlace getPlace(String token) {
	            return new PedidosMainPlace(token);
	        }
	    }
}
