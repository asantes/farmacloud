package com.farmacloud.shared.model.DTO;

import java.io.Serializable;
import java.util.Date;

public class NoticiaDTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7582091238381987498L;
	private byte[] cuerpoBytes;
	private String titular;
	private Date fecha;
	private String keyUsuario;
	private int contador;
	
	public NoticiaDTO(){
	}

	public byte[] getCuerpoBytes() {
		return cuerpoBytes;
	}

	public void setCuerpoBytes(byte[] cuerpoBytes) {
		this.cuerpoBytes = cuerpoBytes;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getKeyUsuario() {
		return keyUsuario;
	}

	public void setKeyUsuario(String keyUsuario) {
		this.keyUsuario = keyUsuario;
	}

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}
}
