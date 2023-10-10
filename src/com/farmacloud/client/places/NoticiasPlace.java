package com.farmacloud.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class NoticiasPlace extends Place
{
	String name;
	String pageNumber;
	
	public NoticiasPlace(String name, String pageNumber){
		this.name = name;
		this.pageNumber = pageNumber;
	}
	
	public String getName() {
	   return name;
	}
	
	public String getPageNumber() {
		return pageNumber;
	}

	@Prefix("noticias")
	public static class Tokenizer implements PlaceTokenizer<NoticiasPlace> 
	{
		@Override
		public String getToken(NoticiasPlace place) 
		{
		    return place.getName()+"/"+place.pageNumber;
		}
		
		@Override
		public NoticiasPlace getPlace(String token) 
		{
			String name = "page";
			String pageNumber = "1";
			
			String[] tokens = token.split(":");
			if(tokens.length==2)
			{
				if(tokens[0].equals("page") && tokens[1].matches("[0-9]"))
					{
						name = tokens[0];
			    		pageNumber = tokens[1];
					}
				}
				
			    return new NoticiasPlace(name, pageNumber);
			}
	    }
}
