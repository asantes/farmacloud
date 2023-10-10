package com.farmacloud.shared.model.DTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserRoleDTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247123732025979680L;
	String principal;
	Set<String> roles = new HashSet<String>();
	
	public UserRoleDTO(){
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRole(HashSet<String> roles) {
		this.roles = roles;
	}
	
	public String rolesToString(){
		String rolesAsString = "";
		for(String s : this.roles){
			rolesAsString.concat(s);
		}
		return rolesAsString;
	}
}
