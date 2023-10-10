package com.farmacloud.client.ui.widgets;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PopupPreview extends PopupPanel
{   
	/* Tenemos un ScrollPanel que contiene a su vez un VerticalPanel en el que se mostrara el preview y el boton para cerrar */
	private ScrollPanel scrollPanel;
    private VerticalPanel vPanel;
    private Button cerrar;
    private NoticiaWidget noticia;
    
	public PopupPreview()
	{
		final PopupPreview esto = this;
		
		this.setAutoHideEnabled(true);
	    this.setAnimationEnabled(true);
	    this.setGlassEnabled(true);
	    this.setGlassStyleName("preview-noticia-glass");
	    this.setStylePrimaryName("preview-noticia");
	   
	    /* Creamos el ScrollPanel */
	    scrollPanel = new ScrollPanel();
	    scrollPanel.addStyleName("scrollPanel-preview");
	 
	    /* Creamos el VerticalPanel. Este contendra el preview y un boton*/
	    vPanel = new VerticalPanel();
	    vPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
	    vPanel.addStyleName("vPanel-preview");
	    
	    cerrar = new Button("X");
	    cerrar.setTitle("cerrar");
	    cerrar.setStylePrimaryName("cerrarPreview");
	    cerrar.addClickHandler(new ClickHandler() 
	    {
			@Override
			public void onClick(ClickEvent event) {
				esto.hide();
			}
		});
	   
	    /* Añadimos el boton y el preview de la noticia*/
	    vPanel.add(cerrar);
	   noticia = new NoticiaWidget("", "", "", new Date()); /* Le añadimos una noticia en blanco de primeras */
	   vPanel.add(noticia);
	    
	    scrollPanel.setWidget(vPanel);	    
	    this.setWidget(scrollPanel);
	}
	
	public void incorporarNoticia(NoticiaWidget noticiaWidget)
	{
		vPanel.remove(noticia);
		this.noticia = noticiaWidget;
		vPanel.add(noticiaWidget);
	}
}
