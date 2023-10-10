package com.farmacloud.client.ui.widgetsold;

import java.util.List;

import com.farmacloud.client.gui.pharmaUsers.RecepcionView.Presenter;
import com.farmacloud.shared.model.infoView.MedicamentoInfo;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

public class InfoMedicamentoViewImp extends Composite implements InfoMedicamentoView{
	
	private Presenter presenter;
	private VerticalPanel vPanel;
	
	/* TextBox donde se introduce el codigo nacional del medicamento a buscar */
	private TextBox cajaEscaner;
	
	/* Labels que muestran la informacion del medicamento escaneado */
	private Label labelNombreMedicamento;
	private Label labelLaboratorioMedicamento;
	private Label labelPrecioPVPMedicamento;
	private Label labelPrincipioActivoMedicamento;
	private Label labelCodigoNacional;
	
	/* Label que muestra mensajes de aviso con info adicional puntual*/
	private HTML mensaje;

	
	public InfoMedicamentoViewImp()
	{
		vPanel = new VerticalPanel();
		
		/* Informacion fija */
		vPanel.add(new HTML("<b>Codigo nacional</b>", true));
		cajaEscaner = new TextBox();
		vPanel.add(cajaEscaner);
		
	    vPanel.add(new HTML("<b>Nombre del producto</b>", true));
		labelNombreMedicamento = new Label();
		vPanel.add(labelNombreMedicamento);
		
		vPanel.add(new HTML("<b>Laboratorio</b>", true));
		labelLaboratorioMedicamento = new Label();
		vPanel.add(labelLaboratorioMedicamento);
		
		vPanel.add(new HTML("<b>Principio Activo</b>", true));
		labelPrincipioActivoMedicamento = new Label();
		vPanel.add(labelPrincipioActivoMedicamento);
		
		vPanel.add(new HTML("<b>PVP</b>", true));
		labelPrecioPVPMedicamento = new Label();
		vPanel.add(labelPrecioPVPMedicamento);
		
		vPanel.add(new HTML("<b>Codigo nacional</b>", true));
		labelCodigoNacional = new Label();
		vPanel.add(labelCodigoNacional);
		
		/* Informacion puntual */
	    mensaje = new HTML();
	    mensaje.setVisible(false);
	    vPanel.add(mensaje);	
		
		initWidget(vPanel);
		//setStylePrimaryName("infoMedicamento");
		
		cajaEscaner.addKeyUpHandler(new ManejadoraCajaEscaner());
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
						presenter.buscarMedicamento(cadenaVerificada);
					}
				}
			}
		}
	}

	@Override
	public void setData(List<String> data) {
		System.out.println((data.get(1)));
		this.labelCodigoNacional.setText(data.get(0));
		this.labelNombreMedicamento.setText(data.get(1));
		this.labelLaboratorioMedicamento.setText(data.get(2));
		this.labelPrincipioActivoMedicamento.setText(data.get(3));
		this.labelPrecioPVPMedicamento.setText(data.get(4));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setMensaje(String msg, boolean visibilidad) {
		this.mensaje.setText(msg);
		this.mensaje.setVisible(visibilidad);
	}

	public Label getLabelCodigoNacional() {
		return labelCodigoNacional;
	}
}
