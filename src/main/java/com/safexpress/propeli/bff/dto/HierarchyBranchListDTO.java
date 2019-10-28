package com.safexpress.propeli.bff.dto;

import java.util.List;
import java.util.Map;

public class HierarchyBranchListDTO {
	
	
	List<BranchInputDTO> defaultBranchHierarchyList;
	Map<Integer,List<BranchInputDTO>> privilegeBranchHierarchyList;

	
	public List<BranchInputDTO> getDefaultBranchHierarchyList() {
		return defaultBranchHierarchyList;
	}
	public void setDefaultBranchHierarchyList(List<BranchInputDTO> defaultBranchHierarchyList) {
		this.defaultBranchHierarchyList = defaultBranchHierarchyList;
	}
	public Map<Integer, List<BranchInputDTO>> getPrivilageBranchHierarchyList() {
		return privilegeBranchHierarchyList;
	}
	public void setPrivilageBranchHierarchyList(Map<Integer, List<BranchInputDTO>> privilegeBranchHierarchyList) {
		this.privilegeBranchHierarchyList = privilegeBranchHierarchyList;
	}
	

}
