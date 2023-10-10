package com.farmacloud.client.services;

import java.util.List;

import com.farmacloud.shared.model.InfoEstado;
import com.farmacloud.shared.model.Noticia;
import com.farmacloud.shared.model.DTO.NoticiaDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServicioGestionFarmaciaAsync {

	void añadirNoticia(byte[] cuerpoNoticia, String titularNoticia,
			AsyncCallback<Boolean> callback);

	void obtenerNoticias(int numNoticias, int numPag,
			AsyncCallback<List<NoticiaDTO>> callback);

}
