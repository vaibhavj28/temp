package com.safexpress.propeli.bff.dto;

import java.util.List;
import java.util.Map;

public class HierarchyBranchListDTO {
	
	
	List<BranchHierarchyDTO> defaultBranchHierarchyList;
	Map<Integer,List<BranchHierarchyDTO>> privilegeBranchHierarchyList;

	
	public List<BranchHierarchyDTO> getDefaultBranchHierarchyList() {
		return defaultBranchHierarchyList;
	}
	public void setDefaultBranchHierarchyList(List<BranchHierarchyDTO> defaultBranchHierarchyList) {
		this.defaultBranchHierarchyList = defaultBranchHierarchyList;
	}
	public Map<Integer, List<BranchHierarchyDTO>> getPrivilageBranchHierarchyList() {
		return privilegeBranchHierarchyList;
	}
	public void setPrivilageBranchHierarchyList(Map<Integer, List<BranchHierarchyDTO>> privilegeBranchHierarchyList) {
		this.privilegeBranchHierarchyList = privilegeBranchHierarchyList;
	}
	

}
