package com.safexpress.propeli.bff.service;

import java.util.List;

import com.safexpress.propeli.bff.dto.BranchDTO;
import com.safexpress.propeli.bff.dto.RoleDTO;
import com.safexpress.propeli.bff.dto.UserDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;

/**
 * @author IBM India
 * 	
 **/

public interface UserService {

	String addUser(DFHeader header, UserDTO newUser) throws Exception;

	UserDTO getUser(DFHeader header, String userId) throws Exception;

	List<BranchDTO> getPrevilegeBranches(DFHeader header, String userId) throws Exception;

	List<RoleDTO> getUserRoles(DFHeader header, String userId) throws Exception;

	List<UserDTO> getAllUsers(DFHeader header) throws Exception;

	String updateUser(DFHeader header, UserDTO updatedUser) throws Exception;

	String updateUserPrevilegeBranches(DFHeader header, String userId, String idKey, List<BranchDTO> previlegeBranches) throws Exception;

	String updateUserRoles(DFHeader header, String userId, String idKey, List<RoleDTO> userRoles) throws Exception;

}
