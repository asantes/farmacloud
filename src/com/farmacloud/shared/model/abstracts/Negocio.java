package com.farmacloud.shared.model.abstracts;

import java.io.Serializable;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import org.hibernate.validator.constraints.NotBlank;

import com.farmacloud.shared.model.CatalogoMedicamentos;
import com.farmacloud.shared.validation.ExtendedEmailValidator;
import com.farmacloud.shared.validation.PhoneValidator;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class Negocio implements Serializable
{
	private static final long serialVersionUID = 2252927832304063353L;
	
	@PrimaryKey
	@NotBlank
    private String NIF;
	@NotBlank
    private String nombre;
	@NotBlank
    private String direccion;
	@PhoneValidator
    private String telefono;
	@ExtendedEmailValidator
    private String email;
    private CatalogoMedicamentos catalogo;
    
	public String getNIF() {
		return NIF;
	}
	
	public void setNIF(String nIF) {
		NIF = nIF;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public CatalogoMedicamentos getCatalogo() {
		return catalogo;
	}
	
	public void setCatalogo(CatalogoMedicamentos catalogo) {
		this.catalogo = catalogo;
	}
}
