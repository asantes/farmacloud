package com.farmacloud.client.presenter;

import com.google.gwt.place.shared.Place;

public interface Presenter 
{
	/**
     * Ask user before stopping this activity
     */
    //public String mayStop();

    /**
     * Navigate to a new Place in the browser
     */
    public void goTo(Place place);
}
