package com.safexpress.propeli.bff.service;

import com.safexpress.propeli.bff.constants.CommonBFFConstant;
import com.safexpress.propeli.bff.utility.CommonBFFUtil;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

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

    @Value("${service.mdmUserManagement.objects_uri}")
    private String objectUri;

    @Value("${service.mdmUserManagement.roles_uri}")
    private String rolesUri;

    @Value("${message.success}")
    private
    String successMessage;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * This method will return all the roles
     *
     * @param header DFHeader
     * @return Response<RoleDTO>
     * @throws Exception exception
     */
    @Override
    public Response<RoleDTO> getAllRoles(DFHeader header) throws Exception {
        try {
            String object = rolesUri.replace("/", "");
            Response<RoleDTO> response = new Response<>();
            HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
            List<RoleDTO> roles = new ArrayList<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                roles = restTemplate.exchange(new URI(rolesUrl), HttpMethod.GET,
                        entity, new ParameterizedTypeReference<List<RoleDTO>>() {
                        }).getBody();
            }
            response.setData(roles);
            response.setMessage(successMessage);
            return response;
        } catch (RestClientException | URISyntaxException e) {
            log.error("Inside MdmServiceImpl :: getAllRoles() ", e);
            throw e;
        }
    }


    /**
     * This method will return the role permissions on role Id
     *
     * @param header DFHeader
     * @param roleId long
     * @return Response<RolePermissionDTO>
     * @throws Exception Exception
     */
    @Override
    public Response<RolePermissionDTO> getRolePermission(DFHeader header, long roleId) throws Exception {

        try {
            String object = rolesUri.replace("/", "");
            Response<RolePermissionDTO> response = new Response<>();
            HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
            RolePermissionDTO rolePermissionDTO = new RolePermissionDTO();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                rolePermissionDTO = restTemplate.exchange(new URI(rolesUrl + "permissions/" + roleId), HttpMethod.GET, entity,
                        RolePermissionDTO.class).getBody();
            }
            List<LookUpMDMDTO> lookUpChannels = getLookUpByChannel(header, entity);
            ReferenceDTO referenceDTO = new ReferenceDTO();
            referenceDTO.setCategoryList(lookUpChannels);
            List<RolePermissionDTO> rolePermission = new ArrayList<>();
            rolePermission.add(rolePermissionDTO);
            response.setRefernceList(referenceDTO);
            response.setData(rolePermission);
            response.setMessage(successMessage);
            return response;

        } catch (RestClientException | URISyntaxException e) {
            log.error("Inside MdmServiceImpl :: getRolePermission() " + e.getMessage());
            throw e;
        }
    }

    /**
     * This method will get the master data of look up channels
     *
     * @param header DFHeader
     * @param entity HttpEntity<DFHeader>
     * @return List<LookUpMDMDTO>
     * @throws URISyntaxException URISyntaxException
     */
    private List<LookUpMDMDTO> getLookUpByChannel(DFHeader header, HttpEntity<DFHeader> entity) throws URISyntaxException {
        List<LookUpMDMDTO> lookUpChannels = new ArrayList<>();
        String lookUpObject = "lookUp";
        if (CommonBFFUtil.isPermitted(header, lookUpObject, AuthUtil.permissionTypeEnum.GET)) {
            lookUpChannels = restTemplate.exchange(
                    new URI(lookUpUrl + CommonBFFConstant.GET_LOOK_UP_DETAILS_URI + CommonBFFConstant.LOOK_UP_CHANNEL), HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<LookUpMDMDTO>>() {
                    }).getBody();
        }
        return lookUpChannels;
    }

    /**
     * This method will add a role with permission
     *
     * @param header     DFHeader
     * @param roleDetail RolePermissionDTO
     * @return ResponseDTO<Integer>
     * @throws Exception exception
     */
    @Override
    public ResponseDTO<Integer> addRolePermission(DFHeader header, RolePermissionDTO roleDetail) throws Exception {
        try {
            String object = rolesUri.replace("/", "");
            HttpEntity<RoleDTO> entity = new HttpEntity<>(roleDetail.getRoleDto(), BaseUtil.payload(header));
            ResponseDTO<Integer> response = new ResponseDTO<>();
            int status = 0;
            String data = null;
            RoleDTO role = new RoleDTO();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.POST)) {
                role = restTemplate.exchange(new URI(rolesUrl), HttpMethod.POST, entity, RoleDTO.class)
                        .getBody();
            }
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
     * This method will add permissions to the role
     *
     * @param header     DFHeader
     * @param roleDetail RolePermissionDTO
     * @return int
     * @throws Exception exception
     */
    private int addPermission(DFHeader header, RolePermissionDTO roleDetail) throws Exception {
        try {
            String object = rolesUri.replace("/", "");
            HttpEntity<RolePermissionDTO> rolePermissionEntity = new HttpEntity<>(roleDetail, BaseUtil.payload(header));
            int result = 0;
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                result = restTemplate.exchange(new URI(rolesUrl + "permissions"), HttpMethod.POST, rolePermissionEntity, Integer.class).getBody();
            }
            return result;
        } catch (RestClientException e) {
            log.error("Inside MdmServiceImpl :: addPermission() " + e.getMessage());
            return 0;
        }
    }

    /**
     * This method will fetch lookUp data based on look up type
     *
     * @param header     DFHeader
     * @param lookupType String
     * @return Response<LookUpDTO>
     */
    @Override
    public Response<LookUpDTO> lookupData(@Valid DFHeader header, String lookupType) {
        try {
            String object = "lookUp";
            Response<LookUpDTO> response = new Response<>();
            HttpEntity<Long> entity = new HttpEntity<>(BaseUtil.payload(header));
            Map<String, String> params = new HashMap<String, String>();
            params.put("lookupType", lookupType);
            List<LookUpDTO> lookUpDTOs = new ArrayList<>();
            URI uri = UriComponentsBuilder.fromUriString(lookUpUrl + CommonBFFConstant.GET_LOOK_UP_DETAILS_URI
                    + "{lookupType}").buildAndExpand(params).toUri();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                lookUpDTOs = restTemplate.exchange(uri, HttpMethod.GET, entity,
                        new ParameterizedTypeReference<List<LookUpDTO>>() {
                        }).getBody();
            }
            response.setData(lookUpDTOs);
            response.setMessage(successMessage);
            return response;
        } catch (RestClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * This method will edit the role permissions
     *
     * @param header     DFHeader
     * @param roleDetail RolePermissionDTO
     * @return Integer
     */
    @Override
    public Integer editRolePermission(DFHeader header, RolePermissionDTO roleDetail) {
        try {
            String object = rolesUri.replace("/", "");
            HttpEntity<RolePermissionDTO> entity = new HttpEntity<>(roleDetail, BaseUtil.payload(header));
            Integer result = null;
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                result = restTemplate.exchange(new URI(rolesUrl + "/permissions"), HttpMethod.PUT, entity, Integer.class)
                        .getBody();
            }
            return result;

        } catch (RestClientException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * This method will return the menu hierarchy
     *
     * @param header DFHeader
     * @return Response<MenuHierarchyDTO>
     * @throws Exception exception
     */
    @Override
    public Response<MenuHierarchyDTO> getMenuHierarchy(DFHeader header) throws Exception {
        try {
            String object = objectUri.replace("/", "");
            Response<MenuHierarchyDTO> response = new Response<>();
            HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
            List<MenuHierarchyDTO> menuHierarchyDTOs = new ArrayList<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                menuHierarchyDTOs = restTemplate.exchange(new URI(objectUrl + "menuHierarchies"), HttpMethod.GET,
                        entity, new ParameterizedTypeReference<List<MenuHierarchyDTO>>() {
                        }).getBody();
            }
            response.setData(menuHierarchyDTOs);
            response.setMessage(successMessage);
            return response;
        } catch (RestClientException | URISyntaxException e) {
            log.error("Inside MdmServiceImpl :: getMenuHierarchy() " + e.getMessage());
            throw e;
        }
    }

    /**
     * This method will return the last N updated roles
     *
     * @param header DFHeader
     * @param number int
     * @return Response<RoleDTO>
     * @throws Exception excpetion
     */
    public Response<RoleDTO> getLastNUpdatedRoles(DFHeader header, int number) throws Exception {
        try {
            String object = rolesUri.replace("/", "");
            Response<RoleDTO> response = new Response<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                ResponseEntity<List<RoleDTO>> userDTOs = restTemplate.exchange(new URI(rolesUrl + "lastUpdated/" +
                                number), HttpMethod.GET,
                        entity, new ParameterizedTypeReference<List<RoleDTO>>() {
                        });
                response.setData(userDTOs.getBody());
                response.setMessage(successMessage);
            }
            return response;
        } catch (RestClientResponseException e) {
            InputStream body = new ByteArrayInputStream(e.getResponseBodyAsByteArray());
            log.error("Inside UserServiceImpl :: getLastNUpdatedRoles() {}", e.getResponseBodyAsString());
            throw new ServiceException(body.toString(), "", 500);
        }
    }

    /**
     * @param keyExtractor Function<? super T, ?>
     * @param <T>          Type
     * @return <T> Predicate<T>
     */

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
