package com.farmacloud.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;

import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;

@PersistenceCapable(detachable = "true")
@Inheritance(customStrategy = "complete-table")
public class MedicamentoProveedor extends MedicamentoAbstracto implements Serializable
{
	private static final long serialVersionUID = -3358943516981364239L;
	
	public MedicamentoProveedor(){
	}
}