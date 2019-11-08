package com.safexpress.propeli.bff.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safexpress.propeli.bff.dto.CredentialDTO;
import com.safexpress.propeli.bff.dto.MenuHierarchyDTO;
import com.safexpress.propeli.bff.dto.TokenDTO;
import com.safexpress.propeli.bff.exception.CommonBffException;
import com.safexpress.propeli.bff.service.AuthnzService;
import com.safexpress.propeli.servicebase.annotation.SFXApi;
import com.safexpress.propeli.servicebase.dto.ResponseDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Ajay Singh Negi
	
 *
 */
@Api(value = "Authentication and Authorization", tags = "Authentication and Authorization", description = "Authentication and Authorization")
@RestController
@SFXApi

public class AuthnzController {

	private static final Logger logger= LoggerFactory.getLogger(AuthnzController.class);
	
	@Autowired
	private AuthnzService authnzService;
	
	@PostMapping("/v1/login")
	@ApiOperation(value = "Get access-token in cookie", notes = "Get access-token in cookie")
	public ResponseEntity<ResponseDTO<Void>> login(@Valid DFHeader header, @RequestBody CredentialDTO credentialDTO, HttpServletResponse response){
		
		TokenDTO tokenDTO =authnzService.getToken(header, credentialDTO);
		String token=tokenDTO.getAccessToken();
		response.addCookie(authnzService.createCookie(token));
		ResponseDTO<Void> responseDTO= new ResponseDTO<>();
		responseDTO.setMessage("User logged in. access-token returned in cookie");
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
		
	}
	
	@PostMapping("/secure/v1/logout")
	@ApiOperation(value = "Logout", notes = "Logout")
	public ResponseEntity<ResponseDTO<Void>> logout(@Valid DFHeader header){	
		authnzService.logout(header);
		ResponseDTO<Void> responseDTO= new ResponseDTO<>();
		responseDTO.setMessage("User logged out successfully");
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}
	
	@GetMapping("/secure/v1/menu")
	@ApiOperation(value = "Get menu details", notes = "Get menu details")
	public ResponseEntity<ResponseDTO<List<MenuHierarchyDTO>>> getUserMenu(@Valid DFHeader header){	
		
		List<MenuHierarchyDTO> menu =authnzService.getUserMenu(header);
		ResponseDTO<List<MenuHierarchyDTO>> responseDTO= new ResponseDTO<>();
		responseDTO.setMessage("Menu details retrieved successfully");
		responseDTO.setData(menu);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}
	
	@GetMapping("/secure/v1/permissions")
	@ApiOperation(value = "Get user permissions details", notes = "Get user permissions details")
	public ResponseEntity<ResponseDTO<Map<String, Object>>> getUserPermissions(@Valid DFHeader header){	
		
		Map<String, Object> permissions =authnzService.getAllPermissionsForUser(header);
		ResponseDTO<Map<String, Object>> responseDTO= new ResponseDTO<>();
		responseDTO.setMessage("User permissions fetched successfully");
		responseDTO.setData(permissions);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}
	
}
