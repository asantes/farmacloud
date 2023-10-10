package com.farmacloud.shared.validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsuarioForm {
	
	@Size(min=3, max=20)
	@NotNull
	private String usuario;
	
	@Size(min=6, max=20)
	@NotNull
	private String contraseña;
	
	@NotNull
	private String email;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
