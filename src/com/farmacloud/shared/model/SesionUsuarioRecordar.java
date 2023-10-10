package com.farmacloud.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;

import com.farmacloud.shared.model.abstracts.SesionUsuarioAbstracta;

@PersistenceCapable
public class SesionUsuarioRecordar extends SesionUsuarioAbstracta implements Serializable{

	private static final long serialVersionUID = -812535594432209725L;
	
	public SesionUsuarioRecordar(){
	}
}
