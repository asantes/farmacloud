package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class RegistroPlace extends Place{

	public RegistroPlace(){
	}
		
	   @Prefix("registro")
	   public static class Tokenizer implements PlaceTokenizer<RegistroPlace> {
	        @Override
	        public String getToken(RegistroPlace place) {
	            return "";
	        }

	        @Override
	        public RegistroPlace getPlace(String token) {
	            return new RegistroPlace();
	        }
	    }
}
