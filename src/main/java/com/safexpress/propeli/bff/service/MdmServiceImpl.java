package com.safexpress.propeli.bff.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.safexpress.propeli.bff.configuration.Util;
import com.safexpress.propeli.bff.dto.BranchDTO;
import com.safexpress.propeli.bff.dto.LookUpDTO;
import com.safexpress.propeli.bff.dto.RoleDTO;
import com.safexpress.propeli.bff.dto.RolePermissionDTO;
import com.safexpress.propeli.servicebase.dto.ResponseDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;

@Service
public class MdmServiceImpl implements MdmService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.MdmUserManagement.mdm-url}")
	private String url;

	private static final Logger log = LoggerFactory.getLogger(MdmServiceImpl.class);

	@Override
	public List<RoleDTO> getAllRoles(DFHeader header) throws Exception {

		try {
			HttpEntity<DFHeader> entity = new HttpEntity<>(Util.payload(header));
			ResponseEntity<List<RoleDTO>> response = restTemplate.exchange(new URI(url + "/roles/"), HttpMethod.GET,
					entity, new ParameterizedTypeReference<List<RoleDTO>>() {
					});
			return response.getBody();
		} catch (RestClientException | URISyntaxException e) {
			log.error("Inside MdmServiceImpl :: getAllRoles() " + e.getMessage());
			throw e;
		}
	}

	@Override
	public RolePermissionDTO getRolePermission(DFHeader header, long roleId) throws Exception {

		try {
			HttpEntity<DFHeader> entity = new HttpEntity<>(Util.payload(header));
			return restTemplate.exchange(new URI(url + "/permission/role/" + roleId), HttpMethod.GET, entity,
					RolePermissionDTO.class).getBody();

		} catch (RestClientException | URISyntaxException e) {
			log.error("Inside MdmServiceImpl :: getRolePermission() " + e.getMessage());
			throw e;
		}
	}

	@Override
	public ResponseDTO addRolePermission(DFHeader header, RolePermissionDTO roleDetail) throws Exception {
		try {
			HttpEntity<RoleDTO> entity = new HttpEntity<>(roleDetail.getRoleDto(), Util.payload(header));
			ResponseDTO response = new ResponseDTO();
			int status = 0;
			String data = null;
			RoleDTO role = restTemplate.exchange(new URI(url + "/roles"), HttpMethod.POST, entity, RoleDTO.class)
					.getBody();
			if (role.getRoleId() != 0) {
				status = 1;
				data = "role created without permissions";
			}
			roleDetail.setRoleDto(role);
			if (!CollectionUtils.isEmpty(roleDetail.getObjectPermissionList())) {
				status = addPermission(header, roleDetail);
				if (status == 1) {
					data = "role created with permissions";
				}
			}
			response.setMessage(data);
			response.setData(status);
			return response;
			
		} catch (RestClientException e) {
			log.error("Inside MdmServiceImpl :: addRolePermission() " + e.getMessage());
			throw e;
		}
	}
	private int addPermission(DFHeader header, RolePermissionDTO roleDetail) throws Exception {
		try {
		HttpEntity<RolePermissionDTO> rolePermissionEntity = new HttpEntity<>(roleDetail,Util.payload(header));
		return restTemplate.exchange(new URI(url + "/role/permission"), HttpMethod.POST, rolePermissionEntity, Integer.class).getBody();
		} catch (RestClientException e) {
			log.error("Inside MdmServiceImpl :: addRolePermission() " + e.getMessage());
			return 0;
		}
	}
	
	@Override
	public List<LookUpDTO> lookupData(@Valid DFHeader header, String lokupType) {
		try {
			HttpEntity<Long> entity = new HttpEntity<>(Util.payload(header));
			Map<String, String> params = new HashMap<String, String>();
			params.put("lokupType", lokupType);
			URI uri = UriComponentsBuilder.fromUriString(url + "/lookUp/{lokupType}").buildAndExpand(params).toUri();
			return restTemplate
					.exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<List<LookUpDTO>>() {
					}).getBody();

		} catch (RestClientException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<BranchDTO> getAllBranch(DFHeader header) {
		try {
			HttpEntity<String> entity = new HttpEntity<>(Util.payload(header));
			ResponseEntity<List<BranchDTO>> reponse = restTemplate.exchange(new URI(url + "/AllBranchDetails"),
					HttpMethod.GET, entity, new ParameterizedTypeReference<List<BranchDTO>>() {
					});
			return reponse.getBody();
		} catch (RestClientException | URISyntaxException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Integer editRolePermission(DFHeader header, RolePermissionDTO roleDetail) {
		try {
			HttpEntity<RolePermissionDTO> entity = new HttpEntity<>(roleDetail, Util.payload(header));
			return restTemplate.exchange(new URI(url + "/role/permission"), HttpMethod.PUT, entity, Integer.class)
					.getBody();
			
		} catch (RestClientException | URISyntaxException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

}
