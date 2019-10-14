package com.safexpress.propeli.bff.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.safexpress.propeli.servicebase.model.DFHeader;

public class Util {
	
	public static HttpHeaders payload(DFHeader header) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("journeyId", header.getJourneyId());
		headers.set("Authorization", header.getAuthorization());
		headers.set("branchCode", header.getBranchCode());
		headers.set("userId", header.getUserId());
		headers.set("correlationId", header.getCorrelationId());
		headers.set("originUserType", header.getOriginUserType());
		return headers;
	}

}
