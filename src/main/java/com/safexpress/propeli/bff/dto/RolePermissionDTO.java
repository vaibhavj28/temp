package com.safexpress.propeli.bff.dto;

import java.sql.Timestamp;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.safexpress.propeli.bff.dto.RoleDTO;

public class RolePermissionDTO {
	@JsonInclude(Include.NON_NULL)
	private RoleDTO roleDto;
	private String status;
	private String description;
	private String createdBy;
	private String updatedBy;
	private Timestamp effectiveDate;
	private Timestamp expiryDate;
	private long Id;
	private String isAddOrRemoveOrUpdate;
	@JsonInclude(Include.NON_NULL)
	List<ModuleObjectDTO> objectPermissionList;
	
	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the objectPermissionList
	 */
	public List<ModuleObjectDTO> getObjectPermissionList() {
		return objectPermissionList;
	}
	/**
	 * @param objectPermissionList the objectPermissionList to set
	 */
	public void setObjectPermissionList(List<ModuleObjectDTO> objectPermissionList) {
		this.objectPermissionList = objectPermissionList;
	}
	
	
	public RoleDTO getRoleDto() {
		return roleDto;
	}
	public void setRoleDto(RoleDTO roleDto) {
		this.roleDto = roleDto;
	}
	public List<ModuleObjectDTO> objectPermissionList() {
		return objectPermissionList;
	}
	public void setOnjectPermissionList(List<ModuleObjectDTO> objectPermissionList) {
		this.objectPermissionList = objectPermissionList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the effectiveDate
	 */
	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	/**
	 * @return the expiryDate
	 */
	public Timestamp getExpiryDate() {
		return expiryDate;
	}
	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getIsAddOrRemoveOrUpdate() {
		return isAddOrRemoveOrUpdate;
	}
	public void setIsAddOrRemoveOrUpdate(String isAddOrRemoveOrUpdate) {
		this.isAddOrRemoveOrUpdate = isAddOrRemoveOrUpdate;
	}
	

}
