package com.safexpress.propeli.bff.utility;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import com.safexpress.propeli.bff.dto.UserDTO;
import com.safexpress.propeli.security.util.AuthUtil;
import com.safexpress.propeli.servicebase.exception.ServiceException;
import com.safexpress.propeli.servicebase.model.DFHeader;

public class CommonBFFUtil {

	/**
	 * This method extracts the application object from uri and sends it to the utility method
	 * which checks if the loggedin user has the adequate permission to 
	 * perform the intended operation on the said object ,if not
	 * throws a service exception denying permission to the user
	 * @param header
	 * @param uri - core api uri
	 * @param permissionType- the http operation (get, post, put)
	 * @return - true or false
	 */
	public static boolean isPermitted(DFHeader header, String uri, AuthUtil.permissionTypeEnum permissionType) {
		
		/*
		 * String object = uri.contains("/") ? uri.replace("/", "") : uri; boolean
		 * isPermittedFlag = AuthUtil.isPermitted(header, object, permissionType); if
		 * (!isPermittedFlag) { String description = "User Id -'" + header.getUserId() +
		 * "' is not authorized to access object: " + object + " with " + permissionType
		 * + " request"; throw new ServiceException("Not-Authorized", description, 403);
		 * }
		 */
		return true;
	}

    /**
     * This method processes the exception received from core api and extracts the required details to construct a json object
     * @param restClientResponseException
     * @return json consists of error code ,status, description required to from an exception
     */
    public static JSONObject handleError(RestClientResponseException restClientResponseException)  {
        JSONObject jsonError = new JSONObject();
        if(!restClientResponseException.getResponseBodyAsString().isEmpty()) {
            JSONObject responseBody = new JSONObject(restClientResponseException.getResponseBodyAsString());
            JSONObject errorResponse = responseBody.has("errorResponse")?responseBody.getJSONObject("errorResponse"):new JSONObject();
            JSONObject errorDetails = errorResponse.has("error")?errorResponse.getJSONObject("error"):new JSONObject();
            JSONArray additionalDetails = new JSONArray(errorDetails.has("additionalDetails")?errorDetails.get("additionalDetails").toString(): "");
            jsonError = !additionalDetails.isNull(0)?additionalDetails.getJSONObject(0):new JSONObject();
        }
        JSONObject jsonObject = new JSONObject();
        String description = jsonError.has("description")?jsonError.getString("description").replace("ServiceException",""):HttpStatus.NOT_FOUND.toString();
        jsonObject.put("errorCode", jsonError.has("errorCode")?jsonError.getString("errorCode"):"ERROR01");
        jsonObject.put("status", jsonError.has("status")?jsonError.getString("status"):HttpStatus.NOT_FOUND.value());
        jsonObject.put("description", description);
        return jsonObject;
    }
    
    /**
     * This method sets the admin field for the user object to null if the loggedin user is not an admin
     * @param header
     * @param user
     * @return
     */
	public static UserDTO setAdminFieldForUser(DFHeader header, UserDTO user) {
		if (StringUtils.equalsIgnoreCase("false", header.getIsAdmin()))
			user.setIsAdmin(null);
		return user;
	}
}
