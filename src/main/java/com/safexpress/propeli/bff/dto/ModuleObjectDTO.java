package com.safexpress.propeli.bff.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class ModuleObjectDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long moduleId;
	private long objectId;
	private long entityId;
	private String moduleName;
	private String entityName;
	private String subEntityName;
	private String channel;
	private Long id;
	
	
	
	public long getEntityId() {
		return entityId;
	}



	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	private int[] channelList;
	private int isPublic;
	private String permissionType;
	@JsonInclude(Include.NON_NULL)
	private long permissionId;
	@JsonInclude(Include.NON_NULL)
	private String createdBy;
	@JsonInclude(Include.NON_NULL)
	private String updatedBy;
	private String description;
	@JsonInclude(Include.NON_NULL)
	private Integer channelId;
	@JsonInclude(Include.NON_NULL)
	List<AttributeDTO> attributeExclutionList;
	private int status;
	@JsonInclude(Include.NON_NULL)
	private String isAddOrRemoveOrUpdate;
	@JsonInclude(Include.NON_NULL)
	private Timestamp effectiveDate;
	@JsonInclude(Include.NON_NULL)
	private Timestamp expiryDaye;
	
	
	
	


	
	
	public int[] getChannelList() {
		return channelList;
	}



	public void setChannelList(int[] channelList) {
		this.channelList = channelList;
	}



	/**
	 * @return the channelId
	 */
	public Integer getChannelId() {
		return channelId;
	}



	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}



	/**
	 * @return the permissionId
	 */
	
	public long getPermissionId() {
		return permissionId;
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
	 * @param permissionId the permissionId to set
	 */
	public void setPermissionId(long permissionId) {
		this.permissionId = permissionId;
	}


	
	
	
	

	/**
	 * @return the moduleId
	 */
	public long getModuleId() {
		return moduleId;
	}



	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}



	/**
	 * @return the attributeExclutionList
	 */
	public List<AttributeDTO> getAttributeExclutionList() {
		return attributeExclutionList;
	}



	/**
	 * @param attributeExclutionList the attributeExclutionList to set
	 */
	public void setAttributeExclutionList(List<AttributeDTO> attributeExclutionList) {
		this.attributeExclutionList = attributeExclutionList;
	}



	public String getPermissionType() {
		return permissionType;
	}



	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	
	
	
	public int isPublic() {
		return isPublic;
	}



	public void setPublic(int isPublic) {
		this.isPublic = isPublic;
	}



	public String getModuleName() {
		return moduleName;
	}



	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}



	public String getEntityName() {
		return entityName;
	}



	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}



	public String getSubEntityName() {
		return subEntityName;
	}



	public void setSubEntityName(String subEntityName) {
		this.subEntityName = subEntityName;
	}



	public String getChannel() {
		return channel;
	}



	public void setChannel(String channel) {
		this.channel = channel;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	




	public long getObjectId() {
		return objectId;
	}



	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	

	


	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	@Override
	public String toString() {
		return "ModuleObjectDTO [moduleId=" + moduleId + ", objectId=" + objectId + ", moduleName=" + moduleName
				+ ", entityName=" + entityName + ", subEntityName=" + subEntityName + ", channel=" + channel
				+ ", isPublic=" + isPublic + ", permissionType=" + permissionType + ", permissionId=" + permissionId
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", description=" + description
				+ ", channelId=" + channelId + ", attributeExclutionList=" + attributeExclutionList + ", status="
				+ status + "]";
	}



	public String getIsAddOrUpdate() {
		return isAddOrRemoveOrUpdate;
	}



	public void setIsAddOrUpdate(String isAddorUpdate) {
		this.isAddOrRemoveOrUpdate = isAddorUpdate;
	}



	/**
	 * @return the isPublic
	 */
	public int getIsPublic() {
		return isPublic;
	}



	/**
	 * @param isPublic the isPublic to set
	 */
	public void setIsPublic(int isPublic) {
		this.isPublic = isPublic;
	}



	/**
	 * @return the isAddOrRemoveOrUpdate
	 */
	public String getIsAddOrRemoveOrUpdate() {
		return isAddOrRemoveOrUpdate;
	}



	/**
	 * @param isAddOrRemoveOrUpdate the isAddOrRemoveOrUpdate to set
	 */
	public void setIsAddOrRemoveOrUpdate(String isAddOrRemoveOrUpdate) {
		this.isAddOrRemoveOrUpdate = isAddOrRemoveOrUpdate;
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
	 * @return the expiryDaye
	 */
	public Timestamp getExpiryDaye() {
		return expiryDaye;
	}



	/**
	 * @param expiryDaye the expiryDaye to set
	 */
	public void setExpiryDaye(Timestamp expiryDaye) {
		this.expiryDaye = expiryDaye;
	}



	public int[] getChannelArr() {
		return channelList;
	}



	public void setChannelArr(int[] channelArr) {
		this.channelList = channelArr;
	}

    
}
