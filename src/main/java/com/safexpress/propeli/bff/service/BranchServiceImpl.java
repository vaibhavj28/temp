package com.safexpress.propeli.bff.service;

import com.safexpress.propeli.bff.dto.BranchDTO;
import com.safexpress.propeli.bff.dto.HierarchyBranchListDTO;
import com.safexpress.propeli.bff.dto.Response;
import com.safexpress.propeli.bff.dto.UserBranchMappingDTO;
import com.safexpress.propeli.bff.utility.CommonBFFUtil;
import com.safexpress.propeli.security.util.AuthUtil;
import com.safexpress.propeli.servicebase.exception.ServiceException;
import com.safexpress.propeli.servicebase.model.DFHeader;
import com.safexpress.propeli.servicebase.util.BaseUtil;
import org.json.JSONObject;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.branchService.branch_url}")
    private
    String branchUrl;

    @Value("${message.success}")
    private
    String successMessage;

    @Value("${service.branchService.branch_uri}")
    private
    String branchUri;

    private static final Logger log = LoggerFactory.getLogger(BranchServiceImpl.class);


    /**
     * This method will return the last N updated branches from repo
     *
     * @param header         DFHeader
     * @param numberOfBranch numberOfBranch
     * @return Response<BranchDTO>
     */
    @Override
    public Response<BranchDTO> getLatestNBranches(DFHeader header, int numberOfBranch) throws Exception {
        try {
            
            Response<BranchDTO> response = new Response<>();
            
            if (CommonBFFUtil.isPermitted(header, branchUri, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                ResponseEntity<List<BranchDTO>> branchDTOs = restTemplate.exchange(new URI(branchUrl + "lastUpdated/" +
                                numberOfBranch), HttpMethod.GET,
                        entity, new ParameterizedTypeReference<List<BranchDTO>>() {
                        });
                
                response.setData(branchDTOs.getBody());
                response.setMessage(successMessage);
            }
            return response;
        } catch (RestClientResponseException exception) {
            JSONObject error = CommonBFFUtil.handleError(exception);
            throw new ServiceException(error.getString("errorCode"), error.getString("description"), Integer.parseInt(error.getString("status")));

        }
    }

    /**
     * This method will return the branch by branch code
     *
     * @param header     DFHeader
     * @param branchCode String
     * @return Response<BranchDTO>
     * @throws Exception exception
     */
    @Override
    public Response<BranchDTO> getBranchByCode(DFHeader header, String branchCode) throws Exception {
        Response<BranchDTO> result;
        try {
            
            Response<BranchDTO> response = new Response<>();
            
            if (CommonBFFUtil.isPermitted(header, branchUri, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                BranchDTO branchDTO = restTemplate.exchange(new URI(branchUrl + "branchCode/" + branchCode),
                        HttpMethod.GET, entity, BranchDTO.class).getBody();
                
                List<BranchDTO> branchDTOS = new ArrayList<>();
                branchDTOS.add(branchDTO);
                response.setData(branchDTOS);
                response.setMessage(successMessage);
            }
            result = response;
        } catch (RestClientResponseException exception) {
            JSONObject error = CommonBFFUtil.handleError(exception);
            throw new ServiceException(error.getString("errorCode"), error.getString("description"), Integer.parseInt(error.getString("status")));

        }
        return result;
    }

    /**
     * This method will return the branch details based on search criteria such as area, region, branch Type, pin code
     *
     * @param header         DFHeader
     * @param searchCriteria String
     * @param criteriaValue  String
     * @return Response<BranchDTO>
     * @throws Exception exception
     */

    @Override
    public Response<BranchDTO> getBranchDetailsByCriteria(DFHeader header, String searchCriteria, String criteriaValue) throws Exception {
        try {
            
            Response<BranchDTO> response = new Response<>();
            
            if (CommonBFFUtil.isPermitted(header, branchUri, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                ResponseEntity<List<BranchDTO>> branchDTO = restTemplate.exchange(new URI(branchUrl + searchCriteria + "/" + criteriaValue),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<BranchDTO>>() {
                        });
                
                response.setData(branchDTO.getBody());
                response.setMessage(successMessage);
            }
            return response;
        } catch (RestClientResponseException exception) {
            JSONObject error = CommonBFFUtil.handleError(exception);
            throw new ServiceException(error.getString("errorCode"), error.getString("description"), Integer.parseInt(error.getString("status")));
        }
    }


    /**
     * This method will return all the branches
     *
     * @param header DFHeader
     * @return Response<BranchDTO>
     */
    @Override
    public Response<BranchDTO> getAllBranch(DFHeader header) throws Exception {
        try {
            
            Response<BranchDTO> response = new Response<>();
           
            if (CommonBFFUtil.isPermitted(header, branchUri, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<String> entity = new HttpEntity<>(BaseUtil.payload(header));
                ResponseEntity<List<BranchDTO>> branchDTOs = restTemplate.exchange(new URI(branchUrl),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<BranchDTO>>() {
                        });
                response.setData(branchDTOs.getBody());
                response.setMessage(successMessage);
            }
            
            return response;
        } catch (RestClientResponseException exception) {
            JSONObject error = CommonBFFUtil.handleError(exception);
            throw new ServiceException(error.getString("errorCode"), error.getString("description"), Integer.parseInt(error.getString("status")));

        }
    }

    /**
     * This method will return the branch Hierarchy list of default and privilege branch based on parent branch code and isDefault criteria
     *
     * @param header     DFHeader
     * @param branchList List<BranchInputDTO>
     * @return Response<HierarchyBranchListDTO>
     * @throws Exception exception
     */
    public Response<HierarchyBranchListDTO> getBranchHierarchyDetails(DFHeader header, List<UserBranchMappingDTO> branchList) throws Exception {
        try {
            
            Response<HierarchyBranchListDTO> response = new Response<>();
            HttpEntity<List<UserBranchMappingDTO>> entity = new HttpEntity<>(branchList, BaseUtil.payload(header));
            
            if (CommonBFFUtil.isPermitted(header, branchUri, AuthUtil.permissionTypeEnum.POST)) {
                ResponseEntity<HierarchyBranchListDTO> branchHierarchies = restTemplate.exchange(new URI(branchUrl +
                                "branchHierarchyDetails"),
                        HttpMethod.POST, entity, new ParameterizedTypeReference<HierarchyBranchListDTO>() {
                        });
                
                List<HierarchyBranchListDTO> hierarchyBranchListDTOS = new ArrayList<>();
                hierarchyBranchListDTOS.add(branchHierarchies.getBody());
                response.setData(hierarchyBranchListDTOS);
                response.setMessage(successMessage);
            }
            return response;
        } catch (RestClientResponseException exception) {
            JSONObject error = CommonBFFUtil.handleError(exception);
            throw new ServiceException(error.getString("errorCode"), error.getString("description"), Integer.parseInt(error.getString("status")));
        }
    }

    /**
     * This method will return the branches based on wild search on branch name
     *
     * @param header     DFHeader
     * @param branchName String
     * @return Response<BranchDTO>
     */
    public Response<BranchDTO> getBranchByName(DFHeader header, String branchName) throws Exception {
        try {
            
            Response<BranchDTO> response = new Response<>();
           
            if (CommonBFFUtil.isPermitted(header, branchUri, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                List<BranchDTO> branchDTOs = restTemplate.exchange(new URI(branchUrl + branchName),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<BranchDTO>>() {
                        }).getBody();
                response.setData(branchDTOs);
                response.setMessage(successMessage);
            }
            
            return response;
        } catch (RestClientResponseException exception) {
            JSONObject error = CommonBFFUtil.handleError(exception);
            throw new ServiceException(error.getString("errorCode"), error.getString("description"), Integer.parseInt(error.getString("status")));

        }
    }

    /**
     * This method will fetch all the branch types from repository
     *
     * @param header DFHeader
     * @return Response<String>
     * @throws Exception exception
     */
    public Response<String> getAllBranchesTypes(DFHeader header) throws Exception {
        try {
            
            Response<String> response = new Response<>();
            
            if (CommonBFFUtil.isPermitted(header, branchUri, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                List<String> branchTypes = restTemplate.exchange(new URI(branchUrl + "types"),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<String>>() {
                        }).getBody();
                response.setData(branchTypes);
                response.setMessage(successMessage);
            }
            
            return response;
        } catch (RestClientResponseException exception) {
            JSONObject error = CommonBFFUtil.handleError(exception);
            throw new ServiceException(error.getString("errorCode"), error.getString("description"), Integer.parseInt(error.getString("status")));

        }

    }
}
