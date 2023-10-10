package com.farmacloud.client.ui.widgets;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyType;
import org.gwtbootstrap3.extras.notify.client.ui.Notify;
import org.gwtbootstrap3.extras.notify.client.ui.NotifySettings;

public class UiHelper {
	
	public static void Notify(boolean exito, String title, String message)
	{
		NotifySettings settings =  NotifySettings.newSettings();
		IconType iconType;
		
		if(exito){
			settings.setType(NotifyType.SUCCESS);
			iconType = IconType.SMILE_O;
		}
		else{
			settings.setType(NotifyType.WARNING);
			iconType = IconType.MEH_O;
		}
		
		settings.setAllowDismiss(false);
		Notify.notify(title, message, iconType, settings);
	}
}
