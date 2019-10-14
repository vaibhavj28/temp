package com.safexpress.propeli.bff.service;

import java.util.List;

import com.safexpress.propeli.bff.dto.BranchDTO;
import com.safexpress.propeli.bff.dto.LookUpDTO;
import com.safexpress.propeli.bff.dto.RoleDTO;
import com.safexpress.propeli.bff.dto.RolePermissionDTO;
import com.safexpress.propeli.servicebase.dto.ResponseDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;

public interface MdmService {

	List<RoleDTO> getAllRoles(DFHeader header) throws Exception;

	RolePermissionDTO getRolePermission(DFHeader header, long roleId) throws Exception;

	ResponseDTO addRolePermission(DFHeader header, RolePermissionDTO roleDetail) throws Exception;

	Integer editRolePermission(DFHeader header, RolePermissionDTO roleDetail);
	
	List<LookUpDTO> lookupData(DFHeader header, String lokupType);

	List<BranchDTO> getAllBranch(DFHeader header);

}
