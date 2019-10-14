package com.safexpress.propeli.bff.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.safexpress.propeli.bff.configuration.Util;
import com.safexpress.propeli.bff.dto.CredentialDTO;
import com.safexpress.propeli.bff.dto.ResponseDTO;
import com.safexpress.propeli.bff.dto.TokenDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;

/**
 * @author Ajay Singh Negi	
 *
 */
@Service
public class AuthnzServiceImpl implements AuthnzService {

	private static final Logger logger= LoggerFactory.getLogger(AuthnzServiceImpl.class);
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${service.authnz-service.url}")
	String authnzServiceUrl;
	
	
	
	public TokenDTO getToken(DFHeader header, CredentialDTO credentialDTO) {
		
		String url= authnzServiceUrl + "/" + "login";
		logger.debug("requesting resouce {}", url);
		
		HttpHeaders httpHeaders = Util.payload(header);
		HttpEntity<CredentialDTO> httpEntity = new HttpEntity<>(credentialDTO,httpHeaders);
		ResponseEntity<TokenDTO> responseEntity= restTemplate.exchange(url, 
															HttpMethod.POST, 
															httpEntity,
															TokenDTO.class);		
		
		return responseEntity.getBody();
	}
	public void logout(DFHeader header) {
		
		String url= authnzServiceUrl + "/" + "logout";
		logger.debug("requesting resouce {}", url);
		
		HttpHeaders httpHeaders = Util.payload(header);
		HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);
		ResponseEntity<Void> responseEntity= restTemplate.exchange(url, 
															HttpMethod.POST, 
															httpEntity, Void.class);		
		
		if(responseEntity.getStatusCode().is2xxSuccessful()) {
			// log here
		}else {
			throw new RuntimeException("Could not logout user");
		}
		
	}
	
	public ResponseDTO getAllPermissionsForUser(DFHeader header) {
		return null;
	}
	
	public ResponseDTO getUserMenu(DFHeader dfHeader) {
		return null;
	}


}
