package com.safexpress.propeli.bff.service;

import com.safexpress.propeli.bff.dto.Response;
import com.safexpress.propeli.bff.dto.RoleDTO;
import com.safexpress.propeli.bff.dto.UserBranchMappingDTO;
import com.safexpress.propeli.bff.dto.UserDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;

import java.util.List;

/**
 * @author IBM India
 * 	
 **/

public interface UserService {

	String saveUser(DFHeader header, UserDTO newUser) throws Exception;

	Response<UserDTO> getUser(DFHeader header, String userId) throws Exception;

	Response<UserBranchMappingDTO> getPrevilegeBranches(DFHeader header, String userId) throws Exception;

	Response<RoleDTO> getUserRoles(DFHeader header, String userId) throws Exception;

	Response<UserDTO> getAllUsers(DFHeader header) throws Exception;

	String updateUser(DFHeader header, UserDTO updatedUser) throws Exception;

	String updateUserPrevilegeBranches(DFHeader header, String userId, String idKey, List<UserBranchMappingDTO> previlegeBranches) throws Exception;

	String updateUserRoles(DFHeader header, String userId, String idKey, List<RoleDTO> userRoles) throws Exception;

	Response<UserDTO> getLastNUpdatedUsers(DFHeader header, int number) throws Exception;

}
