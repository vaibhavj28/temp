package com.safexpress.propeli.bff.service;

import com.safexpress.propeli.bff.configuration.CommonBFFUtil;
import com.safexpress.propeli.bff.dto.*;
import com.safexpress.propeli.security.util.AuthUtil;
import com.safexpress.propeli.servicebase.dto.ResponseDTO;
import com.safexpress.propeli.servicebase.exception.ServiceException;
import com.safexpress.propeli.servicebase.model.DFHeader;
import com.safexpress.propeli.servicebase.util.BaseUtil;
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
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MdmServiceImpl implements MdmService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.lookUpNotepadCommandmentService.look_up_url}")
	private String lookUpUrl;

	@Value("${service.mdmUserManagement.roles_url}")
	private String rolesUrl;

	@Value("${service.mdmUserManagement.objects_url}")
	private String objectUrl;

    @Value("${service.mdmUserManagement.roles_uri}")
    private String rolesUri;

	@Value("${message.success}")
	private
	String successMessage;

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public Response<RoleDTO> getAllRoles(DFHeader header) throws Exception {
		try {
			Response<RoleDTO> response= new Response<>();
			HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
			ResponseEntity<List<RoleDTO>> roles = restTemplate.exchange(new URI(rolesUrl), HttpMethod.GET,
					entity, new ParameterizedTypeReference<List<RoleDTO>>() {
					});
			response.setData(roles.getBody());
			response.setMessage(successMessage);
			return response;
		} catch (RestClientException | URISyntaxException e) {
			log.error("Inside MdmServiceImpl :: getAllRoles() ", e);
			throw e;
		}
	}

	@Override
	public Response<RolePermissionDTO> getRolePermission(DFHeader header, long roleId) throws Exception {

		try {
			Response<RolePermissionDTO> response= new Response<>();
			HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
			ResponseEntity<RolePermissionDTO> rolePermissionDTO = restTemplate.exchange(new URI(rolesUrl + "permissions/" + roleId), HttpMethod.GET, entity,
					RolePermissionDTO.class);
			List<RolePermissionDTO> rolePermission = new ArrayList<>();
			rolePermission.add(rolePermissionDTO.getBody());
			response.setData(rolePermission);
			response.setMessage(successMessage);
			return  response;

		} catch (RestClientException | URISyntaxException e) {
			log.error("Inside MdmServiceImpl :: getRolePermission() " + e.getMessage());
			throw e;
		}
	}

	@Override
	public ResponseDTO addRolePermission(DFHeader header, RolePermissionDTO roleDetail) throws Exception {
		try {
			HttpEntity<RoleDTO> entity = new HttpEntity<>(roleDetail.getRoleDto(), BaseUtil.payload(header));
			ResponseDTO response = new ResponseDTO();
			int status = 0;
			String data = null;
			RoleDTO role = restTemplate.exchange(new URI(rolesUrl), HttpMethod.POST, entity, RoleDTO.class)
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

	/**
	 *
	 * @param header DFHeader
	 * @param roleDetail RolePermissionDTO
	 * @return int
	 * @throws Exception exception
	 */
	private int addPermission(DFHeader header, RolePermissionDTO roleDetail) throws Exception {
		try {
		HttpEntity<RolePermissionDTO> rolePermissionEntity = new HttpEntity<>(roleDetail,BaseUtil.payload(header));
		return restTemplate.exchange(new URI(rolesUrl + "permissions"), HttpMethod.POST, rolePermissionEntity, Integer.class).getBody();
		} catch (RestClientException e) {
			log.error("Inside MdmServiceImpl :: addRolePermission() " + e.getMessage());
			return 0;
		}
	}

	/**
	 *
	 * @param header DFHeader
	 * @param lookupType String
	 * @return  Response<LookUpDTO>
	 */
	@Override
	public Response<LookUpDTO> lookupData(@Valid DFHeader header, String lookupType) {
		try {
			Response<LookUpDTO> response= new Response<>();
			HttpEntity<Long> entity = new HttpEntity<>(BaseUtil.payload(header));
			Map<String, String> params = new HashMap<String, String>();
			params.put("lookupType", lookupType);
			URI uri = UriComponentsBuilder.fromUriString(lookUpUrl + "/lookUpValueByLookUpType/{lookupType}").buildAndExpand(params).toUri();
			ResponseEntity<List<LookUpDTO>> lookUpDTOs=restTemplate.exchange(uri, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<LookUpDTO>>() {
					});
			response.setData(lookUpDTOs.getBody());
			response.setMessage(successMessage);
			return response;
		} catch (RestClientException e) {
			throw new RuntimeException(e.getMessage());
		}
	}


	/**
	 *
	 * @param header DFHeader
	 * @param roleDetail RolePermissionDTO
	 * @return Integer
	 */
	@Override
	public Integer editRolePermission(DFHeader header, RolePermissionDTO roleDetail) {
		try {
			HttpEntity<RolePermissionDTO> entity = new HttpEntity<>(roleDetail, BaseUtil.payload(header));
			return restTemplate.exchange(new URI(rolesUrl + "/permissions"), HttpMethod.PUT, entity, Integer.class)
					.getBody();
			
		} catch (RestClientException | URISyntaxException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	/**
	 *
	 * @param header DFHeader
	 * @return Response<MenuHierarchyDTO>
	 * @throws Exception exception
	 */
	@Override
	public Response<MenuHierarchyDTO> getMenuHierarchy(DFHeader header) throws Exception {
		try {
			Response<MenuHierarchyDTO> response= new Response<>();
			HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
			ResponseEntity<List<MenuHierarchyDTO>> menuHierarchyDTOs = restTemplate.exchange(new URI( objectUrl+ "menuHierarchies"), HttpMethod.GET,
					entity, new ParameterizedTypeReference<List<MenuHierarchyDTO>>() {
					});
			response.setData(menuHierarchyDTOs.getBody());
			response.setMessage(successMessage);
			return response;
		} catch (RestClientException | URISyntaxException e) {
			log.error("Inside MdmServiceImpl :: getMenuHierarchy() " + e.getMessage());
			throw e;
		}
	}
    public Response<RoleDTO> getLastNUpdatedRoles(DFHeader header, int number) throws Exception {
        try {
            String object = rolesUri.replace("/", "");
            Response<RoleDTO> response = new Response<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                ResponseEntity<List<RoleDTO>> userDTOs = restTemplate.exchange(new URI( rolesUrl + "lastUpdated/"+
                                number), HttpMethod.GET,
                        entity, new ParameterizedTypeReference<List<RoleDTO>>() {
                        });
                response.setData(userDTOs.getBody());
                response.setMessage(successMessage);
            }
            return response;
        } catch (RestClientResponseException e) {
            InputStream body = new ByteArrayInputStream(e.getResponseBodyAsByteArray());
            log.error("Inside UserServiceImpl :: editUser() {}", e.getResponseBodyAsString());
            throw new ServiceException(body.toString(), "", 500);
        }
    }
}
