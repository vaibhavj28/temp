package com.safexpress.propeli.bff.service;

import java.util.List;

import com.safexpress.propeli.bff.dto.*;
import com.safexpress.propeli.servicebase.dto.ResponseDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;

public interface MdmService {

	Response<RoleDTO> getAllRoles(DFHeader header) throws Exception;

	Response<RolePermissionDTO> getRolePermission(DFHeader header, long roleId) throws Exception;

	ResponseDTO addRolePermission(DFHeader header, RolePermissionDTO roleDetail) throws Exception;

	Integer editRolePermission(DFHeader header, RolePermissionDTO roleDetail);

	Response<LookUpDTO> lookupData(DFHeader header, String lokupType);

	Response<MenuHierarchyDTO> getMenuHierarchy(DFHeader header) throws Exception;

}
