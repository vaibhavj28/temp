package com.safexpress.propeli.bff.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class BranchDTO implements Serializable {
	private static final long serialVersionUID = 316332571995776L;
	
	@JsonInclude(Include.NON_NULL)
	private Long branchId;
	@JsonInclude(Include.NON_NULL)
	private long parentBranchId;
	@NotNull(message = "branchCode cannot be null")
	private String branchCode;
	private String parentBranchCode;
	@NotNull(message = "branchName cannot be null")
	private String branchName;
	private String phone;
	private String mobile;
	private String email;
	@JsonInclude(Include.NON_NULL)
	private String addOrRemove;
	private int isDefault;
	
	
	public long getBranchId() {
		return branchId;
	}
	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}
	public long getParentBranchId() {
		return parentBranchId;
	}
	public void setParentBranchId(long parentBranchId) {
		this.parentBranchId = parentBranchId;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddOrRemove() {
		return addOrRemove;
	}
	public void setAddOrRemove(String addOrRemove) {
		this.addOrRemove = addOrRemove;
	}
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}	
		
}
