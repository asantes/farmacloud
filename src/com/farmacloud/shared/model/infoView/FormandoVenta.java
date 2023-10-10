package com.farmacloud.shared.model.infoView;

import java.util.List;

import com.farmacloud.shared.model.LineaVenta;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

public class FormandoVenta 
{
	private ListDataProvider<LineaVenta> dataProvider = new ListDataProvider<LineaVenta>();
	
	public FormandoVenta(){
	}

	public void addMedicament(LineaVenta medicamentoPedido)
	 {
		 List<LineaVenta> listaMedicamentos = dataProvider.getList();
	 	 listaMedicamentos.add(medicamentoPedido);
	 }
	
	public void removeMedicament(LineaVenta medicamento)	{
		List<LineaVenta> listaMedicamentos = dataProvider.getList();
		listaMedicamentos.remove(medicamento);
	}

	 public void addDataDisplay(HasData<LineaVenta> display) {
		 dataProvider.addDataDisplay(display);
	 }

	 public void refreshDisplays() {
		 dataProvider.refresh();
	 }
	 
	 public ListDataProvider<LineaVenta> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(ListDataProvider<LineaVenta> dataProvider) {
		this.dataProvider = dataProvider;
	}

}
