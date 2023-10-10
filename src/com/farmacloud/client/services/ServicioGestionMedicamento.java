package com.farmacloud.client.services;

import java.util.List;

import com.farmacloud.shared.model.MedicamentoFarmacia;
import com.farmacloud.shared.model.DTO.ProgressUploadDTO;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.farmacloud.shared.model.infoView.GetTableWithCursor;
import com.farmacloud.shared.model.infoView.MedicamentoSuggestion;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;
import com.google.gwt.user.client.ui.SuggestOracle.Request;

@RemoteServiceRelativePath("gestionmedicamento")
public interface ServicioGestionMedicamento extends RemoteService
{
	void crearCatalogoMedicamentosFinanciados(String catalogo);
	List<MedicamentoAbstracto> getCatalogoMedicamentos();
	MedicamentoAbstracto getMedicamento(String codigo);
	MedicamentoFarmacia crearMedicamentoFarmacia(List<String> data);
	List<MultiWordSuggestion> getSuggestions(Request request);
	GetTableWithCursor getPorcionCatalogo(int start);
	int getPositionInCatalogue(String nombre);
	
	ProgressUploadDTO progressUpload();
	public int test();
}
