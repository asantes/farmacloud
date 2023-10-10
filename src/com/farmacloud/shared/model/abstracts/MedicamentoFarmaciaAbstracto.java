package com.farmacloud.shared.model.abstracts;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class MedicamentoFarmaciaAbstracto implements Serializable
{
	private static final long serialVersionUID = -8671333033069941066L;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String codigoNacional;
	private Long parentKey;
	private String comentario;
	private double media;
	private int parametro; 
	private int stockMinimo;
	private int stockMaximo;
	private int ide;
	private int contadorVentas;
	private int numDisponibles;
	private float porcentajeDescuento;
	
	public MedicamentoFarmaciaAbstracto(){
	}

	public Long getParentKey() {
		return parentKey;
	}

	public void setParentKey(Long parentKey) {
		this.parentKey = parentKey;
	}

	public String getCodigoNacional() {
		return codigoNacional;
	}

	public void setCodigoNacional(String codigoNacional) {
		this.codigoNacional = codigoNacional;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public int getParametro() {
		return parametro;
	}

	public void setParametro(int parametro) {
		this.parametro = parametro;
	}

	public int getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public int getStockMaximo() {
		return stockMaximo;
	}

	public void setStockMaximo(int stockMaximo) {
		this.stockMaximo = stockMaximo;
	}

	public int getIde() {
		return ide;
	}

	public void setIde(int ide) {
		this.ide = ide;
	}

	public int getContadorVentas() {
		return contadorVentas;
	}

	public void setContadorVentas(int contadorVentas) {
		this.contadorVentas = contadorVentas;
	}

	public int getNumDisponibles() {
		return numDisponibles;
	}

	public void setNumDisponibles(int numDisponibles) {
		this.numDisponibles = numDisponibles;
	}

	public float getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(float porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}
}