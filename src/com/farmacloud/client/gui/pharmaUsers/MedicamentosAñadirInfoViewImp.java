package com.farmacloud.client.gui.pharmaUsers;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.ui.widgetsold.InfoMedicamentoViewImp;
import com.farmacloud.client.ui.widgetsold.InfoMedicamentoView.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MedicamentosAñadirInfoViewImp extends Composite implements MedicamentosAñadirInfoView {

	private Presenter presenter;
	private InfoMedicamentoViewImp infoMedicamentoView;
	
	/* */
	private TextBox stockMinimo;
	private TextBox stockMaximo;
	private Button añadir;
	
	public MedicamentosAñadirInfoViewImp() {
		
		VerticalPanel vPanel = new VerticalPanel();
		infoMedicamentoView = new InfoMedicamentoViewImp();
		vPanel.add(infoMedicamentoView);

		vPanel.add(new HTML("<b>Stock minimo</b>"));
		stockMinimo = new TextBox();
		vPanel.add(stockMinimo);
		
		vPanel.add(new HTML("<b>Stock maximo</b>"));
		stockMaximo = new TextBox();
		vPanel.add(stockMaximo);
		
		añadir = new Button("Añadir");
		vPanel.add(añadir);
		
		initWidget(vPanel);
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public InfoMedicamentoViewImp getInfoMedicamentoViewImp() {
		return this.infoMedicamentoView;
	}

	@Override
	public HasClickHandlers getAddButton() {
		return this.añadir;
	}

	@Override
	public List<String> getInputData() {
		List<String> inputData = new ArrayList<String>();
		inputData.add(this.infoMedicamentoView.getLabelCodigoNacional().getText());
		inputData.add(this.stockMinimo.getText());
		inputData.add(this.stockMaximo.getText());
		
		return inputData;
	}
}
