package com.farmacloud.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class LineaVenta implements Serializable
{	
	private static final long serialVersionUID = 4769976632898945520L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
    
	@Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
	private String parentEncodedKey;
	@Persistent(mappedBy = "listaLineaVenta")
	private Venta venta;
	
	private List<String> listaUnidadMedicamentoKeys = new ArrayList<String>();
	private double precioFinal;
	
	public LineaVenta(){
	}

	/* G E T T E R S		&		S E T T E R S		*/
	
	public String getEncodedKey() {
		return encodedKey;
	}

	public String getParentEncodedKey() {
		return parentEncodedKey;
	}

	public Venta getVenta() {
		return venta;
	}
	
	public List<String> getListaUnidadMedicamentoKeys() {
		return listaUnidadMedicamentoKeys;
	}

	public void setListaUnidadMedicamentoKeys(
			List<String> listaUnidadMedicamentoKeys) {
		this.listaUnidadMedicamentoKeys = listaUnidadMedicamentoKeys;
	}
	

	public double getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}
}
