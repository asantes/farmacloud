package com.farmacloud.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;

public class PlaceControllerExt extends PlaceController
{
	  private final Place defaultPlace;
	  private Place previousPlace;
	  private Place currentPlace;

	  public PlaceControllerExt(EventBus eventBus, Place defaultPlace) 
	  {
	      super(eventBus);
	      this.defaultPlace = defaultPlace;
	      eventBus.addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {

	          public void onPlaceChange(PlaceChangeEvent event) {

	              previousPlace = currentPlace;
	              currentPlace = event.getNewPlace();
	              System.out.println("N U E V O   S I T I O   "+currentPlace.toString());
	          }
	      });
	  }

	  /**
	   * Navigate back to the previous Place. If there is no previous place then
	   * goto to default place. If there isn't one of these then it'll go back to
	   * the default place as configured when the PlaceHistoryHandler was
	   * registered. This is better than using History#back() as that can have the
	   * undesired effect of leaving the web app.
	   */
	  public void previous() {

	      if (!isPreviousNull()) {
	          goTo(previousPlace);
	      } else {
	          goTo(defaultPlace);
	      }
	  }
	  
	  public boolean isPreviousNull(){
		  if(this.previousPlace==null)
			  return true;
		  else return false;
	  }
}