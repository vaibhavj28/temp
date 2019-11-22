package com.safexpress.propeli.bff.dto;

import javax.validation.constraints.NotNull;

public class PincodeDTO {

	private Integer id;	
	@NotNull
	private String pincode;
	@NotNull
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
