package com.safexpress.propeli.bff.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class BranchInputDTO implements Serializable {
	private static final long serialVersionUID = 316332571995776L;
	
	@JsonInclude(Include.NON_NULL)
	private Long branchId;
	@NotNull(message = "branchName cannot be null")
	private String branchName;
	@JsonInclude(Include.NON_NULL)
	private int isDefault;
	private String branchCode;
	
	public long getBranchId() {
		return branchId;
	}
	public void setBranchId(long branchId) {
		this.branchId = branchId;
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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
}
