package com.safexpress.propeli.bff.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "User data model")
@Validated
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 3170771691995776L;
	
	@JsonInclude(Include.NON_NULL)
	private long id;
	@NotNull(message = "UserId cannot be null")
	private String userId;
	@NotNull(message = "Name cannot be null")
	private String name;
	@NotNull(message = "Email cannot be null")
	private String email;
	@NotNull(message = "Mobile cannot be null")
	private String mobile;
	@NotNull(message = "Category Id cannot be null")
	private int categoryId;
	@JsonInclude(Include.NON_NULL)
	private String categoryName;
	@NotNull(message = "Default Branch cannot be null")
	private BranchDTO defaultBranch;
	@JsonInclude(Include.NON_NULL)
	private List<BranchDTO> previlegeBranches;
	@JsonInclude(Include.NON_NULL)
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

	public BranchDTO getDefaultBranch() {
		return defaultBranch;
	}

	public void setDefaultBranch(BranchDTO defaultBranch) {
		this.defaultBranch = defaultBranch;
	}

	public List<BranchDTO> getPrevilegeBranches() {
		return previlegeBranches;
	}

	public void setPrevilegeBranches(List<BranchDTO> previlegeBranches) {
		this.previlegeBranches = previlegeBranches;
	}

	public List<RoleDTO> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<RoleDTO> userRoles) {
		this.userRoles = userRoles;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
