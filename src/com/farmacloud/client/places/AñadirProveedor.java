package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class AñadirProveedor extends Place
{
	public AñadirProveedor(){
	}

	@Prefix("añadir-proveedor")
	public static class Tokenizer implements PlaceTokenizer<AñadirProveedor>
	{
        @Override
        public String getToken(AñadirProveedor place) {
            return "";
        }

        @Override
        public AñadirProveedor getPlace(String token) {
            return new AñadirProveedor();
        }
    }
}
