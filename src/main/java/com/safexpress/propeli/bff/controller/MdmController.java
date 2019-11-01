package com.safexpress.propeli.bff.controller;

import java.util.List;

import javax.validation.Valid;

import com.safexpress.propeli.bff.dto.*;
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
	public ResponseEntity<Response<RoleDTO>> getAllRoles(@Valid DFHeader header) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(service.getAllRoles(header));
	}

	@ApiOperation(value = "Fetches Permissions of a Role")
	@GetMapping("roles/{roleId}/permissions")
	public ResponseEntity<Response<RolePermissionDTO>> getRolePermission(@Valid DFHeader header, @PathVariable long roleId) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(service.getRolePermission(header, roleId));
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
	@GetMapping("lookup/{lookupType}")
	public ResponseEntity<Response<LookUpDTO>> lookup(@Valid DFHeader header,  @PathVariable String lookupType)
			throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(service.lookupData(header, lookupType));
	}

	@ApiOperation(value = "Menu Hierarchy List Details", notes = "Get a list of all Menu Hierarchies")
	@GetMapping(path = "objects/menuHierarchies", produces = "application/json")
	public ResponseEntity<Response<MenuHierarchyDTO>> getMenuHierarchy(DFHeader header) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(service.getMenuHierarchy(header));
	}

	@ApiOperation(value = "Get last n updated Role  Details", notes = "Get a list of all Role Details")
	@GetMapping("roles/lastUpdated/{number}")
	public ResponseEntity<Response<RoleDTO>> getLastNUpdatedRoles(@Valid DFHeader header,
																  @PathVariable int number) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(service.getLastNUpdatedRoles(header, number));
	}

}
