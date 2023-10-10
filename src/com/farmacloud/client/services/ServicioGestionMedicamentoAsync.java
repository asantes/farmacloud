package com.farmacloud.client.services;

import java.util.List;

import com.farmacloud.shared.model.MedicamentoFarmacia;
import com.farmacloud.shared.model.DTO.ProgressUploadDTO;
import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;
import com.farmacloud.shared.model.infoView.GetTableWithCursor;
import com.farmacloud.shared.model.infoView.MedicamentoSuggestion;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;
import com.google.gwt.user.client.ui.SuggestOracle.Request;

public interface ServicioGestionMedicamentoAsync
{
	void crearCatalogoMedicamentosFinanciados(String catalogo,
			AsyncCallback<Void> callback);

	void crearMedicamentoFarmacia(List<String> data,
			AsyncCallback<MedicamentoFarmacia> callback);

	void getCatalogoMedicamentos(
			AsyncCallback<List<MedicamentoAbstracto>> callback);

	void getPorcionCatalogo(int start,
			AsyncCallback<GetTableWithCursor> callback);

	void getPositionInCatalogue(String nombre, AsyncCallback<Integer> callback);

	void getSuggestions(Request request,
			AsyncCallback<List<MultiWordSuggestion>> callback);

	void getMedicamento(String codigo,
			AsyncCallback<MedicamentoAbstracto> callback);

	void test(AsyncCallback<Integer> callback);

	void progressUpload(AsyncCallback<ProgressUploadDTO> callback);
}
