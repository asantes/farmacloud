package com.farmacloud.shared.model.infoView;

import java.util.List;

import com.farmacloud.shared.model.LineaPedido;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

public class ConstruyendoPedido 
{
	private ListDataProvider<LineaPedido> dataProvider = new ListDataProvider<LineaPedido>();
	
	public ConstruyendoPedido() {
	}
	
	public void addMedicament(LineaPedido medicamentoPedido)
	 {
		 List<LineaPedido> listaMedicamentos = dataProvider.getList();
		 boolean duplicado = false;
		 int indiceRepetido = 0;
		 
		 /* En caso de que la lista este vacia, añadimos directamente */
		 if(listaMedicamentos.isEmpty()){
			 medicamentoPedido.setUnidadesPedidas(1);
			 listaMedicamentos.add(medicamentoPedido);
		 }
		 
		 else
		 {
			 /* Verificamos si es un elemento añadido anteriormente */
			 for(LineaPedido m: listaMedicamentos)
			 {
				 if(m.getCodigoNacional().equals(medicamentoPedido.getCodigoNacional())){
					 duplicado = true;
					 indiceRepetido = listaMedicamentos.indexOf(m);
					 System.out.println("INDICE -->" +indiceRepetido);
				 }
			 }
			 /* Si es nuevo MedicamentoInfo lo añadimos, si es un duplicado no */
			 if(!duplicado){
				 medicamentoPedido.setUnidadesPedidas(medicamentoPedido.getUnidadesPedidas()+1);
				 listaMedicamentos.add(medicamentoPedido);
			 	}
			 else{
				 listaMedicamentos.get(indiceRepetido).setUnidadesPedidas(
						 				   listaMedicamentos.get(indiceRepetido).getUnidadesPedidas()+1);
			 }
		 }
	 }
	
	public void removeMedicament(LineaPedido medicamento)	{
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
