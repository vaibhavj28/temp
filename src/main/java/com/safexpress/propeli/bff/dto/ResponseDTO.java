package com.safexpress.propeli.bff.dto;

import org.springframework.stereotype.Component;

/**
 * 
 *@ClassType DTO/Model
 *@Description Response DTO for webservice endpoint response
 */
@Component
public class ResponseDTO {
	
	String message;
	Object data;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	

}
