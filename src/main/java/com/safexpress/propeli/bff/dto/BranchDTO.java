package com.safexpress.propeli.bff.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchDTO implements Serializable {
	private static final long serialVersionUID = 316332571995776L;
	
	@JsonInclude(Include.NON_ABSENT)
	private Long branchId;
	@JsonInclude(Include.NON_NULL)
	@JsonIgnore
	private Long parentBranchId;
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
	@JsonInclude(Include.NON_NULL)
	private int isDefault;
	@JsonInclude(Include.NON_NULL)
	private String address;
	@JsonInclude(Include.NON_NULL)
	private int cutoffTime;
	@JsonInclude(Include.NON_NULL)
	private int organisationId;
	@JsonInclude(Include.NON_NULL)
	private int branchtypeId;
	@JsonInclude(Include.NON_NULL)
	private int autogenWaybl;
	@JsonInclude(Include.NON_NULL)
	private String branchRating;
	@JsonInclude(Include.NON_NULL)
	private int lagTime;
	@JsonInclude(Include.NON_NULL)
	private String descr;
	@JsonInclude(Include.NON_NULL)
	private int status;
	private Date expiryDate;
	private Date effectiveDate;
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

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public void setParentBranchId(Long parentBranchId) {
		this.parentBranchId = parentBranchId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCutoffTime() {
		return cutoffTime;
	}

	public void setCutoffTime(int cutoffTime) {
		this.cutoffTime = cutoffTime;
	}

	public int getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(int organisationId) {
		this.organisationId = organisationId;
	}

	public int getBranchtypeId() {
		return branchtypeId;
	}

	public void setBranchtypeId(int branchtypeId) {
		this.branchtypeId = branchtypeId;
	}

	public int getAutogenWaybl() {
		return autogenWaybl;
	}

	public void setAutogenWaybl(int autogenWaybl) {
		this.autogenWaybl = autogenWaybl;
	}

	public String getBranchRating() {
		return branchRating;
	}

	public void setBranchRating(String branchRating) {
		this.branchRating = branchRating;
	}

	public int getLagTime() {
		return lagTime;
	}

	public void setLagTime(int lagTime) {
		this.lagTime = lagTime;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}
