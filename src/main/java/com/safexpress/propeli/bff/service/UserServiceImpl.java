package com.safexpress.propeli.bff.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.safexpress.propeli.bff.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.safexpress.propeli.servicebase.model.DFHeader;
import com.safexpress.propeli.servicebase.util.BaseUtil;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private RestTemplate restTemplate;

/**	@Value("${service.MdmUserManagement.um-url}")
	private String url;

	@Value("${service.MdmUserManagement.mdm-url}")
	private String mdmUrl;

	@Value("${service.MdmUserManagement.mdm-branch-url}")
	private String mdmBranchUrl;
	**/
		
	@Value("${service.mdmUserManagement.host}")
	String host;
	
	@Value("${service.mdmUserManagement.port}")
	String port;
	
	@Value("${service.mdmUserManagement.users_uri}")
	String uri;

	@Value("${service.lookUpNotepadCommandmentService.port}")
	String lookUpNotePadCommandmentPort;

	@Value("${service.branchService.port}")
	String branchPort;

	@Value("${service.lookUpNotepadCommandmentService.lookUp_uri}")
	String lookUpUri;

	@Value("${service.lookUpNotepadCommandmentService.notePad_uri}")
	String notePadUri;

	@Value("${service.branchService.branch_uri}")
	String branchUri;

	@Value("${service.branchService.branch_url}")
	String branchUrl;

	@Value("${service.mdmUserManagement.protocol}")
	String protocol;

	@Value("${service.lookUpNotepadCommandmentService.look_up_url}")
	String lookUpUrl;

	@Value("${service.mdmUserManagement.users_url}")
	String usersUrl;

	@Autowired
	UserBranchMappingDTO userBranchMappingDTO;

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	Response response;

/**
	@Override
	public String addUser(DFHeader header, UserDTO newUser) throws Exception {
		
		if(CollectionUtils.isEmpty(newUser.getUserRoles())){
			throw new ValidationException().businessErrorCode("1005")
			.businessErrorMessage("User Should be created with atleast one role")
			.error("User Id", "002", newUser.getUserId() + " " + "Does Not Have A Role Associated With It");
		}
		else
			 return saveUser(header,newUser);		
			
	}
	**/
	public String saveUser(DFHeader header, UserDTO newUser) throws Exception {
		
		try {
			HttpEntity<UserDTO> entity = new HttpEntity<>(newUser, BaseUtil.payload(header));
					Map<String, String> response = restTemplate.exchange(new URI(usersUrl), HttpMethod.POST, entity, Map.class).getBody();
			return response.get("responseMessage");
		} catch (RestClientResponseException e) {
			log.error("Inside UserServiceImpl :: saveUser()" + e.getResponseBodyAsString());
			String errorpayload = e.getResponseBodyAsString();			
			throw e;
		} 
	}	

	@Override
	public Response<UserDTO> getUser(DFHeader header, String userId) throws Exception {
		try {
			HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
			UserDTO userDTO = restTemplate.exchange(new URI(usersUrl + userId), HttpMethod.GET, entity, UserDTO.class)
					.getBody();
			Response responseDTO = new Response();
			List<UserDTO> userList = new ArrayList<>();
			userList.add(userDTO);
 			responseDTO.setData(userList);
			ResponseEntity<List<LookUpMDMDTO>> lookUpResponse = restTemplate.exchange(
					new URI(lookUpUrl + "/lookUpValueByLookUpType/USER_CTGY" ), HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<LookUpMDMDTO>>() {
					});
			UserBranchMappingDTO branchMappingDTO = restTemplate.exchange(new URI(branchUrl + "/branchCode/" + userDTO.getDefaultBranch().getBranchCode()), HttpMethod.GET, entity, UserBranchMappingDTO.class)
					.getBody();
			branchMappingDTO.setIsDefault(userDTO.getDefaultBranch().getIsDefault());
			branchMappingDTO.setAddOrRemove(userDTO.getDefaultBranch().getAddOrRemove());
			ReferenceDTO referenceDTO = new ReferenceDTO();
			referenceDTO.setCategoryList(lookUpResponse.getBody());
			referenceDTO.setBranch(branchMappingDTO);
			responseDTO.setRefernceList(referenceDTO);
			return  responseDTO;

		} catch (RestClientResponseException e) {
			log.error("Inside UserServiceImpl :: getUser()" + e.getResponseBodyAsString());
			throw e;
		}
	}

	@Override
	public Response<BranchDTO> getPrevilegeBranches(DFHeader header, String userId) throws Exception {
		try {
			HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
			ResponseEntity<List<BranchDTO>> branchDTOs = restTemplate.exchange(
					new URI(usersUrl + userId + "/previlegeBranchCodes"), HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<BranchDTO>>() {
					});
			response.setData(branchDTOs.getBody());
			response.setMessage("success");
			return response;
		} catch (RestClientResponseException e) {
			log.error("Inside UserServiceImpl :: getPrevilegeBranches() " + e.getResponseBodyAsString());
			throw e;
		}
	}

	@Override
	public Response<RoleDTO> getUserRoles(DFHeader header, String userId) throws Exception {
		try {
			HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
			ResponseEntity<List<RoleDTO>> roles = restTemplate.exchange(new URI(usersUrl + userId + "/roles"),
					HttpMethod.GET, entity, new ParameterizedTypeReference<List<RoleDTO>>() {
					});
			response.setData(roles.getBody());
			response.setMessage("success");
			return response;
		} catch (RestClientResponseException e) {
			log.error("Inside UserServiceImpl :: getUserRoles() " + e.getResponseBodyAsString());
			throw e;
		}
	}

	@Override
	public List<UserDTO> getAllUsers(DFHeader header) throws Exception {
		try {
			HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
			ResponseEntity<List<UserDTO>> response = restTemplate.exchange(new URI(usersUrl), HttpMethod.GET,
					entity, new ParameterizedTypeReference<List<UserDTO>>() {
					});
			return response.getBody();
		} catch (RestClientResponseException e) {
			log.error("Inside UserServiceImpl :: getAllUsers() " + e.getResponseBodyAsString());
			throw e;
		}
	}

	@Override
	public String updateUser(DFHeader header, UserDTO updatedUser) throws Exception {
		try {
			HttpEntity<UserDTO> entity = new HttpEntity<>(updatedUser, BaseUtil.payload(header));
			Map<String, String> response = restTemplate.exchange(new URI(usersUrl), HttpMethod.PUT, entity, Map.class).getBody();
			return response.get("responseMessage");

		} catch (RestClientResponseException e) {
			log.error("Inside UserServiceImpl :: editUser() " + e.getResponseBodyAsString());
			throw e;
		}
	}

	@Override
	public String updateUserPrevilegeBranches(DFHeader header, String userId, String idKey,
			List<BranchDTO> previlegeBranches) throws Exception {

		try {
			HttpEntity<Object> entity = new HttpEntity<>(previlegeBranches, BaseUtil.payload(header));
			Map<String, String> params = new HashMap<>();
			params.put("userId", userId);
			params.put("idKey", idKey);
			URI uri = UriComponentsBuilder.fromUriString(usersUrl + "{userId}/{idKey}/previlegeBranches")
					.buildAndExpand(params).toUri();
			Map<String, String> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Map.class).getBody();
			return response.get("responseMessage");

		} catch (RestClientResponseException e) {
			log.error("Inside UserServiceImpl :: updateUserPrevilegeBranches() " + e.getResponseBodyAsString());
			throw e;
		}

	}

	@Override
	public String updateUserRoles(DFHeader header, String userId, String idKey, List<RoleDTO> userRoles)
			throws Exception {

		try {
			HttpEntity<Object> entity = new HttpEntity<>(userRoles, BaseUtil.payload(header));
			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", userId);
			params.put("idKey", idKey);
			URI uri = UriComponentsBuilder.fromUriString(usersUrl + "{userId}/{idKey}/roles")
					.buildAndExpand(params).toUri();
			return restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class).getBody();

		} catch (RestClientResponseException e) {
			log.error("Inside UserServiceImpl :: updateUserRoles() " + e.getResponseBodyAsString());
			throw e;
		}

	}
}
