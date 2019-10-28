package com.safexpress.propeli.bff.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.safexpress.propeli.bff.dto.Response;
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
import com.safexpress.propeli.bff.dto.EntityDTO;
import com.safexpress.propeli.bff.dto.ModuleObjectDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;
import com.safexpress.propeli.servicebase.util.BaseUtil;

@Service
public class ObjectSerivceImpl implements ObjectSerivce {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.mdmUserManagement.host}")
	String host;
	
	@Value("${service.mdmUserManagement.port}")
	String port;
	
	@Value("${service.mdmUserManagement.objects_uri}")
	String uri;

	@Value("${service.mdmUserManagement.objects_url}")
	String objectUrl;
	
	private final String url = host + port + uri;

	@Autowired
	Response response;

	/*
	 * @Override public ModuleObjectDTO saveObject(DFHeader header, ModuleObjectDTO
	 * moduleObject) throws Exception { try { HttpEntity<ModuleObjectDTO> entity =
	 * new HttpEntity<>(moduleObject, BaseUtil.payload(header)); return
	 * restTemplate.exchange(new URI(url + "/object"), HttpMethod.POST, entity,
	 * ModuleObjectDTO.class).getBody(); } catch (RestClientException |
	 * URISyntaxException e) { throw new RuntimeException(e.getMessage()); } }
	 */

	@Override
	public Response<ModuleObjectDTO> getAllObject(DFHeader header) throws Exception {
		try {
			HttpEntity<String> entity = new HttpEntity<>(BaseUtil.payload(header));
			ResponseEntity<List<ModuleObjectDTO>> moduleObjects = restTemplate.exchange(new URI(objectUrl),
					HttpMethod.GET, entity, new ParameterizedTypeReference<List<ModuleObjectDTO>>() {
					});
			response.setData(moduleObjects.getBody());
			response.setMessage("success");
			return response;
		} catch (RestClientException | URISyntaxException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * @Override public Integer updateObject(DFHeader header, ModuleObjectDTO
	 * moduleObject) throws Exception { try { HttpEntity<ModuleObjectDTO> entity =
	 * new HttpEntity<>(moduleObject, BaseUtil.payload(header)); return
	 * restTemplate.exchange(new URI(url + "/object"), HttpMethod.PUT, entity,
	 * Integer.class).getBody(); } catch (RestClientException | URISyntaxException
	 * e) { throw new RuntimeException(e.getMessage()); } }
	 */

	@Override
	public ModuleObjectDTO getObjectById(DFHeader header, String objectId) throws Exception {
		try {
			HttpEntity<Long> entity = new HttpEntity<>(BaseUtil.payload(header));
			Map<String, String> params = new HashMap<String, String>();
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
			Map<String, String> params = new HashMap<String, String>();
			params.put("objectName", objectName);
			URI uri = UriComponentsBuilder.fromUriString(objectUrl + "/byName/{objectName}").buildAndExpand(params)
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
			HttpEntity<Long> entity = new HttpEntity<>(BaseUtil.payload(header));
			Map<String, Long> params = new HashMap<String, Long>();
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

	/*
	 * @Override public int saveSection(DFHeader header, EntityDTO entityDto) throws
	 * Exception { try { HttpEntity<EntityDTO> entity = new HttpEntity<>(entityDto,
	 * BaseUtil.payload(header)); return restTemplate.exchange(new URI(url +
	 * "/section"), HttpMethod.POST, entity, Integer.class).getBody(); } catch
	 * (RestClientException | URISyntaxException e) { throw new
	 * RuntimeException(e.getMessage()); } }
	 */


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
