package com.safexpress.propeli.bff.controller;

import com.safexpress.propeli.bff.dto.*;
import com.safexpress.propeli.bff.service.MdmService;
import com.safexpress.propeli.servicebase.annotation.SFXApi;
import com.safexpress.propeli.servicebase.dto.ResponseDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "User Management MDM BFF Controller")
@SFXApi
@RestController
@RequestMapping("/secure/v1/roles")
public class MdmController {

	@Autowired
	private MdmService service;

	@ApiOperation(value = "Fetches all Roles")
	@GetMapping
	public ResponseEntity<Response<RoleDTO>> getAllRoles(@Valid DFHeader header) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(service.getAllRoles(header));
	}

	@ApiOperation(value = "Fetches Permissions of a Role")
	@GetMapping("{roleId}/permissions")
	public ResponseEntity<Response<RolePermissionDTO>> getRolePermission(@Valid DFHeader header, @PathVariable long roleId) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(service.getRolePermission(header, roleId));
	}

	@ApiOperation(value = "Creates A Role with permissions")
	@PostMapping
	public ResponseEntity<ResponseDTO> createRolePermission(@Valid DFHeader header,
			@ApiParam(value = "Role data to be inserted", required = true) @Valid @RequestBody RolePermissionDTO roleDetail) throws Exception{

		ResponseDTO response = service.addRolePermission(header, roleDetail);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@ApiOperation(value = "Updates A Role with permissions")
	@PutMapping
	public ResponseEntity<ResponseDTO> updateRolePermission(@Valid DFHeader header,
			@ApiParam(value = "Role data to be updated", required = true) @Valid @RequestBody RolePermissionDTO roleDetail) {
		ResponseDTO responseDTO = new ResponseDTO();
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

	@ApiOperation(value = "Get last n updated Role  Details", notes = "Get a list of all Role Details")
	@GetMapping("lastUpdated/{number}")
	public ResponseEntity<Response<RoleDTO>> getLastNUpdatedRoles(@Valid DFHeader header,
																  @PathVariable int number) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(service.getLastNUpdatedRoles(header, number));
	}

}
