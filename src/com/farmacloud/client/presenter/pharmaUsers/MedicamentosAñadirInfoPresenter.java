package com.farmacloud.client.presenter.pharmaUsers;

import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.gui.pharmaUsers.MedicamentosAñadirInfoView;
import com.farmacloud.client.gui.pharmaUsers.MedicamentosAñadirInfoView.Presenter;
import com.farmacloud.client.services.ServicioGestionMedicamento;
import com.farmacloud.client.services.ServicioGestionMedicamentoAsync;
import com.farmacloud.shared.model.MedicamentoFarmacia;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class MedicamentosAñadirInfoPresenter extends InfoMedicamentoPresenter implements Presenter
{
	private MedicamentosAñadirInfoView view;
	private final ServicioGestionMedicamentoAsync servicioGestionMedicamento = GWT.create(ServicioGestionMedicamento.class);
	
	public MedicamentosAñadirInfoPresenter(ClientFactory clientFactory) {
		super(clientFactory);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus)
	{
		this.view = getClientFactory().getMedicamentosAñadirInfoView();
		this.view.setPresenter(this);
		/* Obtenemos el custom widget para establecer comunicacion widget-presenter */
		this.setView(this.view.getInfoMedicamentoViewImp());
		this.view.getInfoMedicamentoViewImp().setPresenter(this);
		
		/* R E G I S T R A T I O N S */
		getListHandlerRegistration().add(this.view.getAddButton().addClickHandler(new BotonAñadirManejadora()));
		panel.setWidget(view.asWidget());
	}
	
	class BotonAñadirManejadora implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) {
			List<String> inputData = view.getInputData();
			añadirNuevoMedicamento(inputData);
		}
	}
	
	void añadirNuevoMedicamento(List<String> inputData)
	{
		/*this.servicioGestionMedicamento.añadirNuevoMedicamento(inputData, new AsyncCallback<MedicamentoFarmacia>()
		{	
			@Override
			public void onSuccess(MedicamentoFarmacia result) {
				System.out.println("CLIENT OK: rpc-añadir nuevo medicamento");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("CLIENT ERROR: rpc-añadir nuevo medicamento");	
			}
		});*/
	}
}
