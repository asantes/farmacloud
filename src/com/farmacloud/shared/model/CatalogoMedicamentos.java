package com.farmacloud.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;

@PersistenceCapable(detachable = "true")
public class CatalogoMedicamentos implements Serializable
{
	private static final long serialVersionUID = -1923585027409229475L;
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;

    private String parentNegocioKey;
    private Date fecha;
    private int numMedicamentos;
    
    @NotPersistent
    private List<MedicamentoAbstracto> listaMedicamentos = new ArrayList<MedicamentoAbstracto>();
    
    public CatalogoMedicamentos(){
    }

    /* G E T T E R S		&		S E T T E R S		*/

	public Date getFecha() {
		return fecha;
	}

	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getParentNegocioKey() {
		return parentNegocioKey;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getNumMedicamentos() {
		return numMedicamentos;
	}

	public void setNumMedicamentos(int numMedicamentos) {
		this.numMedicamentos = numMedicamentos;
	}

	public String getParentKey() {
		return parentNegocioKey;
	}

	public void setParentNegocioKey(String parentNegocioKey) {
		this.parentNegocioKey = parentNegocioKey;
	}
	
	/* */
	public List<MedicamentoAbstracto> getListaMedicamentos() {
		return listaMedicamentos;
	}

	public void setListaMedicamentos(List<MedicamentoAbstracto> listaMedicamentos) {
		this.listaMedicamentos = listaMedicamentos;
	}
}
