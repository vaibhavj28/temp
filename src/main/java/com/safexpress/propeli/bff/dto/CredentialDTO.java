package com.safexpress.propeli.bff.dto;

import java.io.Serializable;

/**
 * @author Arun Singh
 * @Email arusin06@in.ibm.com	
 *
 */
public class CredentialDTO implements Serializable {
	private static final long serialVersionUID = -2908910470966290666L;
	private String username;
	private String password;
	private boolean rememberMe = false;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public String toString() {
		return "username = " + getUsername() + "\nrememberMe = " + isRememberMe();
	}

}
