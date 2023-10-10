package com.farmacloud.client.gui.pharmaUsers;

import java.util.Iterator;
import java.util.List;

import com.farmacloud.client.ui.widgetsold.TablaInfoPedidoView;
import com.farmacloud.client.ui.widgetsold.TablaInfoPedidoViewImp;
import com.farmacloud.shared.model.Pedido;
import com.farmacloud.shared.model.Proveedor;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class PedidosMainViewImp extends Composite implements PedidosMainView
{
	private Presenter presenter;
	
	private AbsolutePanel sPanel;
	private Tree arbolPendientes;
	private Tree arbolIncompletos;
	private Button recepcionar;
	
	private TablaInfoPedidoView tablaPedido;

	public PedidosMainViewImp()
	{
		sPanel = new AbsolutePanel();
		
		/*		A R B O L	P E N D I E N T E S		*/
	    /* Creamos el Arbol de pedidos PENDIENTES*/
	    arbolPendientes = new Tree();
	    arbolPendientes.setAnimationEnabled(true);
	    arbolPendientes.ensureDebugId("cwTree-staticTree");
	    ScrollPanel scrollPendientes = new ScrollPanel(arbolPendientes);
	    scrollPendientes.ensureDebugId("cwTree-staticTree-Wrapper");
	    scrollPendientes.setSize("300px", "300px");
	
	    /* Metemos en Grid en un DecoratorPanel */
	    DecoratorPanel decoratorPendientes = new DecoratorPanel();
	    decoratorPendientes.setWidget(scrollPendientes);
	    
	    /*		A R B O L	I N C O M P L E T O S		*/
	    /* Creamos el Arbol de pedidos INCOMPLETOS */
	    arbolIncompletos = new Tree();
	    arbolIncompletos.setAnimationEnabled(true);
	    arbolIncompletos.ensureDebugId("cwTree-staticTree");
	    ScrollPanel scrollIncompletos = new ScrollPanel(arbolIncompletos);
	    scrollIncompletos.ensureDebugId("cwTree-staticTree-Wrapper");
	    scrollIncompletos.setSize("300px", "300px");
	
	    /* Metemos el Tree en un DecoratorPanel */
	    DecoratorPanel decoratorIncompletos = new DecoratorPanel();
	    decoratorIncompletos.setWidget(scrollIncompletos);
	
	    /* Añdimos los arboles y textos cabeceras a un Grid */
	    Grid grid = new Grid(2, 2);
	    grid.setCellPadding(2);
	    grid.getRowFormatter().setVerticalAlign(1, HasVerticalAlignment.ALIGN_TOP);
	    grid.setHTML(0, 0, "Pedidos Pendientes");
	    grid.setWidget(1, 0, decoratorPendientes);
	    grid.setHTML(0, 1, "Pedidos Incompletos");
	    grid.setWidget(1, 1, decoratorIncompletos);
	    sPanel.add(grid);
	    
	    /* Creamos el widget tabla-pedidos*/
	    tablaPedido = new TablaInfoPedidoViewImp();
	    sPanel.add(tablaPedido);

        recepcionar = new Button("Recepcionar");
        recepcionar.setEnabled(true);
        sPanel.add(recepcionar);
        
	    /* Iniciamos el widget */
	    initWidget(sPanel);
	}
	
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = this.presenter;
	}
	
	@Override
	public Tree getPendientesTree() {
		return this.arbolPendientes;
	}

	@Override
	public Tree getIncompletosTree() {
		return this.arbolIncompletos;
	}
	
	@Override
	public TablaInfoPedidoView getWidgetTablaPedido() {
		return this.tablaPedido;
	}

	@Override
	public HasClickHandlers getRecepcionarButton() {
		return this.recepcionar;
	}

	@Override
	public void setRootData(List<Proveedor> data, Tree arbol) 
	{
		/* Limpiamos los nodos y añadimos los nombres de los proveedores en el 
		 * primer nivel del arbol
		 */
		arbol.clear();
		if(!data.isEmpty())
		{
			for(Proveedor proveedor: data)
			{
					/* Añadimos el nombre del Proveedor en el primer nivel del arbol */
					TreeItem item = new TreeItem();
					item.setText(proveedor.getNombre());
					item.setUserObject(proveedor);
					
					/* Le añadimos un hijo "dummy" por defecto para que el padre sea
					 * desplegable. Cuando se despliegue se sustituira el hijo por la
					 * informacion del pedido correspondiente
					 */
					TreeItem hijo = new TreeItem();
					hijo.setText("");
					item.addItem(hijo);
					arbol.addItem(item);
					
					System.out.println("VISTA " +proveedor.getNombre());
			}
		}
	}

	@Override
	public void setChildData(List<Pedido> pedido, Tree arbol) 
	{
		if(!pedido.isEmpty())
		{
			Iterator<TreeItem> iterador = arbol.treeItemIterator();
			
			/* Recorremos el arbol buscando el elemento de primer nivel detallar. 
			 * Una vez encontrado añadimos los correspondientes hijos con la informacion
			 * del pedido
			 */
			System.out.println("no entro");
			while(iterador.hasNext())
			{
				System.out.println("Entro a iterar");
				TreeItem itemPadre = iterador.next();

				if(itemPadre.getUserObject() instanceof Proveedor)
				{
					Proveedor proveedor = (Proveedor)itemPadre.getUserObject();
					String name = proveedor.getNombre();
					
					//if pedido.getNOMBREPROVEEDOR ojoooooooooooo
					if(pedido.get(0).equals(name))
					{
						/* Eliminamos los anteriores hijos. Actualizamos */
						itemPadre.removeItems();
						
						/* Añadimos los hijos. Que son la informacion del pedido */
						for(Pedido p : pedido)
						{
							System.out.println("SI que añado hijo");
							TreeItem hijo = new TreeItem();
							hijo.setText(p.getIdInterno()+" "+p.getIdExterno());
							hijo.setUserObject(p);
							itemPadre.addItem(hijo);
						}
					}
				}
			}
		}
	}
	
 
}
