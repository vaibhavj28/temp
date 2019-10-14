package com.safexpress.propeli.bff.service;

import com.safexpress.propeli.bff.dto.CredentialDTO;
import com.safexpress.propeli.bff.dto.ResponseDTO;
import com.safexpress.propeli.bff.dto.TokenDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;

/**
 * @author Ajay Singh Negi
 * 	
 *
 */
public interface AuthnzService{
	
	public TokenDTO getToken(DFHeader header, CredentialDTO credentialDTO);
	public void logout(DFHeader header);
	
	public ResponseDTO getAllPermissionsForUser(DFHeader header);
	
	public ResponseDTO getUserMenu(DFHeader dfHeader);
	
	
}
