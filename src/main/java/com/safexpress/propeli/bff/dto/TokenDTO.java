package com.safexpress.propeli.bff.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Singh
 * @ClassType DTO	
 * @ClassDescription DTO for jwt token
 */
public class TokenDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5324794093142868374L;
	
	@JsonProperty("access-token")
	private String accessToken;

	public TokenDTO() {
		
	}
	
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
