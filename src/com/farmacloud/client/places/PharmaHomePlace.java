package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class PharmaHomePlace extends Place
{
	public PharmaHomePlace(){
	}
	
    @Prefix("pharmaHome")
    public static class Tokenizer implements PlaceTokenizer<PharmaHomePlace> 
    {
         @Override
         public String getToken(PharmaHomePlace place) {
             return "";
     }
 
     @Override
     public PharmaHomePlace getPlace(String token) {
         return new PharmaHomePlace();
     }
   }
}
