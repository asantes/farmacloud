package com.farmacloud.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;

import com.farmacloud.shared.model.abstracts.UsuarioAbstracto;

@PersistenceCapable
public class UsuarioPharma extends UsuarioAbstracto implements Serializable
{
	private static final long serialVersionUID = -7318961179600575373L;

	private String NIF;
	private String telefono;
	private String direccion;
	
	public UsuarioPharma(){
	}

	/* G E T T E R S		&		S E T T E R S		*/
	
	public String getNIF() {
		return NIF;
	}

	public void setNIF(String nIF) {
		NIF = nIF;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
