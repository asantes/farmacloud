package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EscribirNoticiaPlace extends Place
{
	private String name;

	public EscribirNoticiaPlace(String token){
		this.name = token;
	}
	
	public String getName(){
		return this.name;
	}

	@Prefix("escribir-noticia")
	public static class Tokenizer implements PlaceTokenizer<EscribirNoticiaPlace>
	{
		@Override
        public String getToken(EscribirNoticiaPlace place) {
            return place.getName();
        }

        @Override
        public EscribirNoticiaPlace getPlace(String token) {
            return new EscribirNoticiaPlace(token);
        }
    }
}