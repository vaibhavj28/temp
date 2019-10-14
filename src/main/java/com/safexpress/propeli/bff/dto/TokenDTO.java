package com.safexpress.propeli.bff.dto;

import java.io.Serializable;

/**
 * @author Arun Singh
 * @ClassType DTO	
 * @ClassDescription DTO for jwt token
 */
public class TokenDTO implements Serializable {

	/**
	 * 
	 */
	public TokenDTO(){}
	
	private static final long serialVersionUID = 5324794093142868374L;
	private String accessToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public TokenDTO(String accessToken) {
		this.accessToken = accessToken;
	}


}
