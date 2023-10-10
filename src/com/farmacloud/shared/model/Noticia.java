package com.farmacloud.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.farmacloud.shared.model.DTO.NoticiaDTO;
import com.google.appengine.api.datastore.Blob;

@PersistenceCapable(detachable = "true")
public class Noticia implements Serializable
{
	private static final long serialVersionUID = 6310296528845909467L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String titular;
    
	private Blob cuerpoCrudo;
	private Date fecha;
	private String autor;
	
	public Noticia(){
	}

	public NoticiaDTO toDTO()
	{
		NoticiaDTO noticiaDTO = new NoticiaDTO();
		
		if(cuerpoCrudo!=null && titular!=null && fecha!=null && autor!=null)
		{
			noticiaDTO = new NoticiaDTO();
			noticiaDTO.setCuerpoBytes(this.cuerpoCrudo.getBytes());
			noticiaDTO.setFecha(this.fecha);
			noticiaDTO.setTitular(this.titular);
			noticiaDTO.setKeyUsuario(autor);
		}

		return noticiaDTO;
	}
	
	/*		G E T T E R S		&		S E T T E R S		*/
	
	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}
	
	public Blob getCuerpoCrudo() {
		return cuerpoCrudo;
	}

	public void setCuerpoCrudo(Blob cuerpoCrudo) {
		this.cuerpoCrudo = cuerpoCrudo;
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

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
}
