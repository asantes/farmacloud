package com.farmacloud.shared.model;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import com.farmacloud.shared.model.abstracts.VentaAbstracta;

@PersistenceCapable(detachable="true")
public class Venta extends VentaAbstracta implements Serializable 
{
	private static final long serialVersionUID = 6301765574587154622L;
	
	List<LineaVenta> listaLineaVenta;

	public Venta(){
	}
	
	/* G E T T E R S		&		S E T T E R S		*/

	public List<LineaVenta> getListaLineaVenta() {
		return listaLineaVenta;
	}

	public void setListaLineaVenta(List<LineaVenta> listaLineaVenta) {
		this.listaLineaVenta = listaLineaVenta;
	}
}
