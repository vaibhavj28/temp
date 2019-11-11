package com.safexpress.propeli.bff.service;

import com.safexpress.propeli.bff.constants.CommonBFFConstant;
import com.safexpress.propeli.bff.utility.CommonBFFUtil;
import com.safexpress.propeli.bff.dto.*;
import com.safexpress.propeli.security.util.AuthUtil;
import com.safexpress.propeli.servicebase.exception.ServiceException;
import com.safexpress.propeli.servicebase.model.DFHeader;
import com.safexpress.propeli.servicebase.util.BaseUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ObjectSerivceImpl implements ObjectSerivce {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.mdmUserManagement.objects_url}")
    private String objectUrl;

    @Value("${service.mdmUserManagement.objects_uri}")
    private String objectUri;

    @Value("${message.success}")
    private String successMessage;

    @Value("${service.lookUpNotepadCommandmentService.look_up_url}")
    private String lookUpUrl;


    /**
     * This method will return the list of all the objects from the repo
     *
     * @param header DFHeader
     * @return Response<ModuleObjectDTO>
     * @throws Exception exception
     */
    @Override
    public Response<ModuleObjectDTO> getAllObject(DFHeader header) throws Exception {
        try {
            String object = objectUri.replace("/", "");
            Response<ModuleObjectDTO> response = new Response<>();
            HttpEntity<String> entity = new HttpEntity<>(BaseUtil.payload(header));
            List<ModuleObjectDTO> moduleObjects = new ArrayList<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                moduleObjects = restTemplate.exchange(new URI(objectUrl),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<ModuleObjectDTO>>() {
                        }).getBody();
            }
            List<LookUpMDMDTO> lookUpChannels = getLookUpByChannelType(header, entity);
            response.setData(moduleObjects);
            ReferenceDTO referenceDTO = new ReferenceDTO();
            referenceDTO.setCategoryList(lookUpChannels);
            response.setRefernceList(referenceDTO);
            response.setMessage("success");
            return response;
        } catch (RestClientException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * This method will return the lookUp by channel Type
     *
     * @param header DFHeader
     * @param entity HttpEntity<String> entity
     * @return List<LookUpMDMDTO>
     * @throws URISyntaxException exception
     */
    private List<LookUpMDMDTO> getLookUpByChannelType(DFHeader header, HttpEntity<?> entity) throws URISyntaxException {
        List<LookUpMDMDTO> lookUpMDMDTO = new ArrayList<>();
        String object = "lookUp";
        if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
            lookUpMDMDTO = restTemplate.exchange(
                    new URI(lookUpUrl + CommonBFFConstant.GET_LOOK_UP_DETAILS_URI + "CHANNEL"), HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<LookUpMDMDTO>>() {
                    }).getBody();
        }
        return lookUpMDMDTO;
    }

    /**
     * This method will return the module by objectId
     *
     * @param header   DFHeader
     * @param objectId String
     * @return ModuleObjectDTO
     * @throws Exception
     */

    @Override
    public ModuleObjectDTO getObjectById(DFHeader header, String objectId) throws Exception {
        try {
            String object = objectUri.replace("/", "");
            HttpEntity<Long> entity = new HttpEntity<>(BaseUtil.payload(header));
            Map<String, String> params = new HashMap<>();
            params.put("objectId", objectId);
            ModuleObjectDTO moduleObjectDTO = new ModuleObjectDTO();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                URI uri = UriComponentsBuilder.fromUriString(objectUrl + "/byId/{objectId}").buildAndExpand(params)
                        .toUri();
                moduleObjectDTO = restTemplate.exchange(uri, HttpMethod.GET, entity, ModuleObjectDTO.class).getBody();
            }
            return moduleObjectDTO;
        } catch (RestClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * This method will return the look up by object Namez
     *
     * @param header     DFHeader
     * @param objectName String
     * @return Response<ModuleObjectDTO>
     * @throws Exception excpetion
     */
    @Override
    public Response<ModuleObjectDTO> getObjectByName(DFHeader header, String objectName) throws Exception {
        try {
            String object = objectUri.replace("/", "");
            Response<ModuleObjectDTO> response = new Response<>();
            HttpEntity<String> entity = new HttpEntity<>(BaseUtil.payload(header));
            Map<String, String> params = new HashMap<>();
            List<ModuleObjectDTO> moduleObjectDTOS = new ArrayList<>();
            params.put("objectName", objectName);
            URI uri = UriComponentsBuilder.fromUriString(objectUrl + "/name/{objectName}").buildAndExpand(params)
                    .toUri();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                moduleObjectDTOS = restTemplate.exchange(uri, HttpMethod.GET, entity,
                        new ParameterizedTypeReference<List<ModuleObjectDTO>>() {
                        }).getBody();
            }
            List<LookUpMDMDTO> lookUpChannels = getLookUpByChannelType(header, entity);
            ReferenceDTO referenceDTO = new ReferenceDTO();
            referenceDTO.setCategoryList(lookUpChannels);
            response.setRefernceList(referenceDTO);
            response.setData(moduleObjectDTOS);
            response.setMessage(successMessage);
            return response;
        } catch (RestClientResponseException e) {
            JSONObject error = CommonBFFUtil.handleError(e);
            String description = error.get("description").toString().replace("ServiceException:", "");
            throw new ServiceException("ERROR01", description, Integer.parseInt(error.has("status") ? (error.get("status").toString()) : "500"));
        }
    }


    /**
     * This method will return the sections
     *
     * @param header DFHeader
     * @param menuId long
     * @return Response<EntityDTO>
     * @throws Exception exception
     */

    @Override
    public Response<EntityDTO> getSectionList(DFHeader header, long menuId) throws Exception {
        try {
            String object = objectUri.replace("/", "");
            Response<EntityDTO> response = new Response<>();
            HttpEntity<Long> entity = new HttpEntity<>(BaseUtil.payload(header));
            Map<String, Long> params = new HashMap<>();
            params.put("menuId", menuId);
            List<EntityDTO> entityList = new ArrayList<>();
            URI uri = UriComponentsBuilder.fromUriString(objectUrl + "/sections/{menuId}").buildAndExpand(params).toUri();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                entityList = restTemplate.exchange(uri, HttpMethod.GET, entity,
                        new ParameterizedTypeReference<List<EntityDTO>>() {
                        }).getBody();
            }
            response.setData(entityList);
            response.setMessage("success");

            return response;
        } catch (RestClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * This method will return the last N updated objects
     *
     * @param header DFHeader
     * @param number int
     * @return Response<ModuleObjectDTO>
     * @throws Exception exception
     */
    public Response<ModuleObjectDTO> getLastNUpdatedObjects(DFHeader header, int number) throws Exception {
        try {
            String object = objectUri.replace("/", "");
            Response<ModuleObjectDTO> response = new Response<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                ResponseEntity<List<ModuleObjectDTO>> objects = restTemplate.exchange(new URI(objectUrl + "lastUpdated/" +
                                number), HttpMethod.GET,
                        entity, new ParameterizedTypeReference<List<ModuleObjectDTO>>() {
                        });
                List<LookUpMDMDTO> lookUpChannels = getLookUpByChannelType(header, entity);
                ReferenceDTO referenceDTO = new ReferenceDTO();
                referenceDTO.setCategoryList(lookUpChannels);
                response.setRefernceList(referenceDTO);
                response.setData(objects.getBody());
                response.setMessage(successMessage);
            }
            return response;
        } catch (RestClientResponseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
