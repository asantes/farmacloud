package com.farmacloud.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;

import com.farmacloud.shared.model.abstracts.Negocio;

@PersistenceCapable
public class Farmacia extends Negocio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2031266044337583361L;

	public Farmacia(){
	}
}
