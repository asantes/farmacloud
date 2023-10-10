package com.farmacloud.shared.model.abstracts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import com.farmacloud.shared.model.SesionUsuarioRecordar;
import com.farmacloud.shared.model.SesionUsuarioTemporal;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class UsuarioAbstracto implements Serializable 
{
	private static final long serialVersionUID = 8950336179115989242L;
	
	@PrimaryKey
	private String nameUser;
	private String password;
	private String salt;
	private String nombre;
	private String apellido;
	private String mail;
	private Set<String> roles;
	private SesionUsuarioTemporal sesionTemporal;
	private List<SesionUsuarioRecordar> sesionRecordar = new ArrayList<SesionUsuarioRecordar>();
	
	@NotPersistent
	private boolean sesionValida;
	private String uuid;

	public UsuarioAbstracto(){
	}
	
	/* G E T T E R S		&		S E T T E R S		*/

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public SesionUsuarioTemporal getSesionTemporal() {
		return sesionTemporal;
	}

	public void setSesionTemporal(SesionUsuarioTemporal sesionTemporal) {
		this.sesionTemporal = sesionTemporal;
	}

	public List<SesionUsuarioRecordar> getSesionRecordar() {
		return sesionRecordar;
	}

	public void setSesionRecordar(List<SesionUsuarioRecordar> sesionRecordar) {
		this.sesionRecordar = sesionRecordar;
	}
	
	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	/* */
	public boolean isSesionValida() {
		return sesionValida;
	}

	public void setSesionValida(boolean sesionValida) {
		this.sesionValida = sesionValida;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
