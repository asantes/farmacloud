package com.farmacloud.shared.validation;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Credenciales implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9148713770096688206L;

	@Size(min=3, max=20)
	@NotNull
	private String usuario;
	
	@Size(min=6, max=20)
	@NotNull
	private String contraseña;

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
}
