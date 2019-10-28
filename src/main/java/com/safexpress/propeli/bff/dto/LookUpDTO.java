package com.safexpress.propeli.bff.dto;

import java.io.Serializable;

public class LookUpDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long id;
	private String lookupVal;
	private int lookupTypeId;
	private String status;
	
	public LookUpDTO(){}
	/**
	 * @param id
	 * @param lookUpValue
	 */
	public LookUpDTO(long id, String lookUpValue) {
		this.id = id;
		this.lookupVal = lookUpValue;
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
	public String getLookupVal() {
		return lookupVal;
	}
	/**
	 * @param lookupVal the lookUpValue to set
	 */
	public void setLookupVal(String lookupVal) {
		this.lookupVal = lookupVal;
	}

	public int getLookupTypeId() {
		return lookupTypeId;
	}

	public void setLookupTypeId(int lookupTypeId) {
		this.lookupTypeId = lookupTypeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
