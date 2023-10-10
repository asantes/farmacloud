package com.farmacloud.client.services;

import javax.validation.ConstraintViolationException;

import com.farmacloud.shared.model.DTO.UserRoleDTO;
import com.farmacloud.shared.model.abstracts.UsuarioAbstracto;
import com.farmacloud.shared.validation.Credenciales;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.XsrfProtectedService;
import com.google.gwt.user.server.rpc.NoXsrfProtect;

@RemoteServiceRelativePath("gestionusuario")
public interface ServicioUsuario extends XsrfProtectedService
{
	@NoXsrfProtect
	public void crearUsuario(UsuarioAbstracto usuario);
	@NoXsrfProtect
	public UserRoleDTO login(Credenciales creds, boolean remember) throws ConstraintViolationException;
	@NoXsrfProtect
	void logout();
	@NoXsrfProtect
	public UserRoleDTO isLoggedIn();
	@NoXsrfProtect
	public UserRoleDTO isRemembered();
	@NoXsrfProtect
	public UserRoleDTO isActive();

	
	@NoXsrfProtect
	public void dumbRequest();
}
