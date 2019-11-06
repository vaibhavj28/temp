package com.safexpress.propeli.bff.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.safexpress.propeli.bff.dto.RoleDTO;
import com.safexpress.propeli.bff.dto.UserBranchMappingDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "User data model")
@Validated
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 3170771691995776L;

	@ApiModelProperty(value="User Table Key", notes="Required in case of user update")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long id;
	@ApiModelProperty(value = "User Id which is unique")
	@NotEmpty(message = "User Id cannot be null or empty")
	@Size(max = 50, message = "UserId must be below 50 characters long")
	private String userId;
	@ApiModelProperty(value = "User Name", notes = "Name must be in characters")
	@NotEmpty(message = "Name cannot be null or empty")
	@Size(max = 50, message = "Name must be below 50 characters long")
	private String name;
	@ApiModelProperty(value = "User Email", notes = "Email must adher to standard email format")
	@NotEmpty(message = "Email cannot be null or empty")
	@Size(max = 50, message = "Email must be below 50 characters long")
	private String email;
	@ApiModelProperty(value= "User Mobile no.", notes = "Mobile Number must only be digits")
	@Size(max = 21, message = "Mobile No. must be below 21 characters long")
	private String mobile;
	@ApiModelProperty(value= "User Category Id", required = true, notes = "Category Id must only be integer")
	@NotNull(message = "Category Id cannot be null")
	private int categoryId;
	@ApiModelProperty(value="User Status", notes = "1 means active and 0 means not active")
	@NotNull(message = "Status cannot be null")
	private int status;
	@ApiModelProperty(value="User Description")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(max = 100, message = "Description must be below 100 characters long")
	private String description;
	@ApiModelProperty(value="User admin property", notes = "1 means user is super admin and 0 means user is not")
	private int isAdmin;
	@ApiModelProperty(value="User Menu Hierachy id", notes="Menu Hierachy id must only be in digits")
	@NotNull(message = "Menu Hierarchy cannot be null")
	private int menuHierarchyId;
	@ApiModelProperty(value="User default branch", notes = "User default branch cannot be empty or null")
	@NotNull(message = "Default Branch cannot be null")
	private UserBranchMappingDTO defaultBranch;
	@ApiModelProperty(value="User Previlege Branches")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<UserBranchMappingDTO> previlegeBranches;
	@ApiModelProperty(value="User Roles", notes = "User Role List must not be Empty or Null")
	@NotEmpty(message = "User Roles cannot be null or empty")
	private List<RoleDTO> userRoles;

	public UserDTO() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public List<RoleDTO> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<RoleDTO> userRoles) {
		this.userRoles = userRoles;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getMenuHierarchyId() {
		return menuHierarchyId;
	}

	public void setMenuHierarchyId(int menuHierarchyId) {
		this.menuHierarchyId = menuHierarchyId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserBranchMappingDTO getDefaultBranch() {
		return defaultBranch;
	}

	public void setDefaultBranch(UserBranchMappingDTO defaultBranch) {
		this.defaultBranch = defaultBranch;
	}

	public List<UserBranchMappingDTO> getPrevilegeBranches() {
		return previlegeBranches;
	}

	public void setPrevilegeBranches(List<UserBranchMappingDTO> previlegeBranches) {
		this.previlegeBranches = previlegeBranches;
	}


}
