package com.farmacloud.client.gui.pharmaUsers;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.shared.model.LineaVenta;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

public class VentaViewImp extends Composite implements VentaView
{
	private Presenter presenter;
	
	/* Tabla con los medicamentos de la venta */
	private CellTable<LineaVenta> tablaMedicamentosPedido;
	private SingleSelectionModel<LineaVenta> selectionModelPedido;
	private ScrollPanel scrollMedicamentosPedido;
	
	private TextBox cajaEscaner;
	private Label mensaje;
	private Label precioTotal;
	
	private Button cerrarVenta;
	
	public VentaViewImp() 
	{
		VerticalPanel vPanel = new VerticalPanel();
		SimplePanel sPanel = new SimplePanel();
		
		cajaEscaner = new TextBox();
		cajaEscaner.addKeyUpHandler(new ManejadoraCajaEscaner());
		vPanel.add(cajaEscaner);
		
		mensaje = new Label();
		vPanel.add(mensaje);
		
	     /*		T A B L A		*/
		/* Creamos la tabla con los medicamentos que forman la venta */
	    tablaMedicamentosPedido = new CellTable<LineaVenta>();
	    tablaMedicamentosPedido.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	    prepararTablaPedido();
	    
	    // Add a selection model to handle user selection.
	    selectionModelPedido = new SingleSelectionModel<LineaVenta>();
	    tablaMedicamentosPedido.setSelectionModel(selectionModelPedido);
	    
	    /* Creamos y añadimos el scroll a la tabla con los medicamentos que forman el pedido*/
	    scrollMedicamentosPedido = new ScrollPanel(tablaMedicamentosPedido);
	    scrollMedicamentosPedido.setWidth("100%");
	    scrollMedicamentosPedido.setHeight("20em");
	    
	    /* Añadimos el scroll(con la tabla) al panel */
	    sPanel.add(scrollMedicamentosPedido);
	            
	    /* Añadimos estilo propio */
	    scrollMedicamentosPedido.setStylePrimaryName("scrollPanel");
	    
	    vPanel.add(sPanel);
	    
	    Label textoPrecioTotal = new Label("Total ");
	    vPanel.add(textoPrecioTotal);
	    precioTotal = new Label();
	    vPanel.add(precioTotal);
	    
	    cerrarVenta = new Button("Cerrar venta");
	    vPanel.add(cerrarVenta);
	    
	    initWidget(vPanel);
		
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void setPrecioTotal(String precio) {
		this.precioTotal.setText(precio);
	}
	
	@Override
	public CellTable<LineaVenta> getSellTable() {
		return this.tablaMedicamentosPedido;
	}
	
	@Override
	public HasClickHandlers getCerrarVenta() {
		return this.cerrarVenta;
	}
	
	class ManejadoraCajaEscaner implements KeyUpHandler
	{
		@Override
		public void onKeyUp(KeyUpEvent event) 
		{
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
			{
				String cadenaIntroducida = cajaEscaner.getValue();
				String cadenaVerificada = presenter.verificarCodigoIntroducido(cadenaIntroducida);
			
				/* Verificamos que es valida */
				if(cadenaVerificada!=null)
				{
					if(cadenaVerificada.length()==6)
					{
						cajaEscaner.setValue(cadenaVerificada);
						presenter.buscarMedicamentoVenta(cadenaVerificada);
					}
				}
			}
		}
	}
	
	public void setMensaje(String msg, boolean visibilidad) {
		this.mensaje.setText(msg);
		this.mensaje.setVisible(visibilidad);
	}
	
	public void prepararTablaPedido() 
	{
	    final List<String> lista = new ArrayList<String>();
	    lista.add("ninguno");
	    lista.add("TSI001");
	    lista.add("TSI002");
	    lista.add("TSI003");
	    lista.add("TSI004");
	    lista.add("TSI005");
	    lista.add("TSI006");
	    
	    SelectionCell listaCell = new SelectionCell(lista);
	    Column<LineaVenta, String> tsiColumn = new Column<LineaVenta, String>(listaCell) {
			@Override
			public String getValue(LineaVenta object) {
				//return object.getTSI();
				return "";
			}
	    };
	    tablaMedicamentosPedido.addColumn(tsiColumn, "TSI");
	    
	    tsiColumn.setFieldUpdater(new FieldUpdater<LineaVenta, String>() 
	    {
	      public void update(int index, LineaVenta object, String value) 
	      {
	        for (String s : lista)
	        {
	          if (s.equals(value)) 
	          {
	           // object.setTSI(s);
	            presenter.actualizarPrecioUnidad(index);
	          }
	        }
	      }
	    });
	    
		TextColumn<LineaVenta> codigoColumn = new TextColumn<LineaVenta>() {
			@Override
			public String getValue(LineaVenta object) {
				//return object.getUnidadMedicamento().getCodigoNacional();
				return "";
			}
	      };
	    tablaMedicamentosPedido.addColumn(codigoColumn, "Codigo nacional");
	    
	   
	   TextColumn<LineaVenta> nameColumn = new TextColumn<LineaVenta>() {
	         @Override
	         public String getValue(LineaVenta object) {
	           // return object.getUnidadMedicamento().getMedicamento().getNombre();
	            return"";
	         }
	      };
	    tablaMedicamentosPedido.addColumn(nameColumn, "Nombre");
	    
	    
	   TextColumn<LineaVenta> pvp = new TextColumn<LineaVenta>(){
	         @Override
	         public String getValue(LineaVenta object) {
	            //return String.valueOf(object.getUnidadMedicamento().getMedicamento().getPrecioPVP());
	        	 return "";
	         }
	      };
	   tablaMedicamentosPedido.addColumn(pvp, "PVP");
	   
	   TextColumn<LineaVenta> aportacion = new TextColumn<LineaVenta>(){
	         @Override
	         public String getValue(LineaVenta object) {
	        	// String aportacion = String.valueOf(object.getAportacion());
	          //  return aportacion+"%";
	            return "";
	         }
	      };
	   tablaMedicamentosPedido.addColumn(aportacion, "Aportacion");
	   
	   TextColumn<LineaVenta> precioFinal = new TextColumn<LineaVenta>(){
	         @Override
	         public String getValue(LineaVenta object) {
	            return String.valueOf(object.getPrecioFinal());
	         }
	      };
	   tablaMedicamentosPedido.addColumn(precioFinal, "PVP");

	}
}
