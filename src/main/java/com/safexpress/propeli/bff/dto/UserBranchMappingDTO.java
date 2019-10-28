package com.safexpress.propeli.bff.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Component
public class UserBranchMappingDTO implements Serializable {
    private static final long serialVersionUID = 316332571995776L;

    @ApiModelProperty(value="User default branch code", notes = "User default branch code must be in characters and cannot be empty or null")
    @NotEmpty(message = "Default Branch code cannot be null")
    private String branchCode;
    @ApiModelProperty(value="Flag to specify if the branch is to be added or removed ", notes = "Specify 'add' for addition and 'remove' for removal")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String addOrRemove;
    @ApiModelProperty(value="Flag to specify if the associated branch id default or previlege", notes = "1 means default and 0 means previlege")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int isDefault;

    public String getBranchCode() {
        return branchCode;
    }
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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
