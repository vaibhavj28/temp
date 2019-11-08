package com.safexpress.propeli.bff.configuration;

import com.safexpress.propeli.security.util.AuthUtil;
import com.safexpress.propeli.servicebase.exception.ServiceException;
import com.safexpress.propeli.servicebase.model.DFHeader;
import org.springframework.stereotype.Component;

@Component
public class CommonBFFUtil {

    public static boolean isPermitted(DFHeader header, String object, AuthUtil.permissionTypeEnum permissionType) {
        boolean isPermittedFlag = AuthUtil.isPermitted(header, object, permissionType);
        isPermittedFlag = true;
        if (!isPermittedFlag) {
            String description = "User Id -'" + header.getUserId() + "' is not authorized to access object: " + object +
                    " with " + permissionType.value() + " request";
            throw new ServiceException("Not-Authorized", description, 403);
        }
        return true;
    }
}
