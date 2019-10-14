package com.safexpress.propeli.bff.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import com.safexpress.propeli.bff.dto.CredentialDTO;
import com.safexpress.propeli.bff.dto.MenuHierarchyDTO;
import com.safexpress.propeli.bff.dto.TokenDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;

/**
 * @author Ajay Singh Negi
 * 	
 *
 */
public interface AuthnzService{
	
	public TokenDTO getToken(DFHeader header, CredentialDTO credentialDTO);
	public Cookie createCookie(String token);
	public void logout(DFHeader header);
	
	public Map<String,Object> getAllPermissionsForUser(DFHeader header);
	
	public List<MenuHierarchyDTO> getUserMenu(DFHeader dfHeader);
}
