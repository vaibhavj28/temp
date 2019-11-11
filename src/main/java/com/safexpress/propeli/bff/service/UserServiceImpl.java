package com.safexpress.propeli.bff.service;

import com.safexpress.propeli.bff.constants.CommonBFFConstant;
import com.safexpress.propeli.bff.dto.*;
import com.safexpress.propeli.bff.utility.CommonBFFUtil;
import com.safexpress.propeli.security.util.AuthUtil;
import com.safexpress.propeli.servicebase.exception.ServiceException;
import com.safexpress.propeli.servicebase.model.DFHeader;
import com.safexpress.propeli.servicebase.util.BaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.branchService.branch_uri}")
    private
    String branchUri;

    @Value("${service.branchService.branch_url}")
    String branchUrl;

    @Value("${service.lookUpNotepadCommandmentService.look_up_url}")
    String lookUpUrl;

    @Value("${service.mdmUserManagement.users_url}")
    String usersUrl;

    @Value("${service.mdmUserManagement.users_uri}")
    String usersUri;

    @Autowired
    UserBranchMappingDTO userBranchMappingDTO;

    @Value("${message.success}")
    private
    String successMessage;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * @Override public String addUser(DFHeader header, UserDTO newUser) throws Exception {
     * <p>
     * if(CollectionUtils.isEmpty(newUser.getUserRoles())){
     * throw new ValidationException().businessErrorCode("1005")
     * .businessErrorMessage("User Should be created with atleast one role")
     * .error("User Id", "002", newUser.getUserId() + " " + "Does Not Have A Role Associated With It");
     * }
     * else
     * return saveUser(header,newUser);
     * <p>
     * }
     **/

    /**
     * This method will save the details of new user
     *
     * @param header  DFHeader
     * @param newUser UserDTO
     * @return String
     * @throws Exception exception
     */
    public String saveUser(DFHeader header, UserDTO newUser) throws Exception {
        try {
            if (!Boolean.parseBoolean(header.getIsAdmin())) {
                newUser.setIsAdmin(null);
            }
            Map<String, String> response = new HashMap<>();
            String object = usersUri.replace("/", "");
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.POST)) {
                HttpEntity<UserDTO> entity = new HttpEntity<>(newUser, BaseUtil.payload(header));
                response = restTemplate.exchange(new URI(usersUrl), HttpMethod.POST, entity, Map.class).getBody();
                return response.get("responseMessage");
            }
            return "";
        } catch (RestClientResponseException e) {
            log.error("Inside UserServiceImpl :: saveUser()" + e.getResponseBodyAsString());
            String errorpayload = e.getResponseBodyAsString();
            throw e;
        }
    }

    /**
     * This method will get the user details based on user id
     *
     * @param header DFHeader
     * @param userId String
     * @return Response<UserDTO>
     * @throws Exception exception
     */
    @Override
    public Response<UserDTO> getUser(DFHeader header, String userId) throws Exception {
        try {
            String object = usersUri.replace("/", "");
            HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
            Optional<UserDTO> userDTO = Optional.of(new UserDTO());
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                userDTO = Optional.ofNullable(restTemplate.exchange(new URI(usersUrl + userId), HttpMethod.GET, entity, UserDTO.class)
                        .getBody());
            }
            Response<UserDTO> responseDTO = new Response<>();
            List<UserDTO> userList = new ArrayList<>();
            if (!Boolean.parseBoolean(header.getIsAdmin())) {
                userDTO.ifPresent(dto -> dto.setIsAdmin(null));
            }
            userDTO.ifPresent(userList::add);
            responseDTO.setData(userList);
            List<LookUpMDMDTO> lookUpResponse = getLookUpDetails(header, entity);
            BranchDTO branchDTO = getBranchDetails(header, entity, userDTO.get());
            ReferenceDTO referenceDTO = new ReferenceDTO();
            referenceDTO.setCategoryList(lookUpResponse);
            List<BranchDTO> branchDTOs = new ArrayList<>();
            branchDTOs.add(branchDTO);
            referenceDTO.setBranch(branchDTOs);
            responseDTO.setRefernceList(referenceDTO);
            responseDTO.setMessage(successMessage);
            return responseDTO;

        } catch (RestClientResponseException e) {
            log.error("Inside UserServiceImpl :: getUser()" + e.getResponseBodyAsString());
            throw e;
        }
    }


    /**
     * This method will call the branch micro service to get the master data of branches
     *
     * @param header  DFHeader
     * @param entity  HttpEntity<DFHeader>
     * @param userDTO UserDTO
     * @return BranchDTO
     * @throws URISyntaxException Exception
     */
    private BranchDTO getBranchDetails(DFHeader header, HttpEntity<DFHeader> entity, UserDTO userDTO) throws URISyntaxException {
        BranchDTO branchDTO = null;
        String object = branchUri.replace("/", "");
        if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
            ResponseEntity<BranchDTO> response = restTemplate.exchange(new URI(branchUrl + CommonBFFConstant.GET_BRANCH_CODE_URI + userDTO.getDefaultBranch().getBranchCode()),
                    HttpMethod.GET, entity, BranchDTO.class);
            branchDTO = response.getBody();
        }
        return branchDTO;
    }

    /**
     * This method will call the micro service of lookService to show the master data of look up details
     *
     * @param header header
     * @param entity HttpEntity<DFHeader>
     * @return ResponseEntity<List < LookUpMDMDTO>>
     * @throws URISyntaxException exception
     */
    private List<LookUpMDMDTO> getLookUpDetails(DFHeader header, HttpEntity<DFHeader> entity) throws URISyntaxException {
        List<LookUpMDMDTO> lookUpResponse = new ArrayList<>();
        String object = "lookUp";
        if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
            lookUpResponse = restTemplate.exchange(
                    new URI(lookUpUrl + CommonBFFConstant.GET_LOOK_UP_DETAILS_URI + CommonBFFConstant.USER_CATEGORY_CHANNEL), HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<LookUpMDMDTO>>() {
                    }).getBody();
        }
        return lookUpResponse;
    }


    /**
     * This method will return the privilege branches
     *
     * @param header DFHeader
     * @param userId String
     * @return Response<UserBranchMappingDTO>
     * @throws Exception exception
     */
    @Override
    public Response<UserBranchMappingDTO> getPrevilegeBranches(DFHeader header, String userId) throws Exception {
        try {
            String object = usersUri.replace("/", "");
            HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
            List<UserBranchMappingDTO> userBranchMappingDTOs = new ArrayList<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                userBranchMappingDTOs = restTemplate.exchange(
                        new URI(usersUrl + userId + CommonBFFConstant.PREVILLEGE_BRANCH_CODES_URI), HttpMethod.GET, entity,
                        new ParameterizedTypeReference<List<UserBranchMappingDTO>>() {
                        }).getBody();
            }
            List<BranchDTO> branchDTOs = new ArrayList<>();
            String branchObject = branchUri.replace("/", "");
            for (UserBranchMappingDTO userBranchMapping : Objects.requireNonNull(userBranchMappingDTOs)) {
                BranchDTO branchDTO = null;
                if (CommonBFFUtil.isPermitted(header, branchObject, AuthUtil.permissionTypeEnum.GET)) {
                    branchDTO = restTemplate.exchange(new URI(branchUrl + CommonBFFConstant.GET_BRANCH_CODE_URI + userBranchMapping.getBranchCode()), HttpMethod.GET, entity, BranchDTO.class)
                            .getBody();
                }
                branchDTOs.add(branchDTO);
            }
            Response<UserBranchMappingDTO> response = new Response<>();
            ReferenceDTO referenceDTO = new ReferenceDTO();
            referenceDTO.setBranch(branchDTOs);
            response.setData(userBranchMappingDTOs);
            response.setRefernceList(referenceDTO);
            response.setMessage(successMessage);
            return response;
        } catch (RestClientResponseException e) {
            log.error("Inside UserServiceImpl :: getPrevilegeBranches() " + e.getResponseBodyAsString());
            throw e;
        }
    }

    /**
     * This method will return all the user roles
     *
     * @param header DFHeader
     * @param userId String
     * @return Response<RoleDTO>
     * @throws Exception exception
     */
    @Override
    public Response<RoleDTO> getUserRoles(DFHeader header, String userId) throws Exception {
        try {
            String object = usersUri.replace("/", "");
            Response<RoleDTO> response = new Response<>();
            HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
            List<RoleDTO> roles = new ArrayList<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                roles = restTemplate.exchange(new URI(usersUrl + userId + CommonBFFConstant.GET_ROLES_URI),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<RoleDTO>>() {
                        }).getBody();
            }
            response.setData(roles);
            ReferenceDTO referenceDTO = new ReferenceDTO();
            response.setRefernceList(referenceDTO);
            response.setMessage(successMessage);
            return response;
        } catch (RestClientResponseException e) {
            log.error("Inside UserServiceImpl :: editUser() {}", e.getResponseBodyAsString());
            throw new ServiceException("", "", 780);
        }
    }


    /**
     * This method will return all the users
     *
     * @param header DFHeader
     * @return Response<UserDTO>
     * @throws Exception exception
     */
    @Override
    public Response<UserDTO> getAllUsers(DFHeader header) throws Exception {
        try {
            String object = usersUri.replace("/", "");
            HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
            List<UserDTO> userDTOs = new ArrayList<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                userDTOs = restTemplate.exchange(new URI(usersUrl), HttpMethod.GET,
                        entity, new ParameterizedTypeReference<List<UserDTO>>() {
                        }).getBody();
            }
            List<LookUpMDMDTO> lookUpResponse = getLookUpDetails(header, entity);
            ReferenceDTO referenceDTO = new ReferenceDTO();
            referenceDTO.setCategoryList(lookUpResponse);
            Response<UserDTO> response = new Response<>();
            response.setRefernceList(referenceDTO);
            response.setData(userDTOs);
            response.setMessage(successMessage);
            return response;
        } catch (RestClientResponseException e) {
            log.error("Inside UserServiceImpl :: getAllUsers() " + e.getResponseBodyAsString());
            throw e;
        }
    }

    /**
     * This method will update the user
     *
     * @param header      DFHeader
     * @param updatedUser UserDTO
     * @return String
     * @throws Exception exception
     */
    @Override
    public String updateUser(DFHeader header, UserDTO updatedUser) throws Exception {
        try {
            if (!Boolean.parseBoolean(header.getIsAdmin())) {
                updatedUser.setIsAdmin(null);
            }
            String object = usersUri.replace("/", "");
            Map<String, String> result = new HashMap<>();
            HttpEntity<UserDTO> entity = new HttpEntity<>(updatedUser, BaseUtil.payload(header));
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.PUT)) {
                result = restTemplate.exchange(new URI(usersUrl), HttpMethod.PUT, entity, Map.class).getBody();
            }
            return result.get("responseMessage");

        } catch (RestClientResponseException e) {
            log.error("Inside UserServiceImpl :: editUser() " + e.getResponseBodyAsString());
            throw e;
        }
    }

    /**
     * Ths method will update the user privilege branches
     *
     * @param header            DFHeader
     * @param userId            String
     * @param idKey             String
     * @param previlegeBranches List<UserBranchMappingDTO>
     * @return String
     * @throws Exception excpetion
     */
    @Override
    public String updateUserPrevilegeBranches(DFHeader header, String userId, String idKey,
                                              List<UserBranchMappingDTO> previlegeBranches) throws Exception {

        try {
            String object = usersUri.replace("/", "");
            HttpEntity<Object> entity = new HttpEntity<>(previlegeBranches, BaseUtil.payload(header));
            Map<String, String> params = new HashMap<>();
            Map<String, String> result = null;
            params.put("userId", userId);
            params.put("idKey", idKey);
            URI uri = UriComponentsBuilder.fromUriString(usersUrl + "{userId}/{idKey}" + CommonBFFConstant.PREVILLEGE_BRANCH_CODES_URI_FOR_USER)
                    .buildAndExpand(params).toUri();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.PUT)) {
                result = restTemplate.exchange(uri, HttpMethod.PUT, entity, Map.class).getBody();
                return result.get("responseMessage");
            }
            return "";

        } catch (RestClientResponseException e) {
            log.error("Inside UserServiceImpl :: updateUserPrevilegeBranches() " + e.getResponseBodyAsString());
            throw e;
        }

    }

    /**
     * This method will update the user roles
     *
     * @param header    DFHeader
     * @param userId    String
     * @param idKey     String
     * @param userRoles List<RoleDTO>
     * @return List<RoleDTO>
     * @throws Exception exception
     */

    @Override
    public String updateUserRoles(DFHeader header, String userId, String idKey, List<RoleDTO> userRoles)
            throws Exception {

        try {
            String object = usersUri.replace("/", "");
            HttpEntity<Object> entity = new HttpEntity<>(userRoles, BaseUtil.payload(header));
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String> result = new HashMap<>();
            params.put("userId", userId);
            params.put("idKey", idKey);
            URI uri = UriComponentsBuilder.fromUriString(usersUrl + "{userId}/{idKey}" + CommonBFFConstant.GET_ROLES_URI)
                    .buildAndExpand(params).toUri();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.PUT)) {
                result = restTemplate.exchange(uri, HttpMethod.PUT, entity, Map.class).getBody();
            }
            return result.get("responseMessage");

        } catch (RestClientResponseException e) {
            log.error("Inside UserServiceImpl :: updateUserRoles() " + e.getResponseBodyAsString());
            throw e;
        }

    }

    /**
     * This method will get the list on last N updated users
     *
     * @param header DFHeader
     * @param number int
     * @return Response<UserDTO>
     * @throws Exception exception
     */
    public Response<UserDTO> getLastNUpdatedUsers(DFHeader header, int number) throws Exception {
        try {
            String object = usersUri.replace("/", "");
            Response<UserDTO> response = new Response<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                ResponseEntity<List<UserDTO>> userDTOs = restTemplate.exchange(new URI(usersUrl + "lastUpdated/" +
                                number), HttpMethod.GET,
                        entity, new ParameterizedTypeReference<List<UserDTO>>() {
                        });
                List<LookUpMDMDTO> lookUpResponse = getLookUpDetails(header, entity);
                ReferenceDTO referenceDTO = new ReferenceDTO();
                referenceDTO.setCategoryList(lookUpResponse);
                response.setRefernceList(referenceDTO);
                response.setData(userDTOs.getBody());
                response.setMessage(successMessage);
            }
            return response;
        } catch (RestClientResponseException e) {
            InputStream body = new ByteArrayInputStream(e.getResponseBodyAsByteArray());
            log.error("Inside UserServiceImpl :: editUser() {}", e.getResponseBodyAsString());
            throw new ServiceException(body.toString(), "", 500);
        }
    }
}
