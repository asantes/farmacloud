package com.farmacloud.shared.model;

import java.io.Serializable;

public class InfoEstado implements Serializable
{
	private boolean exito;
    private String msg;
    private boolean noRegistradoOnoValidado;

	public InfoEstado(){
	}
	
	public void setSesionInvalida(String _msg){
		this.noRegistradoOnoValidado = true;
		this.exito = false;
		this.msg = _msg;
	}

	/*		G E T T E R S		&		S E T T E R S		*/
	
	public boolean isExito() {
		return exito;
	}
	
	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isNoRegistradoOnoValidado() {
		return noRegistradoOnoValidado;
	}

	public void setNoRegistradoOnoValidado(boolean noRegistradoOnoValidado) {
		this.noRegistradoOnoValidado = noRegistradoOnoValidado;
	}
}
