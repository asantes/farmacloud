package com.farmacloud.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;

public class PlaceHistoryMapperWithoutColon implements PlaceHistoryMapper 
{
    private static final String COLON = ":";
    private static final String SLASH = "/";

    private PlaceHistoryMapper placeHistoryMapper;

    public PlaceHistoryMapperWithoutColon(PlaceHistoryMapper placeHistoryMapper) {
        this.placeHistoryMapper = placeHistoryMapper;
    }

    @Override
    public Place getPlace(String token) 
    {
    	/* Si el prefijo no tiene ':' no es reconodido(emparejado) con su Tokenizer y no se procesa el token  
    	 * Por lo tanto reemplazamos el caracter delimitador propio  que habiamos añadido por el ':' 
    	 */
        if(token != null && token.contains(SLASH))
        	token = token.replace(SLASH, COLON);
        
        /* Aqui el token es del tipo 
         * 	
         * 		NoticiasPlace:page:2
         * 
         * Pero lo que le llega al Tokenizer es
         * 	
         * 		page:2
         */

        return placeHistoryMapper.getPlace(token);
    }

    @Override
    public String getToken(Place place) 
    {
    	/* Aqui actua el Tokenizer y nos devuelve el token con el delimitador que le tengamos puesto. Pero al token
    	 * que nos llega automaticamente se le incorpora el prefijo del place en cuestion mas el delimitador ':'
    	 * 
    	 * Ej: 
    	 * 		tokenizer devuelve -->	page/2
    	 * 		lo que nos llega es-->	NoticiasPlace:/page/2
    	 */
        String token = placeHistoryMapper.getToken(place);

        /* Reemplazmos el ':' del prefijo por nuestro caracter delimitador */
        if(token != null && token.contains((COLON)))
        	token = token.replace(COLON, SLASH);
         
        /* En caso de que el Place sea simple, le quitamos el caracter final */
       // if(token !=null && token.endsWith(SLASH))
        	//token = token.substring(0, token.length() - 1);

        return token;
    }
}