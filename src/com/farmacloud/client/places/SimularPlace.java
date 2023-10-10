package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class SimularPlace extends Place
{
	public SimularPlace(String token){
	}
		
	@Prefix("simulacion")
	public static class Tokenizer implements PlaceTokenizer<SimularPlace>
	{
		@Override
		public SimularPlace getPlace(String token) {
			return new SimularPlace(token);
		}

		@Override
		public String getToken(SimularPlace place) {
			return "";
		}
	}
}
