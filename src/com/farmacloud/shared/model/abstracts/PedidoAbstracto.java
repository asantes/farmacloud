package com.farmacloud.shared.model.abstracts;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class PedidoAbstracto implements Serializable
{
	private static final long serialVersionUID = -1675940133546445202L;
	
	@PrimaryKey
	private long idInterno;
	
	private String proveedorKey;
	private int idExterno;
	private Date fecha;
	private float importe;
	private String estado;
	
	public PedidoAbstracto(){
	}

	/* G E T T E R S		&		S E T T E R S		*/
	
	public long getIdInterno() {
		return idInterno;
	}

	public void setIdInterno(long idInterno) {
		this.idInterno = idInterno;
	}

	public String getProveedorKey() {
		return proveedorKey;
	}

	public void setProveedorKey(String proveedorKey) {
		this.proveedorKey = proveedorKey;
	}

	public int getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(int idExterno) {
		this.idExterno = idExterno;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
		
	

}
