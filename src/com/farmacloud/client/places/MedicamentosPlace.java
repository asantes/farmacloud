package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class MedicamentosPlace extends Place
{
	public MedicamentosPlace(String token){
	}
	
	@Prefix("medicamentos")
	public static class Tokenizer implements PlaceTokenizer<MedicamentosPlace> 
	{
		@Override
		public String getToken(MedicamentosPlace place) {
	            return "";
		}

		@Override
	    public MedicamentosPlace getPlace(String token) {
			return new MedicamentosPlace(token);
	    }
	}
}
