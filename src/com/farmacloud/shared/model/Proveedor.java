package com.farmacloud.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;

import com.farmacloud.shared.model.abstracts.Negocio;

@PersistenceCapable
public class Proveedor extends Negocio implements Serializable
{
	private static final long serialVersionUID = -8603356091405853617L;

	public Proveedor(){
	}
	
}
