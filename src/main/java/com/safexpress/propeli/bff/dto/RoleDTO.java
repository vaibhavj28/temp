package com.safexpress.propeli.bff.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RoleDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3170771691990515776L;
	
	@JsonInclude(Include.NON_NULL)
	private long roleId;	
	@NotNull
	private String roleName;
	private String description;
	private int status;
	private String createdBy;
	private String updatedBy;
	@JsonInclude(Include.NON_NULL)
	String addOrRemoveOrUpdate;
	private Timestamp effectiveDate;
	private Timestamp expiryDate;
	
	
	// int channeId;

	public String getCreatedBy() {
		return createdBy;
	}

	public String getAddOrRemove() {
		return addOrRemoveOrUpdate;
	}

	public void setAddOrRemove(String addOrRemove) {
		this.addOrRemoveOrUpdate = addOrRemove;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	

}
