package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class VerProveedoresPlace extends Place
{
	public VerProveedoresPlace(){
	}

	@Prefix("ver-proveedores")
	public static class Tokenizer implements PlaceTokenizer<VerProveedoresPlace>
	{
        @Override
        public String getToken(VerProveedoresPlace place) {
            return "";
        }

        @Override
        public VerProveedoresPlace getPlace(String token) {
            return new VerProveedoresPlace();
        }
    }
}
