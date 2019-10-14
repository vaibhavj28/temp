package com.safexpress.propeli.bff.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class EntityDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonInclude(Include.NON_EMPTY)
	private Integer entityId;
	private Integer parentModuleId;
	@NotNull(message = "entityName cannot be null")
	private String entityName;
	@JsonInclude(Include.NON_EMPTY)
	private String description;
	private Integer status;
	private String createdBy;
	private String updatedBy;
	@JsonInclude(Include.NON_EMPTY)
	private String iSAddOrRemoveOrUpdate;
	@JsonInclude(Include.NON_EMPTY)
	private Timestamp effectiveDate;
	@JsonInclude(Include.NON_EMPTY)
	private Timestamp expiryDate;
	
	
	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Integer getParentModuleId() {
		return parentModuleId;
	}

	public void setParentModuleId(Integer parentModuleId) {
		this.parentModuleId = parentModuleId;
	}

	

	public String getiSAddOrRemoveOrUpdate() {
		return iSAddOrRemoveOrUpdate;
	}

	public void setiSAddOrRemoveOrUpdate(String iSAddOrRemoveOrUpdate) {
		this.iSAddOrRemoveOrUpdate = iSAddOrRemoveOrUpdate;
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

	@Override
	public String toString() {
		return "EntityDTO [entityId=" + entityId + ", parentModuleId=" + parentModuleId + ", entityName=" + entityName
				+ ", description=" + description + ", status=" + status + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + ", iSAddOrRemoveOrUpdate=" + iSAddOrRemoveOrUpdate + ", effectiveDate=" + effectiveDate
				+ ", expiryDate=" + expiryDate + "]";
	}
	
	

}
