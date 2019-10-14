package com.safexpress.propeli.bff.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safexpress.propeli.bff.dto.BranchDTO;
import com.safexpress.propeli.bff.dto.ResponseDTO;
import com.safexpress.propeli.bff.dto.RoleDTO;
import com.safexpress.propeli.bff.dto.UserDTO;
import com.safexpress.propeli.bff.service.UserService;
import com.safexpress.propeli.servicebase.annotation.SFXApi;
import com.safexpress.propeli.servicebase.model.DFHeader;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author IBM India
 * 
 **/
@Api(value = "User Management BFF Controller")
@SFXApi
@RestController
@RequestMapping("/secure/um/v1/")
public class UserController {

	@Autowired
	private UserService service;

	@ApiOperation(value = "Creates an User")
	@PostMapping("users")
	public ResponseEntity<ResponseDTO> createUser(@Valid DFHeader header,
			@ApiParam(value = "User data to be inserted", required = true) @Valid @RequestBody UserDTO newUser) throws Exception {
		
		String responseStatus = service.addUser(header, newUser);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("success");
		responseDTO.setData(responseStatus);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}

	@ApiOperation(value = "Fetches all Users")
	@GetMapping("users")
	public ResponseEntity<ResponseDTO> getAllUsers(@Valid DFHeader header) throws Exception {
		
		List<UserDTO> users = service.getAllUsers(header);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("success");
		responseDTO.setData(users);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
		
	}

	@ApiOperation(value = "Fetches an User")
	@GetMapping("users/{uid}")
	public ResponseEntity<ResponseDTO> getUser(@Valid DFHeader header,
			@ApiParam(value = "user id for which user has to be retrieved from database", required = true) @PathVariable("uid") String userId) throws Exception {
		
		UserDTO user = service.getUser(header, userId);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("success");
		responseDTO.setData(user);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

	@ApiOperation(value = "Fetches the list of previlege branches for an User")
	@GetMapping("users/{uid}/previlegeBranches")
	public ResponseEntity<ResponseDTO> getUserPrevilegeBranches(@Valid DFHeader header,
			@ApiParam(value = "user id for which previlege branches have to be retrieved from database", required = true) @PathVariable("uid") String userId) throws Exception {
		List<BranchDTO> userPrevilegeBranches = service.getPrevilegeBranches(header, userId);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("success");
		responseDTO.setData(userPrevilegeBranches);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

	@ApiOperation(value = "Fetches the list of roles for an User")
	@GetMapping("users/{uid}/roles")
	public ResponseEntity<ResponseDTO> getUserRoles(@Valid DFHeader header,
			@ApiParam(value = "user id for which roles have to be retrieved from database", required = true) @PathVariable("uid") String userId) throws Exception {
		
		List<RoleDTO> userRoles = service.getUserRoles(header, userId);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("success");
		responseDTO.setData(userRoles);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

	@ApiOperation(value = "Updates an User")
	@PutMapping("users")
	public ResponseEntity<ResponseDTO> updateUser(@Valid DFHeader header,
			@ApiParam(value = "User data to be updated", required = true) @Valid @RequestBody UserDTO updatedUser) throws Exception {
		
		String responseStatus = service.updateUser(header, updatedUser);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("success");
		responseDTO.setData(responseStatus);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

	@ApiOperation(value = "Updates the list of previlege branches for an User")
	@PutMapping("users/{uid}/{idKey}/previlegeBranches")
	public ResponseEntity<ResponseDTO> updateUserPrevilegeBranches(@Valid DFHeader header,
			@ApiParam(value = "user id for which Previlege Branches have to be Updated", required = true) @PathVariable("uid") String userId,
			@ApiParam(value = "user key for which Previlege Branches have to be Updated", required = true) @PathVariable String idKey,
			@ApiParam(value = "Previlege Branch List to be Updated", required = true) @Valid @RequestBody List<BranchDTO> previlegeBranches) throws Exception {
		
		String responseStatus = service.updateUserPrevilegeBranches(header, userId, idKey, previlegeBranches);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("success");
		responseDTO.setData(responseStatus);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

	@ApiOperation(value = "Updates the list of roles for an User")
	@PutMapping("users/{uid}/{idKey}/roles")
	public ResponseEntity<ResponseDTO> updateUserRoles(@Valid DFHeader header,
			@ApiParam(value = "user id for which Roles have to be Updated", required = true) @PathVariable("uid") String userId,
			@ApiParam(value = "user key for which Roles have to be Updated", required = true) @PathVariable String idKey,
			@ApiParam(value = "Role List to be Updated", required = true) @Valid @RequestBody List<RoleDTO> userRoles) throws Exception {
		
		String responseStatus = service.updateUserRoles(header, userId, idKey, userRoles);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("success");
		responseDTO.setData(responseStatus);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

}
