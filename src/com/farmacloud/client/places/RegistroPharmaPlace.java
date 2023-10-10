package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class RegistroPharmaPlace extends Place{

	public RegistroPharmaPlace(){
	}
		
   @Prefix("registro-pharma")
   public static class Tokenizer implements PlaceTokenizer<RegistroPharmaPlace> 
   {
        @Override
        public String getToken(RegistroPharmaPlace place) {
            return "";
    }

    @Override
    public RegistroPharmaPlace getPlace(String token) {
        return new RegistroPharmaPlace();
    }
}
}
