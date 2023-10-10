package com.farmacloud.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;

import com.farmacloud.shared.model.abstracts.SesionUsuarioAbstracta;

@PersistenceCapable(detachable = "true")
public class SesionUsuarioTemporal extends SesionUsuarioAbstracta implements Serializable{

	private static final long serialVersionUID = -6942745024997191579L;

	public SesionUsuarioTemporal(){
	}
}
