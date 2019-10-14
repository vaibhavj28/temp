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
import com.safexpress.propeli.bff.dto.LookUpDTO;
import com.safexpress.propeli.bff.dto.RoleDTO;
import com.safexpress.propeli.bff.dto.RolePermissionDTO;
import com.safexpress.propeli.bff.service.MdmService;
import com.safexpress.propeli.servicebase.annotation.SFXApi;
import com.safexpress.propeli.servicebase.dto.ResponseDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "User Management MDM BFF Controller")
@SFXApi
@RestController
@RequestMapping("/secure/um/v1/")
public class MdmController {

	@Autowired
	private MdmService service;
	
	@Autowired
	private ResponseDTO responseDTO;

	@ApiOperation(value = "Fetches all Roles")
	@GetMapping("roles")
	public ResponseEntity<ResponseDTO> getAllRoles(@Valid DFHeader header) throws Exception {

		List<RoleDTO> roles = service.getAllRoles(header);
		responseDTO.setMessage("success");
		responseDTO.setData(roles);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

	@ApiOperation(value = "Fetches Permissions of a Role")
	@GetMapping("roles/{roleId}/permissions")
	public ResponseEntity<ResponseDTO> getRolePermission(@Valid DFHeader header, @PathVariable long roleId) throws Exception {

		RolePermissionDTO roleDetail = service.getRolePermission(header, roleId);
		responseDTO.setMessage("success");
		responseDTO.setData(roleDetail);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

	@ApiOperation(value = "Creates A Role with permissions")
	@PostMapping("roles")
	public ResponseEntity<ResponseDTO> createRolePermission(@Valid DFHeader header,
			@ApiParam(value = "Role data to be inserted", required = true) @Valid @RequestBody RolePermissionDTO roleDetail) throws Exception{

		ResponseDTO response = service.addRolePermission(header, roleDetail);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@ApiOperation(value = "Updates A Role with permissions")
	@PutMapping("roles")
	public ResponseEntity<ResponseDTO> updateRolePermission(@Valid DFHeader header,
			@ApiParam(value = "Role data to be updated", required = true) @Valid @RequestBody RolePermissionDTO roleDetail) {

		Integer res = service.editRolePermission(header,roleDetail);
		responseDTO.setMessage("Role Updated");
		responseDTO.setData(res);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}
	
	@ApiOperation(value = "lookup for user category and channel", notes = "lookup for user category and channel")
	@GetMapping("lookup/{lokupType}")
	public ResponseEntity<ResponseDTO> lookup(@Valid DFHeader header,  @PathVariable String lokupType)
			throws Exception {
		List<LookUpDTO> response = service.lookupData(header, lokupType);
		responseDTO.setMessage("success");
		responseDTO.setData(response);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}
	
	@ApiOperation(value = "Branch List Details", notes = "Get a list of all Branch Details")
	@GetMapping(path = "branches", produces = "application/json")
	public ResponseEntity<ResponseDTO> getAllBranches(DFHeader header) throws Exception {
		List<BranchDTO> reponse = service.getAllBranch(header);
		responseDTO.setMessage("success");
		responseDTO.setData(reponse);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

}
