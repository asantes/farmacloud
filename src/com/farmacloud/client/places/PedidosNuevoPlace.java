package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PedidosNuevoPlace extends Place
{
	String name;
	
	public PedidosNuevoPlace(String token){
		this.name = token;
	}
	
	public String getName(){
		return this.name;
	}

	   public static class Tokenizer implements PlaceTokenizer<PedidosNuevoPlace> {
	        @Override
	        public String getToken(PedidosNuevoPlace place) {
	            return place.getName();
	        }

	        @Override
	        public PedidosNuevoPlace getPlace(String token) {
	            return new PedidosNuevoPlace(token);
	        }
	    }
}
