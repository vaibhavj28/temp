package com.safexpress.propeli.bff.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
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
import com.safexpress.propeli.bff.service.AuthnzService;
import com.safexpress.propeli.servicebase.annotation.SFXApi;
import com.safexpress.propeli.servicebase.dto.ResponseDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;

import io.swagger.annotations.Api;

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
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/v1/login")
	public ResponseEntity<ResponseDTO> login(@Valid DFHeader header, @RequestBody CredentialDTO credentialDTO, HttpServletResponse response){
		
		TokenDTO tokenDTO =authnzService.getToken(header, credentialDTO);
		if(tokenDTO !=null) {
			String token=tokenDTO.getAccessToken();
			response.addCookie(authnzService.createCookie(token));
		}
			
		
		ResponseDTO responseDTO= new ResponseDTO();
		responseDTO.setMessage("Success");
			
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}
	
	@PostMapping("/secure/v1/logout")
	public ResponseEntity<Void> logout(@Valid DFHeader header){	
		authnzService.logout(header);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/secure/v1/menu")
	public ResponseEntity<ResponseDTO> getUserMenu(@Valid DFHeader header){	
		
		List<MenuHierarchyDTO> menu =authnzService.getUserMenu(header);
		ResponseDTO responseDTO= new ResponseDTO();
		responseDTO.setMessage("success");
		responseDTO.setData(menu);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}
	
	@GetMapping("/secure/v1/permissions")
	public ResponseEntity<ResponseDTO> getUserPermissions(@Valid DFHeader header){	
		
		Map<String, Object> permissions =authnzService.getAllPermissionsForUser(header);
		ResponseDTO responseDTO= new ResponseDTO();
		responseDTO.setMessage("success");
		responseDTO.setData(permissions);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}
	
}
