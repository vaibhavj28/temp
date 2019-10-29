package com.safexpress.propeli.bff.service;

import java.util.List;

import com.safexpress.propeli.bff.dto.*;
import com.safexpress.propeli.servicebase.model.DFHeader;

/**
 * @author IBM India
 * 	
 **/

public interface UserService {

	String saveUser(DFHeader header, UserDTO newUser) throws Exception;

	Response<UserDTO> getUser(DFHeader header, String userId) throws Exception;

	Response<BranchDTO> getPrevilegeBranches(DFHeader header, String userId) throws Exception;

	Response<RoleDTO> getUserRoles(DFHeader header, String userId) throws Exception;

	List<UserDTO> getAllUsers(DFHeader header) throws Exception;

	String updateUser(DFHeader header, UserDTO updatedUser) throws Exception;

	String updateUserPrevilegeBranches(DFHeader header, String userId, String idKey, List<UserBranchMappingDTO> previlegeBranches) throws Exception;

	String updateUserRoles(DFHeader header, String userId, String idKey, List<RoleDTO> userRoles) throws Exception;

}
