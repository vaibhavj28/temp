package com.safexpress.propeli.bff.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class BranchHierarchyDTO implements Serializable {
	private static final long serialVersionUID = 316332571995776L;
	
	@JsonInclude(Include.NON_NULL)
	private int branchId;
	@JsonInclude(Include.NON_NULL)
	private long parentBranchId;
	@NotNull(message = "branchCode cannot be null")
	private String parentBranchCode;
	@NotNull(message = "branchName cannot be null")
	private String branchName;
	@JsonInclude(Include.NON_NULL)
	private int isDefault;
	private String branchCode;
	private String menuFullId;
	
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public long getParentBranchId() {
		return parentBranchId;
	}
	public void setParentBranchId(long parentBranchId) {
		this.parentBranchId = parentBranchId;
	}
	public String getParentBranchCode() {
		return parentBranchCode;
	}
	public void setParentBranchCode(String parentBranchCode) {
		this.parentBranchCode = parentBranchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	public String getMenuFullId() {
		return menuFullId;
	}

	public void setMenuFullId(String menuFullId) {
		this.menuFullId = menuFullId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public BranchHierarchyDTO(int branchId , int parentBranchId, String menuFullId, String branchCode) {
		super();
		this.parentBranchId = parentBranchId;
		this.branchId = branchId;
		this.menuFullId = menuFullId;
		this.branchCode=branchCode;
	}

	public BranchHierarchyDTO() {
		/**
         * default constructor
		 */
	}



}
