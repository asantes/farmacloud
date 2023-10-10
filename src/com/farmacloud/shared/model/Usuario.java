package com.farmacloud.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import com.farmacloud.shared.model.abstracts.UsuarioAbstracto;

@PersistenceCapable(detachable = "true")
public class Usuario extends UsuarioAbstracto implements Serializable{

	private static final long serialVersionUID = 3215345856079074512L;
	
	public Usuario(){
	}
}

