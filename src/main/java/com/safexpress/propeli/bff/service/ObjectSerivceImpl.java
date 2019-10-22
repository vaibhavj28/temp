package com.safexpress.propeli.bff.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.safexpress.propeli.bff.dto.BranchDTO;
import com.safexpress.propeli.bff.dto.EntityDTO;
import com.safexpress.propeli.bff.dto.LookUpDTO;
import com.safexpress.propeli.bff.dto.ModuleObjectDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;
import com.safexpress.propeli.servicebase.util.BaseUtil;

@Service
public class ObjectSerivceImpl implements ObjectSerivce {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.MdmUserManagement.mdm-url}")
	private String url;

	@Override
	public ModuleObjectDTO saveObject(DFHeader header, ModuleObjectDTO moduleObject) throws Exception {
		try {
			HttpEntity<ModuleObjectDTO> entity = new HttpEntity<>(moduleObject, BaseUtil.payload(header));
			return restTemplate.exchange(new URI(url + "/object"), HttpMethod.POST, entity, ModuleObjectDTO.class).getBody();
		} catch (RestClientException | URISyntaxException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<ModuleObjectDTO> getAllObject(DFHeader header) throws Exception {
		try {
			HttpEntity<String> entity = new HttpEntity<>(BaseUtil.payload(header));
			ResponseEntity<List<ModuleObjectDTO>> reponse = restTemplate.exchange(new URI(url + "/object"),
					HttpMethod.GET, entity, new ParameterizedTypeReference<List<ModuleObjectDTO>>() {
					});
			return reponse.getBody();
		} catch (RestClientException | URISyntaxException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Integer updateObject(DFHeader header, ModuleObjectDTO moduleObject) throws Exception {
		try {
			HttpEntity<ModuleObjectDTO> entity = new HttpEntity<>(moduleObject, BaseUtil.payload(header));
			return restTemplate.exchange(new URI(url + "/object"), HttpMethod.PUT, entity, Integer.class).getBody();
		} catch (RestClientException | URISyntaxException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public ModuleObjectDTO getObjectById(DFHeader header, String objectId) throws Exception {
		try {
			HttpEntity<Long> entity = new HttpEntity<>(BaseUtil.payload(header));
			Map<String, String> params = new HashMap<String, String>();
			params.put("objectId", objectId);
			URI uri = UriComponentsBuilder.fromUriString(url + "/object/byId/{objectId}").buildAndExpand(params)
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
			Map<String, String> params = new HashMap<String, String>();
			params.put("objectName", objectName);
			URI uri = UriComponentsBuilder.fromUriString(url + "/object/byName/{objectName}").buildAndExpand(params)
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
	public List<EntityDTO> getSectionList(DFHeader header, long menuId) throws Exception {
		try {
			HttpEntity<Long> entity = new HttpEntity<>(BaseUtil.payload(header));
			Map<String, Long> params = new HashMap<String, Long>();
			params.put("menuId", menuId);
			URI uri = UriComponentsBuilder.fromUriString(url + "/sectionList/{menuId}").buildAndExpand(params).toUri();
			ResponseEntity<List<EntityDTO>> reponse = restTemplate.exchange(uri, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<EntityDTO>>() {
					});
			return reponse.getBody();
		} catch (RestClientException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public int saveSection(DFHeader header, EntityDTO entityDto) throws Exception {
		try {
			HttpEntity<EntityDTO> entity = new HttpEntity<>(entityDto, BaseUtil.payload(header));
			return restTemplate.exchange(new URI(url + "/section"), HttpMethod.POST, entity, Integer.class).getBody();
		} catch (RestClientException | URISyntaxException e) {
			throw new RuntimeException(e.getMessage());
		}
	}


//	@Override
//	public List<AttributeDTO> getAttributeList(@Valid DFHeader header, long objectId) {
//		try {
//			HttpEntity<Long> entity = new HttpEntity<>(util.payload(header));
//			Map<String, Long> params = new HashMap<String, Long>();
//			params.put("objectId", objectId);
//			URI uri = UriComponentsBuilder.fromUriString(url + "/object/attribute/{objectId}").buildAndExpand(params)
//					.toUri();
//			return restTemplate.exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<List<AttributeDTO>>(){}).getBody();
//		} catch (RestClientException e) {
//			throw new RuntimeException(e.getMessage());
//		}
//	}

}
