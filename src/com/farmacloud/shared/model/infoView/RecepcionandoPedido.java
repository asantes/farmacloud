package com.farmacloud.shared.model.infoView;

import java.util.List;

import com.farmacloud.shared.model.LineaPedido;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

public class RecepcionandoPedido 
{
	private ListDataProvider<LineaPedido> dataProvider = new ListDataProvider<LineaPedido>();
	
	public RecepcionandoPedido() {
	}

	public void addMedicament(LineaPedido medicamento)
	 {
		 List<LineaPedido> listaMedicamentos = dataProvider.getList();
		 boolean duplicado = false;
		 
		 /* En caso de que la lista este vacia, añadimos directamente */
		 if(listaMedicamentos.isEmpty())
			 listaMedicamentos.add(medicamento);
		 
		 else
		 {
			 /* Verificamos si es un elemento añadido anteriormente */
			 for(LineaPedido m: listaMedicamentos)
			 {
				 if(m.getCodigoNacional().equals(medicamento.getCodigoNacional()))
					 duplicado = true;
			 }
			 /* Si es nuevo MedicamentoInfo lo añadimos, si es un duplicado no */
			 if(!duplicado)
				 listaMedicamentos.add(medicamento);
		 }
	 }
	
	public void removeMedicament(LineaPedido medicamento){
		List<LineaPedido> listaMedicamentos = dataProvider.getList();
		listaMedicamentos.remove(medicamento);
	}

	 public void addDataDisplay(HasData<LineaPedido> display) {
		 dataProvider.addDataDisplay(display);
	 }

	 public void refreshDisplays() {
		 dataProvider.refresh();
	 }
	 
	 public ListDataProvider<LineaPedido> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(ListDataProvider<LineaPedido> dataProvider) {
		this.dataProvider = dataProvider;
	}
}