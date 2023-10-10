package com.farmacloud.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;

import com.farmacloud.shared.model.abstracts.MedicamentoFarmaciaAbstracto;

@PersistenceCapable(detachable = "true")
public class MedicamentoFarmacia extends MedicamentoFarmaciaAbstracto implements Serializable
{
	private static final long serialVersionUID = 3978720136105781722L;
	
	public MedicamentoFarmacia(){
	}
}
