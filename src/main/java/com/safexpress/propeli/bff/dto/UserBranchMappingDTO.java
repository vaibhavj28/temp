package com.safexpress.propeli.bff.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Component
public class UserBranchMappingDTO implements Serializable {
    private static final long serialVersionUID = 316332571995776L;

    @ApiModelProperty(value = "User default branch code", notes = "User default branch code must be in characters and cannot be empty or null")
    @NotEmpty(message = "Default Branch code cannot be null")
    private String branchCode;
    @ApiModelProperty(value = "Flag to specify if the branch is to be added or removed or updated", notes = "Specify 'add' for addition and 'remove' for removal and 'update' for updation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String addOrRemoveOrUpdate;
    @ApiModelProperty(value = "Flag to specify if the associated branch id default or previlege", notes = "1 means default and 0 means previlege")
    private int isDefault;
    private int status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date effectiveDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expiryDate;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAddOrRemoveOrUpdate() {
        return addOrRemoveOrUpdate;
    }

    public void setAddOrRemoveOrUpdate(String addOrRemoveOrUpdate) {
        this.addOrRemoveOrUpdate = addOrRemoveOrUpdate;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
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
