package com.farmacloud.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;

import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;

@PersistenceCapable(detachable = "true")
@Inheritance(customStrategy = "complete-table")
public class Medicamento extends MedicamentoAbstracto implements Serializable
{	
	private static final long serialVersionUID = -1392535098749332207L;
	
	public Medicamento(){	
	}
}
