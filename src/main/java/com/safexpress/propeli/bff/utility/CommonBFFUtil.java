package com.safexpress.propeli.bff.utility;

import com.safexpress.propeli.security.util.AuthUtil;
import com.safexpress.propeli.servicebase.exception.ServiceException;
import com.safexpress.propeli.servicebase.model.DFHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
public class CommonBFFUtil {

    public static boolean isPermitted(DFHeader header, String object, AuthUtil.permissionTypeEnum permissionType) {
       /* boolean isPermittedFlag = AuthUtil.isPermitted(header, object, permissionType);
        isPermittedFlag = false;
        isPermittedFlag = true;
        if (!isPermittedFlag) {
            String description = "User Id -'" + header.getUserId() + "' is not authorized to access object: " + object +
                    " with " + permissionType.value() + " request";
            throw new ServiceException("Not-Authorized", description, 403);
        }*/
        return true;
    }

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
}
