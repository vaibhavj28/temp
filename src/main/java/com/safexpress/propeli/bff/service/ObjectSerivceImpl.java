package com.safexpress.propeli.bff.service;

import com.safexpress.propeli.bff.configuration.CommonBFFUtil;
import com.safexpress.propeli.bff.dto.*;
import com.safexpress.propeli.security.util.AuthUtil;
import com.safexpress.propeli.servicebase.model.DFHeader;
import com.safexpress.propeli.servicebase.util.BaseUtil;
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

import java.lang.ref.Reference;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ObjectSerivceImpl implements ObjectSerivce {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.mdmUserManagement.objects_url}")
	String objectUrl;

    @Value("${service.mdmUserManagement.objects_uri}")
    String objectUri;

    @Value("${message.success}")
    private
    String successMessage;

	@Value("${service.lookUpNotepadCommandmentService.look_up_url}")
	String lookUpUrl;

	@Override
	public Response<ModuleObjectDTO> getAllObject(DFHeader header) throws Exception {
		try {
			Response<ModuleObjectDTO> response = new Response<>();
 			HttpEntity<String> entity = new HttpEntity<>(BaseUtil.payload(header));
			ResponseEntity<List<ModuleObjectDTO>> moduleObjects = restTemplate.exchange(new URI(objectUrl),
					HttpMethod.GET, entity, new ParameterizedTypeReference<List<ModuleObjectDTO>>() {
					});

			List<LookUpMDMDTO> lookUpChannels = getLookUpByChannelId(header, entity, moduleObjects);
			response.setData(moduleObjects.getBody());
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
	 *
	 * @param header DFHeader
	 * @param entity HttpEntity<String> entity
	 * @param moduleObjects ResponseEntity<List<ModuleObjectDTO>>
	 * @return List<LookUpMDMDTO>
	 * @throws URISyntaxException exception
	 */
	private List<LookUpMDMDTO> getLookUpByChannelId(DFHeader header, HttpEntity<String> entity, ResponseEntity<List<ModuleObjectDTO>> moduleObjects) throws URISyntaxException {
		List<LookUpMDMDTO> lookUpChannels = new ArrayList<>();
		String object = "lookUp";
		if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
			for(ModuleObjectDTO moduleObjectDTO: moduleObjects.getBody()) {
				LookUpMDMDTO lookUpMDMDTO = restTemplate.exchange(
						new URI(lookUpUrl + "/lookUpValuesById/" + moduleObjectDTO.getChannelId()), HttpMethod.GET, entity,
						new ParameterizedTypeReference<LookUpMDMDTO>() {
						}).getBody();
				lookUpChannels.add(lookUpMDMDTO);
			}
		}
		return lookUpChannels.stream().filter(distinctByKey(LookUpMDMDTO::getId)).collect(Collectors.toList());
	}

	@Override
	public ModuleObjectDTO getObjectById(DFHeader header, String objectId) throws Exception {
		try {
			HttpEntity<Long> entity = new HttpEntity<>(BaseUtil.payload(header));
			Map<String, String> params = new HashMap<>();
			params.put("objectId", objectId);
			URI uri = UriComponentsBuilder.fromUriString(objectUrl + "/byId/{objectId}").buildAndExpand(params)
					.toUri();
			return restTemplate.exchange(uri, HttpMethod.GET, entity, ModuleObjectDTO.class).getBody();
		} catch (RestClientException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<ModuleObjectDTO> getObjectByName(DFHeader header, String objectName) throws Exception {
		try {
			HttpEntity<Long> entity = new HttpEntity<>(BaseUtil.payload(header));
			Map<String, String> params = new HashMap<>();
			params.put("objectName", objectName);
			URI uri = UriComponentsBuilder.fromUriString(objectUrl + "/name/{objectName}").buildAndExpand(params)
					.toUri();
			ResponseEntity<List<ModuleObjectDTO>> reponse = restTemplate.exchange(uri, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<ModuleObjectDTO>>() {
					});
			return reponse.getBody();
		} catch (RestClientException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Response<EntityDTO> getSectionList(DFHeader header, long menuId) throws Exception {
		try {
			Response<EntityDTO> response = new Response<>();
			HttpEntity<Long> entity = new HttpEntity<>(BaseUtil.payload(header));
			Map<String, Long> params = new HashMap<>();
			params.put("menuId", menuId);
			URI uri = UriComponentsBuilder.fromUriString(objectUrl + "/sections/{menuId}").buildAndExpand(params).toUri();
			ResponseEntity<List<EntityDTO>> entityList = restTemplate.exchange(uri, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<EntityDTO>>() {
					});
			response.setData(entityList.getBody());
			response.setMessage("success");

			return response;
		} catch (RestClientException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

    /**
     *
     * @param header
     * @param number
     * @return
     * @throws Exception
     */
    public Response<ModuleObjectDTO> getLastNUpdatedObjects(DFHeader header, int number) throws Exception {
        try {
            String object = objectUri.replace("/", "");
            Response<ModuleObjectDTO> response = new Response<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                ResponseEntity<List<ModuleObjectDTO>> objects = restTemplate.exchange(new URI(objectUrl + "lastUpdated/"+
                                number), HttpMethod.GET,
                        entity, new ParameterizedTypeReference<List<ModuleObjectDTO>>() {
                        });

				List<LookUpMDMDTO> lookUpChannels = getLookUpMDMDTOS(header, entity, objects);
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

	private List<LookUpMDMDTO> getLookUpMDMDTOS(DFHeader header, HttpEntity<DFHeader> entity, ResponseEntity<List<ModuleObjectDTO>> objects) throws URISyntaxException {
		List<LookUpMDMDTO> lookUpChannels = new ArrayList<>();
		String lookUpObject = "lookUp";
		if (CommonBFFUtil.isPermitted(header, lookUpObject, AuthUtil.permissionTypeEnum.GET)) {
			for(ModuleObjectDTO moduleObjectDTO: objects.getBody()) {
				LookUpMDMDTO lookUpMDMDTO = restTemplate.exchange(
						new URI(lookUpUrl + "/lookUpValuesById/" + moduleObjectDTO.getChannelId()), HttpMethod.GET, entity,
						new ParameterizedTypeReference<LookUpMDMDTO>() {
						}).getBody();
				if(!lookUpChannels.contains(lookUpMDMDTO.getId()))
				lookUpChannels.add(lookUpMDMDTO);
			}
		}
		return lookUpChannels.stream().filter(distinctByKey(LookUpMDMDTO::getId)).collect(Collectors.toList());
	}


	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}
}
