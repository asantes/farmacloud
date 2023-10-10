package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PedidosMenuPlace extends Place
{
	String name;
	
	public PedidosMenuPlace(String token){
		this.name = token;
	}
	
	public String getName(){
		return this.name;
	}

	   public static class Tokenizer implements PlaceTokenizer<PedidosMenuPlace> {
	        @Override
	        public String getToken(PedidosMenuPlace place) {
	            return place.getName();
	        }

	        @Override
	        public PedidosMenuPlace getPlace(String token) {
	            return new PedidosMenuPlace(token);
	        }
	    }
}
