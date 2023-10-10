package com.farmacloud.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import com.farmacloud.shared.model.abstracts.PedidoAbstracto;

@PersistenceCapable
public class Pedido extends PedidoAbstracto implements Serializable
{
	private static final long serialVersionUID = 365144950198933362L;

	private List<LineaPedido> listaLineaPedido = new ArrayList<LineaPedido>();

	public Pedido(){
	}
	
	/* G E T T E R S		&		S E T T E R S		*/

	public List<LineaPedido> getListaLineaPedido() {
		return listaLineaPedido;
	}

	public void setListaLineaPedido(List<LineaPedido> listaLineaPedido) {
		this.listaLineaPedido = listaLineaPedido;
	}
}
