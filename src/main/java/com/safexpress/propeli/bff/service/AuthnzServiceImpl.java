package com.safexpress.propeli.bff.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.safexpress.propeli.bff.dto.CredentialDTO;
import com.safexpress.propeli.bff.dto.MenuHierarchyDTO;
import com.safexpress.propeli.bff.dto.TokenDTO;
import com.safexpress.propeli.bff.exception.CommonBffException;
import com.safexpress.propeli.servicebase.model.DFHeader;
import com.safexpress.propeli.servicebase.util.BaseUtil;

/**
 * @author Ajay Singh Negi	
 *
 */
@Service
public class AuthnzServiceImpl implements AuthnzService {

	private static final Logger logger= LoggerFactory.getLogger(AuthnzServiceImpl.class);
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${service.authnz.uri}")
	private String authnzServiceUri;

	@Value("${service.authnz.url}")
	private String authnzServiceUrl;
	
	
	@Override
	public TokenDTO getToken(DFHeader header, CredentialDTO credentialDTO) {
		
		String url= authnzServiceUrl + "/" + "login";
		logger.debug("requesting resouce {}", url);
		
		HttpHeaders httpHeaders = BaseUtil.payload(header);
		HttpEntity<CredentialDTO> httpEntity = new HttpEntity<>(credentialDTO,httpHeaders);
		ResponseEntity<TokenDTO> responseEntity= restTemplate.exchange(url, 
															HttpMethod.POST, 
															httpEntity,
															TokenDTO.class);		
		if(responseEntity.getStatusCode().is2xxSuccessful()) {
			return responseEntity.getBody();
		}else {
			throw new CommonBffException("Login failed for user. Could not get access-token");
		}
		
	}

	@Override
	public Cookie createCookie(String token) {

		Cookie cookie = new Cookie("access-token", token);
		//cookie.setDomain("safexpress.com");
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(-1); // cookie should delete when browser is closed
		cookie.setSecure(false); 
		return cookie;
	}
	
	public void logout(DFHeader header) {
		
		String url= authnzServiceUrl + "/" + "logout";
		logger.debug("requesting resouce {}", url);
		
		HttpHeaders httpHeaders = BaseUtil.payload(header);
		HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);
		ResponseEntity<Void> responseEntity= restTemplate.exchange(url, 
															HttpMethod.POST, 
															httpEntity, Void.class);		
		
		if(responseEntity.getStatusCode().is2xxSuccessful()) {
			// log here
		}else {
			throw new CommonBffException("Could not logout user");
		}
		
	}
	
	public Map<String, Object> getAllPermissionsForUser(DFHeader header) {
		
		String url= authnzServiceUrl + "/" + "permissions";
		logger.debug("requesting resouce {}", url);
		
		HttpHeaders httpHeaders = BaseUtil.payload(header);
		HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);
		ResponseEntity<Map<String, Object>> responseEntity= restTemplate.exchange(url, 
															HttpMethod.GET, 
															httpEntity,
															new ParameterizedTypeReference<Map<String,Object>>() {
															});
		
		return responseEntity.getBody();
	}
	
	public List<MenuHierarchyDTO> getUserMenu(DFHeader header) {
		
		String url= authnzServiceUrl + "/" + "menu";
		logger.debug("requesting resouce {}", url);
		
		HttpHeaders httpHeaders = BaseUtil.payload(header);
		HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);
		ResponseEntity<List<MenuHierarchyDTO>> responseEntity= restTemplate.exchange(url, 
															HttpMethod.GET, 
															httpEntity,
															new ParameterizedTypeReference<List<MenuHierarchyDTO>>() {
															});
		
		return responseEntity.getBody();
	}


}
