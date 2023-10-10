package com.farmacloud.client.ui.widgets;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class FocusedPopup extends PopupPanel
{
	private boolean focused;
	private boolean widgetFocused;
	private FocusedPopup esto;
	
	private int MARGEN = 350;
	private Widget widget;
	
	Timer delay = new Timer() 
	{	
		@Override
		public void run() 
		{
			hide();
			widget.setStylePrimaryName("seccionMenu");
			widgetFocused = false;
		}
	};
	
	Timer tFoco = new Timer() 
	{	
		@Override
		public void run() 
		{
			if(!widgetFocused && !focused)
			{
				hide();
				widget.setStylePrimaryName("seccionMenu");
			}
		}
	};
	
	public FocusedPopup(Widget widget)
	{
		super();
		this.widget = widget;
		
		MouseOver mouseOver = new MouseOver();
		MouseOut mouseOut = new MouseOut();
		this.addDomHandler(mouseOver, MouseOverEvent.getType());
		this.addDomHandler(mouseOut, MouseOutEvent.getType());
		this.widget.addDomHandler(mouseOver, MouseOverEvent.getType());
		this.widget.addDomHandler(mouseOut, MouseOutEvent.getType());
		
		setStylePrimaryName("focusedPopup");
		esto = this;
	}
	
	private class MouseOver implements MouseOverHandler
	{
		@Override
		public void onMouseOver(MouseOverEvent event) 
		{
			if(event.getSource().equals(esto))
				focused = true;
			else if(event.getSource().equals(widget))
					widgetFocused = true;
		}	
	}
	
	private class MouseOut implements MouseOutHandler
	{
		@Override
		public void onMouseOut(MouseOutEvent event) 
		{
			if(event.getSource().equals(esto))
			{
				if(esto.isShowing())
				{
					focused = false;
					tFoco.schedule(MARGEN);
				}
			}
			else if(event.getSource().equals(widget))
			{
				if(isShowing())
				{	
					widgetFocused = false;
					tFoco.schedule(MARGEN);
				}
			}
			
		}	
	}
	
	public boolean isFocused(){
		return this.focused;
	}
	
	public void setFocused(boolean focused){
		this.focused = focused;
	}
	
	public boolean isWidgetFocused() {
		return widgetFocused;
	}

	public void setWidgetFocused(boolean widgetFocused) {
		this.widgetFocused = widgetFocused;
	}

	public void hideAfterTime(){
		delay.schedule(MARGEN);
	}
	
	@Override
	public void show(){
		super.show();
	}
}

