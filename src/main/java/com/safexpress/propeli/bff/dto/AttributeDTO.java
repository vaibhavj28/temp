package com.safexpress.propeli.bff.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class AttributeDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String attributeName;
	private String attributeType;
	private long Id;
	private Integer status;
	private String  createdBy;
	private String updatedBY;
	@JsonInclude(Include.NON_NULL)
	private String description;
	@JsonInclude(Include.NON_NULL)
	private long permissionId;
	@JsonInclude(Include.NON_NULL)
	private String permissionExclusion;
	private long objectId;	
	private long objectattribute_id;
	private long obj_role_perm_map_id;
	@JsonInclude(Include.NON_NULL)
	private String isAddorUpdate;
	@JsonInclude(Include.NON_NULL)
	private Timestamp effectiveDate;
	@JsonInclude(Include.NON_NULL)
	private Timestamp expiryDate;
	private String addOrRemoveOrUpdate;
	
	
	
	
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBY() {
		return updatedBY;
	}
	public void setUpdatedBY(String updatedBY) {
		this.updatedBY = updatedBY;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(long permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionExclusion() {
		return permissionExclusion;
	}
	public void setPermissionExclusion(String permissionExclusion) {
		this.permissionExclusion = permissionExclusion;
	}
	/**
	 * @return the objectId
	 */
	public long getObjectId() {
		return objectId;
	}
	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	/**
	 * @return the objectattribute_id
	 */
	public long getObjectattribute_id() {
		return objectattribute_id;
	}
	/**
	 * @param objectattribute_id the objectattribute_id to set
	 */
	public void setObjectattribute_id(long objectattribute_id) {
		this.objectattribute_id = objectattribute_id;
	}
	/**
	 * @return the obj_role_perm_map_id
	 */
	public long getObj_role_perm_map_id() {
		return obj_role_perm_map_id;
	}
	/**
	 * @param obj_role_perm_map_id the obj_role_perm_map_id to set
	 */
	public void setObj_role_perm_map_id(long obj_role_perm_map_id) {
		this.obj_role_perm_map_id = obj_role_perm_map_id;
	}
	public String getIsAddorUpdate() {
		return isAddorUpdate;
	}
	public void setIsAddorUpdate(String isAddorUpdate) {
		this.isAddorUpdate = isAddorUpdate;
	}
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
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
	public String getAddOrRemoveOrUpdate() {
		return addOrRemoveOrUpdate;
	}
	public void setAddOrRemoveOrUpdate(String addOrRemoveOrUpdate) {
		this.addOrRemoveOrUpdate = addOrRemoveOrUpdate;
	}
	
	
	

}
