package com.farmacloud.client.events;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public abstract class PanelResized extends GenericEvent
{
	boolean shrunk;
	int heightPanel;
	int widthPanel;
	int top;
	int left;
	
	public PanelResized(){
	}
	
	public PanelResized(boolean shrunk, int heightPanel, int widthPanel, int top, int left){
		this.shrunk = shrunk;
		this.heightPanel = heightPanel;
		this.widthPanel = widthPanel;
		this.top = top;
		this.left = left;
	}

	public boolean isShrunk() {
		return shrunk;
	}

	public void setShrunk(boolean shrunk) {
		this.shrunk = shrunk;
	}

	public int getHeightPanel() {
		return heightPanel;
	}

	public void setHeightPanel(int heightPanel) {
		this.heightPanel = heightPanel;
	}

	public int getWidthPanel() {
		return widthPanel;
	}

	public void setWidthPanel(int widthPanel) {
		this.widthPanel = widthPanel;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}
}
