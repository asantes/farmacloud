package com.farmacloud.server.shiro;

import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

public class MySaltedAuthenticationInfo implements SaltedAuthenticationInfo 
{
	private static final long serialVersionUID = -3055963988946120321L;
	private String username;
	private String password;
	private Object salt;
	private String realmName;
	
	public MySaltedAuthenticationInfo(String _username, String string, Object _salt, String _realName) {
		this.username = _username;
		this.password = string;
		this.salt = _salt;
		this.realmName = _realName;
	}
	
	@Override
	public String getCredentials() {
		return this.password;
	}

	@Override
	public PrincipalCollection getPrincipals() {
		PrincipalCollection principalCollection = new SimplePrincipalCollection(this.username, this.realmName); //NEITHER PASSWORD NOR KEYS HERE!!!
		return principalCollection;
	}

	@Override
	public ByteSource getCredentialsSalt() {
		ByteSource decodedSalt = new SimpleByteSource(org.apache.shiro.codec.Base64.decode((String) salt));
		return decodedSalt;
	}
}
