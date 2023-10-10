package com.farmacloud.shared.model.infoView;

import java.util.List;

import com.farmacloud.shared.model.simulacion.MedicamentoFarmaciaSimulacion;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

public class SimulacionRunningDataProvider 
{
	private ListDataProvider<MedicamentoFarmaciaSimulacion> dataProvider = new ListDataProvider<MedicamentoFarmaciaSimulacion>();
	
	public SimulacionRunningDataProvider() {
	}

	public void addMedicament(MedicamentoFarmaciaSimulacion medicamento)
	 {
		 List<MedicamentoFarmaciaSimulacion> listaMedicamentos = dataProvider.getList();
		 boolean duplicado = false;
		 
		 /* En caso de que la lista este vacia, añadimos directamente */
		 if(listaMedicamentos.isEmpty())
			 listaMedicamentos.add(medicamento);
		 
		 else
		 {
			 /* Actualizamos el MedicamentoFarmaciaSimulacion con los nuevos valores */
			 int i = 0;
			 for(MedicamentoFarmaciaSimulacion m: listaMedicamentos)
			 {
				 if(m.getCodigoNacional().equals(medicamento.getCodigoNacional()))
				 {
					 listaMedicamentos.set(i, medicamento);;
				 }
			 }
	
		 }
	 }
	
	public void removeMedicament(MedicamentoFarmaciaSimulacion medicamento){
		List<MedicamentoFarmaciaSimulacion> listaMedicamentos = dataProvider.getList();
		listaMedicamentos.remove(medicamento);
	}

	 public void addDataDisplay(HasData<MedicamentoFarmaciaSimulacion> display) {
		 dataProvider.addDataDisplay(display);
	 }

	 public void refreshDisplays() {
		 dataProvider.refresh();
	 }
	 
	 public ListDataProvider<MedicamentoFarmaciaSimulacion> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(ListDataProvider<MedicamentoFarmaciaSimulacion> dataProvider) {
		this.dataProvider = dataProvider;
	}
}
