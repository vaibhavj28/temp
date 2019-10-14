package com.safexpress.propeli.bff.dto;

import java.io.Serializable;

public class LookUpDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long id;
	private String lookUpValue;
	
	public LookUpDTO(){}
	/**
	 * @param id
	 * @param lookUpValue
	 */
	public LookUpDTO(long id, String lookUpValue) {
		this.id = id;
		this.lookUpValue = lookUpValue;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the lookUpValue
	 */
	public String getLookUpValue() {
		return lookUpValue;
	}
	/**
	 * @param lookUpValue the lookUpValue to set
	 */
	public void setLookUpValue(String lookUpValue) {
		this.lookUpValue = lookUpValue;
	}
	

}
