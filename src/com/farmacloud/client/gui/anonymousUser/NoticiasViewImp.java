package com.farmacloud.client.gui.anonymousUser;

import java.util.Date;

import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.constants.PanelType;
import org.gwtbootstrap3.client.ui.html.Div;

import com.farmacloud.client.ui.widgets.NoticiaWidget;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NoticiasViewImp extends Composite implements NoticiasView
{
	Presenter presenter;
	Panel  mainPanel; 
	
	public NoticiasViewImp()
	{
		mainPanel = new Panel();
		mainPanel.setType(PanelType.DEFAULT);
		mainPanel.getElement().setId("panelNoticias");

		initWidget(mainPanel);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void showNoticia(String cuerpoNoticia, String titular, String autor, Date fecha)
	{
		NoticiaWidget noticia = new NoticiaWidget(cuerpoNoticia, titular, autor, fecha);
		this.mainPanel.add(noticia);
	}
	
	@Override
	public void buildAndAddNavigator(int indiceStart, int indiceEnd, int indiceActual)
	{
		System.out.println("INDICES: "+indiceStart+" y "+indiceEnd);
	
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		hp.setStylePrimaryName("paginador");
		
		/* Añadimos un link por cada pagina que compone el pager de navegacion */
		for(int i=indiceStart; i<=indiceEnd; i++)
		{
			if(i!=indiceActual)
			{
				String token =  presenter.getToken(i);
				Hyperlink link = new Hyperlink(String.valueOf(i), token);
				link.setStylePrimaryName("indicePaginador");
				hp.add(link);
			}
			else
			{
				Hyperlink link = new Hyperlink(String.valueOf(i),"");
				link.setStylePrimaryName("indicePaginador");
				hp.add(link);
				link.setStyleDependentName("indiceActual", true);
			}
		}
	
		mainPanel.add(hp);
	}

	@Override
	public void setFooter(Widget footerNavigation){
		mainPanel.add(footerNavigation);
		Window.scrollTo(0, mainPanel.getAbsoluteTop()+1000);
	}

	@Override
	public void cleanView() {
		this.mainPanel.clear();
	}
}
