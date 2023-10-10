package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class HomePlace extends Place
{	
	public HomePlace(){
	}
	
	  @Prefix("home")
	   public static class Tokenizer implements PlaceTokenizer<HomePlace> {
	        @Override
	        public String getToken(HomePlace place) {
	        	System.out.println("aq9");
	            return "";
	        }

	        @Override
	        public HomePlace getPlace(String token) {
	        	System.out.println("adasd");
	            return new HomePlace();
	        }
	    }
}
