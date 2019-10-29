package com.safexpress.propeli.bff.dto;

import java.util.List;

public class ReferenceDTO {
    private List<LookUpMDMDTO> categoryList;
    private List<BranchDTO> branch;

    public List<LookUpMDMDTO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<LookUpMDMDTO> categoryList) {
        this.categoryList = categoryList;
    }

    public List<BranchDTO> getBranch() {
        return branch;
    }

    public void setBranch(List<BranchDTO> branch) {
        this.branch = branch;
    }
}

