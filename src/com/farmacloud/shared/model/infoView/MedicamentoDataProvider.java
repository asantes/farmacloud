package com.farmacloud.shared.model.infoView;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.gui.pharmaUsers.SimularView;
import com.farmacloud.client.services.ServicioGestionMedicamento;
import com.farmacloud.client.services.ServicioGestionMedicamentoAsync;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AbstractDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

public class MedicamentoDataProvider extends AbstractDataProvider<MedicamentoAbstracto>
{
	private final ServicioGestionMedicamentoAsync servicioGestionMedicamentoAbstracto = GWT.create(ServicioGestionMedicamento.class);
	private List<MedicamentoAbstracto> listaMedicamentos = new ArrayList<MedicamentoAbstracto>();
	private HasData<MedicamentoAbstracto> display;
	private Range rango;
	private int row = -2;
	private SimularView.Presenter presenter;
	
	@Override
	protected void onRangeChanged(HasData<MedicamentoAbstracto> display) 
	{
		this.display = display;
		this.rango = display.getVisibleRange();
		
		System.out.println("Longitud rango "+rango.getLength());
		System.out.println("Start at "+rango.getStart());
		
	//	if(rango.getLength()==100)
			//this.servicioGestionMedicamentoAbstracto.getPorcionCatalogo(rango.getStart(), new GetPorcionCatalogoRellamada());
	}

	class GetPorcionCatalogoRellamada implements AsyncCallback<GetTableWithCursor>
    {
		@Override
		public void onFailure(Throwable caught)
		{
			System.out.println("Client error: recibiendo porcion Catalogo");
			if(presenter!=null)
				presenter.setSelectedRowAfterRangeChanged(false, row);
		}

		@Override
		public void onSuccess(GetTableWithCursor result) 
		{
			int myRow = row;
			System.out.println("Client ok: recibiendo porcion Catalogo");
			
			if(result!=null)
			{
				if(listaMedicamentos.isEmpty())
				{
					listaMedicamentos= new ArrayList<MedicamentoAbstracto>(result.getTotal());
					for(int i=0; i<result.getTotal(); i++)
					{
						MedicamentoAbstracto m = new MedicamentoAbstracto();
						listaMedicamentos.add(m);
					}	
				}
				
				int indiceInteligente = rango.getStart();
				for(MedicamentoAbstracto m : result.getListaMedicamento())
				{
					listaMedicamentos.set(indiceInteligente, m);
					indiceInteligente++;
				}
				
				display.setRowCount(listaMedicamentos.size(), true);
		        display.setRowData(0, listaMedicamentos);
		
		        /* IMPORTANTE COMPROBAR */
		        if(presenter!=null)
		        	presenter.setSelectedRowAfterRangeChanged(true, myRow);
			}
		}
    }

	public List<MedicamentoAbstracto> getList() {
		return listaMedicamentos;
	}

	public void setList(List<MedicamentoAbstracto> listaMedicamentoAbstractos) {
		this.listaMedicamentos = listaMedicamentoAbstractos;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public SimularView.Presenter getPresenter() {
		return presenter;
	}

	public void setPresenter(SimularView.Presenter presenter) {
		this.presenter = presenter;
	}
}
