package com.safexpress.propeli.bff.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class RoleDTO implements Serializable {
	private static final long serialVersionUID = 3170771691990515776L;
	
	@ApiModelProperty(value="Role Table Key", notes="Required in case of role update or search")
	@JsonInclude(Include.NON_NULL)
	private long roleId;	
	@ApiModelProperty(value = "Role Name", notes = "Name must be in characters")
	@NotEmpty(message = "Role Name cannot be null or empty")
	@Size(max = 50, message = "Role Name must be below 50 characters long") 
	private String roleName;
	@ApiModelProperty(value="Role Description")
	@JsonInclude(Include.NON_NULL)
	@Size(max = 100, message = "Description must be below 100 characters long") 
	private String description;
	@ApiModelProperty(value="Role Status", notes = "1 means active and 0 means not active")
	@NotNull(message = "Status cannot be null")
	private int status;
	@JsonInclude(Include.NON_NULL)
	private String createdBy;
	@JsonInclude(Include.NON_NULL)
	private String updatedBy;
	@ApiModelProperty(value="Flag to specify if the branch is to be added or removed ", notes = "Specify 'add' for addition and 'remove' for removal")
	@JsonInclude(Include.NON_NULL)	
	String addOrRemoveOrUpdate;
	@JsonInclude(Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date effectiveDate;
	@JsonInclude(Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date expiryDate;
	
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getAddOrRemoveOrUpdate() {
		return addOrRemoveOrUpdate;
	}

	public void setAddOrRemoveOrUpdate(String addOrRemoveOrUpdate) {
		this.addOrRemoveOrUpdate = addOrRemoveOrUpdate;
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

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
}