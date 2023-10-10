package com.farmacloud.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class LineaPedido implements Serializable
{
	private static final long serialVersionUID = -8695684505426521678L;
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
    
	@Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
	private String parentEncodedKey;
	
	@Persistent(mappedBy = "listaLineaPedido")
	private Pedido pedido;
	
	private int unidadesPedidas;
	private int unidadesRecibidas;
	private List<UnidadMedicamento> listaUnidadMedicamento = new ArrayList<UnidadMedicamento>();
	
	@NotPersistent
	private String codigoNacional;
	
	public LineaPedido(){
	}

	/* G E T T E R S		&		S E T T E R S		*/
	
	public String getEncodedKey() {
		return encodedKey;
	}
	
	public String getParentEncodedKey() {
		return parentEncodedKey;
	}

	public int getUnidadesPedidas() {
		return unidadesPedidas;
	}

	public void setUnidadesPedidas(int unidadesPedidas) {
		this.unidadesPedidas = unidadesPedidas;
	}

	public int getUnidadesRecibidas() {
		return unidadesRecibidas;
	}

	public void setUnidadesRecibidas(int unidadesRecibidas) {
		this.unidadesRecibidas = unidadesRecibidas;
	}

	public List<UnidadMedicamento> getListaUnidadMedicamento() {
		return listaUnidadMedicamento;
	}

	public void setListaUnidadMedicamento(
			List<UnidadMedicamento> listaUnidadMedicamento) {
		this.listaUnidadMedicamento = listaUnidadMedicamento;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	/* */
	public String getCodigoNacional() {
		return codigoNacional;
	}

	public void setCodigoNacional(String codigoNacional) {
		this.codigoNacional = codigoNacional;
	}

}
