package com.farmacloud.client.services;

import com.farmacloud.shared.model.DTO.UserRoleDTO;
import com.farmacloud.shared.model.abstracts.UsuarioAbstracto;
import com.farmacloud.shared.validation.Credenciales;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServicioUsuarioAsync {

	void crearUsuario(UsuarioAbstracto usuario, AsyncCallback<Void> callback);

	void login(Credenciales creds, boolean remember,
			AsyncCallback<UserRoleDTO> callback);
	void logout(AsyncCallback<Void> callback);
	void isLoggedIn(AsyncCallback<UserRoleDTO> callback);
	void isActive(AsyncCallback<UserRoleDTO> callback);

	void isRemembered(AsyncCallback<UserRoleDTO> callback);

	void dumbRequest(AsyncCallback<Void> callback);
}
