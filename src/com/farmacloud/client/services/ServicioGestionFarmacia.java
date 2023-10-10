package com.farmacloud.client.services;

import java.util.List;

import com.farmacloud.shared.model.DTO.NoticiaDTO;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.XsrfProtectedService;

@RemoteServiceRelativePath("gestionfarmacia")
public interface ServicioGestionFarmacia extends XsrfProtectedService
{
	boolean añadirNoticia(byte[] cuerpoNoticia, String titularNoticia);
	List<NoticiaDTO> obtenerNoticias(int numNoticias, int numPag);
}
