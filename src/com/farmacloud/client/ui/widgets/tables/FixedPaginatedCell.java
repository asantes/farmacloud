package com.farmacloud.client.ui.widgets.tables;

import java.util.List;

import org.gwtbootstrap3.client.ui.gwt.DataGrid;

import com.farmacloud.client.ui.widgets.MyPagination;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public abstract class FixedPaginatedCell<T> extends Composite implements FixedPaginatedCellIterface<T>{

	int numMaxIndexes = 10;
	SimplePager pager = new SimplePager();
	ListDataProvider<T> dataProvider = new ListDataProvider<T>();
	SingleSelectionModel<T> selectionModel = new SingleSelectionModel<T>();
	
	@UiField
	public AbstractCellTable<T> table;
	@UiField
	public MyPagination pagination;

	public FixedPaginatedCell() {
	}
	
	protected abstract void initColumns();

	protected void initTable()
	{
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		table.setAutoHeaderRefreshDisabled(true); // Do not refresh the headers and footers every time the data is updated.
		table.setAutoFooterRefreshDisabled(true);
	    table.setSelectionModel(selectionModel);

		pager.setDisplay(table);
		pager.setPageSize(50);
		pagination.clear();
		dataProvider.addDataDisplay(table);
		
		table.addRangeChangeHandler(new RangeChangeEvent.Handler()
		{
			@Override
			public void onRangeChange(RangeChangeEvent event) {
				pagination.rebuild(pager, numMaxIndexes);
			}
		});
		
		table.addCellPreviewHandler(new CellPreviewEventHandler());
	}
	
	public void setDataTable(List<T> data) 
	{
		if(data!=null){
			dataProvider.setList(data);
			dataProvider.flush();
			pagination.rebuild(pager, numMaxIndexes);
		}
	}
	
	public void setNumMaxIndexes(int num){
		this.numMaxIndexes = num;
	}
	
	@Override
	public void selectRowAndPage(int index, T element)
	{
		int cuenta = index/pager.getPageSize();
		int resto = index%pager.getPageSize();
		/* Nos movemos a la pagina donde esta la fila que queremos selecionar */
		if(pager.getPage()!=cuenta)
			pager.setPage(cuenta);
		selectionModel.setSelected(element, true); /* Selecionamos el elemento en el modelo. Tambien se refleja graficamente */
		table.setKeyboardSelectedRow(resto, true); /* Esto es para que haga scroll hasta el elemento selecionado graficamente*/
	}
	
	public List<T> getData(){
		return dataProvider.getList();
	}
	
	public AbstractCellTable<T> getTable(){
		return table;
	}
	
	class CellPreviewEventHandler implements CellPreviewEvent.Handler<T>
	{
		@Override
		public void onCellPreview(CellPreviewEvent<T> event) 
		{
			 if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_UP  &&  event.getNativeEvent().getType().equals("keydown")
			  || event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DOWN && event.getNativeEvent().getType().equals("keydown"))
			    {
				   int cuenta = pager.getPage() * pager.getPageSize();
		           selectionModel.setSelected(dataProvider.getList().get(table.getKeyboardSelectedRow()+cuenta), true);
			    }
			    
		    if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER && event.getNativeEvent().getType().equals("keydown"))
		    {
		    	  /*MedicamentoAbstracto selected = (MedicamentoAbstracto) selectionModel.getSelectedObject();
	              if (selected != null) {  
	              }*/
		    }
		}
	}
}
