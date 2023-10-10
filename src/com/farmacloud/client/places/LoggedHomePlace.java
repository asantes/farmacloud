package com.farmacloud.client.places;


import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoggedHomePlace extends Place{
	
	String name;
	
	public LoggedHomePlace(String token){
		this.name = token;
	}
	
	public String getName(){
		return this.name;
	}
	
	   public static class Tokenizer implements PlaceTokenizer<LoggedHomePlace> {
	        @Override
	        public String getToken(LoggedHomePlace place) {
	            return place.getName();
	        }

	        @Override
	        public LoggedHomePlace getPlace(String token) {
	            return new LoggedHomePlace(token);
	        }
	    }
}

