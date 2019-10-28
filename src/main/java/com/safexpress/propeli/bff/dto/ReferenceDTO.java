package com.safexpress.propeli.bff.dto;

import java.util.List;

public class ReferenceDTO {
    private List<LookUpMDMDTO> categoryList;
    private UserBranchMappingDTO branch;

    public List<LookUpMDMDTO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<LookUpMDMDTO> categoryList) {
        this.categoryList = categoryList;
    }

    public UserBranchMappingDTO getBranch() {
        return branch;
    }

    public void setBranch(UserBranchMappingDTO branch) {
        this.branch = branch;
    }
}

