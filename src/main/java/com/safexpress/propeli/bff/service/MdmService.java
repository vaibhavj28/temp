package com.safexpress.propeli.bff.service;

import com.safexpress.propeli.bff.dto.LookUpDTO;
import com.safexpress.propeli.bff.dto.Response;
import com.safexpress.propeli.bff.dto.RoleDTO;
import com.safexpress.propeli.bff.dto.RolePermissionDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;

public interface MdmService {

	Response<RoleDTO> getAllRoles(DFHeader header) throws Exception;

	Response<RolePermissionDTO> getRolePermission(DFHeader header, long roleId) throws Exception;

	String addRolePermission(DFHeader header, RolePermissionDTO roleDetail) throws Exception;

	String editRolePermission(DFHeader header, RolePermissionDTO roleDetail);

	Response<LookUpDTO> lookupData(DFHeader header, String lokupType);

	Response<RoleDTO> getLastNUpdatedRoles(DFHeader header, int number) throws Exception;

}
