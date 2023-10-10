package com.farmacloud.shared.model.abstracts;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class SesionUsuarioAbstracta  implements Serializable
{
	private static final long serialVersionUID = -4665396839254811268L;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
	
	@Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
	private String parentEncodedKey;
	
	private Date fechaCreacion;
    private String idSession;
    
    public SesionUsuarioAbstracta(){
    }

    /*		G E T T E R S		&		S E T T E R S		*/
    
	public String getEncodedKey() {
		return encodedKey;
	}
	
	public String getIdSession() {
		return idSession;
	}
	
	public String getParentEncodedKey() {
		return parentEncodedKey;
	} 
	
	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}
