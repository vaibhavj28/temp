package com.safexpress.propeli.bff.dto;

import java.util.List;

public class ReferenceDTO {
    private List<LookUpMDMDTO> userCategoryList;
    private List<LookUpMDMDTO> channelList;
    private List<BranchDTO> branch;
   
    public List<BranchDTO> getBranch() {
        return branch;
    }

    public void setBranch(List<BranchDTO> branch) {
        this.branch = branch;
    }

	public List<LookUpMDMDTO> getUserCategoryList() {
		return userCategoryList;
	}

	public void setUserCategoryList(List<LookUpMDMDTO> userCategoryList) {
		this.userCategoryList = userCategoryList;
	}

	public List<LookUpMDMDTO> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<LookUpMDMDTO> channelList) {
		this.channelList = channelList;
	}
    
}

