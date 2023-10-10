package com.farmacloud.client.ui.widgets;

import java.util.Date;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.NavbarLink;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.html.Paragraph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class NoticiaWidget extends Composite {

	private static NoticiaWidgetUiBinder uiBinder = GWT
			.create(NoticiaWidgetUiBinder.class);

	interface NoticiaWidgetUiBinder extends UiBinder<Widget, NoticiaWidget> {
	}
	
	@UiField
	Hyperlink titular;
	@UiField
	Heading fecha;
	@UiField
	Hyperlink autor;
	@UiField
	Paragraph cuerpo;

	public NoticiaWidget() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	public @UiConstructor NoticiaWidget(String _cuerpoNoticia, String _titular, String _autor, Date _fecha)
	{
		initWidget(uiBinder.createAndBindUi(this));
		
		titular.setHTML(stringToHTMLHeader(_titular, 1));
		fecha.setText(formatearFecha(_fecha));
		autor.setHTML(stringToHTMLHeader(_autor, 4));
		cuerpo.setHTML(_cuerpoNoticia);
	}
	
	public String stringToHTMLHeader(String text, int size)
	{
		if(size>=1 && size<=6)
		{
			String sizeHeader = String.valueOf(size);
			String htmlHeader = "<h"+sizeHeader+">"+text+"</h"+sizeHeader+">";
			return htmlHeader;
		}
		else return text;
	}
	
	public String formatearFecha(Date fecha)
	{
		String hora = String.valueOf(fecha.getHours());
		String minutos = String.valueOf(fecha.getMinutes());
		
		String dia = String.valueOf(fecha.getDate());
		int mesNumero = fecha.getMonth();
		String mesLetra = numberToMonth(mesNumero);
		int añoChapuza = fecha.getYear() + 1900;
		String añoBien = String.valueOf(añoChapuza);
		
		String fechaFormateada = dia+" de "+mesLetra+", "+añoBien;
		
		return fechaFormateada;
	}
	
	public String numberToMonth(int num)
	{
		String nombreMes;
		
		switch (num)
		{
		case 0:
			nombreMes = "enero";
			break;
			
		case 1:
			nombreMes = "febrero";
			break;
			
		case 2:
			nombreMes = "marzo";
			break;
			
		case 3:
			nombreMes = "abril";
			break;
			
		case 4:
			nombreMes = "mayo";
			break;
			
		case 5:
			nombreMes = "junio";
			break;
			
		case 6:
			nombreMes = "julio";
			break;
			
		case 7:
			nombreMes = "agosto";
			break;
			
		case 8:
			nombreMes = "septiembre";
			break;
			
		case 9:
			nombreMes = "octubre";
			break;
			
		case 10:
			nombreMes = "noviembre";
			break;
			
		case 11:
			nombreMes = "diciembre";
			break;
			
		default:
			nombreMes = "eldecimotercermes";
			break;
		}
		
		return nombreMes;
	}
}
