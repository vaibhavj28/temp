package com.safexpress.propeli.bff.service;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safexpress.propeli.bff.configuration.Util;
import com.safexpress.propeli.bff.dto.BranchDTO;
import com.safexpress.propeli.bff.dto.RoleDTO;
import com.safexpress.propeli.bff.dto.UserDTO;
import com.safexpress.propeli.servicebase.exception.ValidationException;
import com.safexpress.propeli.servicebase.model.DFHeader;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.MdmUserManagement.um-url}")
	private String url;

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

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
	private String saveUser(DFHeader header, UserDTO newUser) throws Exception {
		
		try {
			HttpEntity<UserDTO> entity = new HttpEntity<>(newUser, Util.payload(header));
			Map<String, String> response = restTemplate.exchange(new URI(url + "/users"), HttpMethod.POST, entity, Map.class).getBody();
			return response.get("responseMessage");
		} catch (RestClientResponseException e) {
			log.error("Inside UserServiceImpl :: saveUser()" + e.getResponseBodyAsString());
			String errorpayload = e.getResponseBodyAsString();			
			throw e;
		} 
	}	

	@Override
	public UserDTO getUser(DFHeader header, String userId) throws Exception {
		try {
			HttpEntity<DFHeader> entity = new HttpEntity<>(Util.payload(header));
			return restTemplate.exchange(new URI(url + "/users/" + userId), HttpMethod.GET, entity, UserDTO.class)
					.getBody();

		} catch (RestClientResponseException e) {
			log.error("Inside UserServiceImpl :: getUser()" + e.getResponseBodyAsString());
			throw e;
		}
	}

	@Override
	public List<BranchDTO> getPrevilegeBranches(DFHeader header, String userId) throws Exception {
		try {
			HttpEntity<DFHeader> entity = new HttpEntity<>(Util.payload(header));
			ResponseEntity<List<BranchDTO>> response = restTemplate.exchange(
					new URI(url + "/users/" + userId + "/previlegeBranches"), HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<BranchDTO>>() {
					});
			return response.getBody();
		} catch (RestClientResponseException e) {
			log.error("Inside UserServiceImpl :: getPrevilegeBranches() " + e.getResponseBodyAsString());
			throw e;
		}
	}

	@Override
	public List<RoleDTO> getUserRoles(DFHeader header, String userId) throws Exception {
		try {
			HttpEntity<DFHeader> entity = new HttpEntity<>(Util.payload(header));
			ResponseEntity<List<RoleDTO>> response = restTemplate.exchange(new URI(url + "/users/" + userId + "/roles"),
					HttpMethod.GET, entity, new ParameterizedTypeReference<List<RoleDTO>>() {
					});
			return response.getBody();
		} catch (RestClientResponseException e) {
			log.error("Inside UserServiceImpl :: getUserRoles() " + e.getResponseBodyAsString());
			throw e;
		}
	}

	@Override
	public List<UserDTO> getAllUsers(DFHeader header) throws Exception {
		try {
			HttpEntity<DFHeader> entity = new HttpEntity<>(Util.payload(header));
			ResponseEntity<List<UserDTO>> response = restTemplate.exchange(new URI(url + "/users"), HttpMethod.GET,
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
			HttpEntity<UserDTO> entity = new HttpEntity<>(updatedUser, Util.payload(header));
			Map<String, String> response = restTemplate.exchange(new URI(url + "/users"), HttpMethod.PUT, entity, Map.class).getBody();
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
			HttpEntity<Object> entity = new HttpEntity<>(previlegeBranches, Util.payload(header));
			Map<String, String> params = new HashMap<>();
			params.put("userId", userId);
			params.put("idKey", idKey);
			URI uri = UriComponentsBuilder.fromUriString(url + "/users/{userId}/{idKey}/previlegeBranches")
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
			HttpEntity<Object> entity = new HttpEntity<>(userRoles, Util.payload(header));
			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", userId);
			params.put("idKey", idKey);
			URI uri = UriComponentsBuilder.fromUriString(url + "/users/{userId}/{idKey}/roles")
					.buildAndExpand(params).toUri();
			return restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class).getBody();

		} catch (RestClientResponseException e) {
			log.error("Inside UserServiceImpl :: updateUserRoles() " + e.getResponseBodyAsString());
			throw e;
		}

	}

}
