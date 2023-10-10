package com.farmacloud.client.gui.pharmaUsers;

import java.util.List;

import com.farmacloud.client.gui.helpers.RPCSuggestOracle;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.farmacloud.shared.model.infoView.MedicamentoDataProvider;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public class SimularViewImp extends Composite implements SimularView
{
	Presenter presenter;
	EventBus eventBus;
	Button empezar;
	CellTable<MedicamentoAbstracto> table;	
	SingleSelectionModel<MedicamentoAbstracto> selectionModel;
	ScrollPanel scroll;
	SimplePager pager;
	
	RPCSuggestOracle oracle;
	final SuggestBox suggestBox;
	
	Label medicamentoSelecionado;
	Label labelCodigo;
	TextBox stockMaximo;
	TextBox parametroObservacion;
	Button añadirMedicamento;
	
	TextBox periocidad;
	MedicamentoDataProvider medDataProvider;
	
	public SimularViewImp() 
	{
		VerticalPanel vPanel = new VerticalPanel();

	    table = new CellTable<MedicamentoAbstracto>();
	    table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	    prepararTabla();
	    
	    // Define the oracle that finds suggestions
	     //oracle = new RPCSuggestOracleImp();

	    // Create the suggest box
	    suggestBox = new SuggestBox(oracle);
	    oracle.setSuggestWidget(suggestBox);
	    vPanel.add(new HTML("Choose a word",true));
	    vPanel.add(suggestBox);
	    
	    // Do not refresh the headers and footers every time the data is updated.
	    table.setAutoHeaderRefreshDisabled(true);
	    table.setAutoFooterRefreshDisabled(true);
	    
	    // Add a selection model to handle user selection.
	    selectionModel= new SingleSelectionModel<MedicamentoAbstracto>();
	    table.setSelectionModel(selectionModel);

	    /* Creamos y añadimos el scroll a la tabla */
	    scroll = new ScrollPanel(table);
	    scroll.setWidth("100%");
	    scroll.setHeight("20em");
	    vPanel.add(scroll);
	
	    /* Creamos un DataProvider para disponer de paginado */ 
	    medDataProvider = new MedicamentoDataProvider();
	    medDataProvider.addDataDisplay(table);

	    /* Añadimos un Pager para controlar el paginado de la tabla */
	    pager = new SimplePager();
	    pager.setDisplay(table);
	    pager.setPageSize(100);
	    vPanel.add(pager);
		
		medicamentoSelecionado = new Label("");
		vPanel.add(medicamentoSelecionado);
		
		labelCodigo = new Label("");
		vPanel.add(labelCodigo);
		
		Label labelStockMaximo = new Label("Stock maximo");
		vPanel.add(labelStockMaximo);
		stockMaximo = new TextBox();
		vPanel.add(stockMaximo);
		
		Label labelParametroObservacion = new Label("Parametro");
		vPanel.add(labelParametroObservacion);
		parametroObservacion = new TextBox();
		vPanel.add(parametroObservacion);
		
		añadirMedicamento = new Button("Añadir a simulacion");
		vPanel.add(añadirMedicamento);
		
		Label etiquetaPeriocidad = new Label();
		etiquetaPeriocidad.setText("Periocidad");
		vPanel.add(etiquetaPeriocidad);
		
		periocidad = new TextBox();
		vPanel.add(periocidad);
		
		empezar = new Button("Empezar");
		vPanel.add(empezar);
		
		/* M A N E J A D O R A S */
		table.addCellPreviewHandler(new TablaMedicamentosPreviewHandler());
		table.addDomHandler(new TablaMedicamentosDoubleClickHandler(),DoubleClickEvent.getType());
		//suggestBox.addSelectionHandler(new SuggestBoxSelectionHandler());		
		/*  = * = * = * = * = * = */
		
		initWidget(vPanel);
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setDataTable(List<MedicamentoAbstracto> data) 
	{
        // Set the total row count. This isn't strictly necessary,
        // but it affects paging calculations, so its good habit to
        // keep the row count up to date.
        table.setRowCount(data.size(), true);
        // Push the data into the widget.
        table.setRowData(0, data);
     //   dataProvider.setList(data);
        
		System.out.println("yeah");
	}
	
	@Override
	public void setDataSliceTable(List<MedicamentoAbstracto> result) 
	{
		// Set the total row count. This isn't strictly necessary,
        // but it affects paging calculations, so its good habit to
        // keep the row count up to date.
        table.setRowCount(result.size(), true);
        // Push the data into the widget.
        table.setRowData(0, result);
        medDataProvider.setList(result);
        
		System.out.println("yeah");
	} 
	
	public RPCSuggestOracle getOracle(){
		return this.oracle;
	}
	
	class TablaMedicamentosPreviewHandler implements CellPreviewEvent.Handler<MedicamentoAbstracto>
	{
		@Override
		public void onCellPreview(CellPreviewEvent<MedicamentoAbstracto> event) 
		{
			 if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_UP  &&  event.getNativeEvent().getType().equals("keydown")
			  || event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DOWN && event.getNativeEvent().getType().equals("keydown"))
			    {
				   int cuenta = pager.getPage() * pager.getPageSize();
		           selectionModel.setSelected(medDataProvider.getList().get(table.getKeyboardSelectedRow()+cuenta), true);
			    }
			    
		    if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER && event.getNativeEvent().getType().equals("keydown"))
		    {
		    	  MedicamentoAbstracto selected = (MedicamentoAbstracto) selectionModel.getSelectedObject();
	              if (selected != null) {
	            	  
	              }
		    }
		}
	}
	
	class TablaMedicamentosDoubleClickHandler implements DoubleClickHandler
	{
		@Override
		public void onDoubleClick(DoubleClickEvent event) 
		{
			  MedicamentoAbstracto  selected = (MedicamentoAbstracto) selectionModel.getSelectedObject();
              if (selected != null) {
            	 medicamentoSelecionado.setText(selected.getNombre());
            	 labelCodigo.setText(selected.getCodigoNacional());
              }
		}
	}
	
	public void prepararTabla() 
	{
		TextColumn<MedicamentoAbstracto> codigoColumn = new TextColumn<MedicamentoAbstracto>() {
			@Override
			public String getValue(MedicamentoAbstracto object) {
				return object.getCodigoNacional();
			}
	      };
	    table.addColumn(codigoColumn, "Codigo nacional");

	    //
	   TextColumn<MedicamentoAbstracto> nameColumn = new TextColumn<MedicamentoAbstracto>() {
	         @Override
	         public String getValue(MedicamentoAbstracto object) {
	            return object.getNombre();
	         }
	      };
	    table.addColumn(nameColumn, "Nombre");

	    //
	    TextColumn<MedicamentoAbstracto> laboratorioColumn = new TextColumn<MedicamentoAbstracto>(){
	         @Override
	         public String getValue(MedicamentoAbstracto object) {
	            return object.getLaboratorio();
	         }
	      };
	    table.addColumn(laboratorioColumn, "Laboratorio");

	    //
	    TextColumn<MedicamentoAbstracto> principioColumn = new TextColumn<MedicamentoAbstracto>(){
	         @Override
	         public String getValue(MedicamentoAbstracto object) {
	            return object.getPrincipioActivo();
	         }
	      };
	    table.addColumn(principioColumn, "Principio activo");
	    
	    //
	    TextColumn<MedicamentoAbstracto> pvpColumn = new TextColumn<MedicamentoAbstracto>(){
	         @Override
	         public String getValue(MedicamentoAbstracto object) {
	            return String.valueOf(object.getPrecioPVP());
	         }
	      };
	    table.addColumn(pvpColumn, "PVP");
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public HasClickHandlers getSimular() {
		return this.empezar;
	}

	@Override
	public HasClickHandlers getAñadir() {
		return this.añadirMedicamento;
	}

	@Override
	public String getParamentro() {
		return this.parametroObservacion.getValue();
	}

	@Override
	public String getSockMaximo() {
		return this.stockMaximo.getValue();
	}

	@Override
	public String getCodigo() {
		return this.labelCodigo.getText();
	}

	@Override
	public String getPeriocidad() {
		return this.periocidad.getText();
	}

	@Override
	public SimplePager getPager() {
		return this.pager;
	}

	@Override
	public SuggestBox getSuggestBox() {
		return this.suggestBox;
	}

	@Override
	public MedicamentoDataProvider getMedicamentoDataProvider() {
		return this.medDataProvider;
	}

	@Override
	public SelectionModel getSelectionModel() {
		return this.selectionModel;
	}

	@Override
	public CellTable getTable() {
		return this.table;
	}

	@Override
	public void setPresenterOnDataProvider() {
		this.medDataProvider.setPresenter(this.presenter);	
	}	
}
