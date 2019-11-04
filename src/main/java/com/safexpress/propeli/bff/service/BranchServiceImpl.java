package com.safexpress.propeli.bff.service;

import com.safexpress.propeli.bff.configuration.CommonBFFUtil;
import com.safexpress.propeli.bff.dto.BranchDTO;
import com.safexpress.propeli.bff.dto.HierarchyBranchListDTO;
import com.safexpress.propeli.bff.dto.Response;
import com.safexpress.propeli.bff.dto.UserBranchMappingDTO;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.branchService.branch_url}")
    private
    String branchUrl;

    @Autowired
    private UserBranchMappingDTO userBranchMappingDTO;

    @Value("${message.success}")
    private
    String successMessage;

    @Value("${service.branchService.branch_uri}")
    private
    String branchUri;

    private static final Logger log = LoggerFactory.getLogger(BranchServiceImpl.class);


    /**
     * @param header         DFHeader
     * @param numberOfBranch numberOfBranch
     * @return Response<BranchDTO>
     */
    @Override
    public Response<BranchDTO> getLatestNBranches(DFHeader header, int numberOfBranch) {
        try {
            String object = branchUri.replace("/", "");
            Response<BranchDTO> response = new Response<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                ResponseEntity<List<BranchDTO>> branchDTOs = restTemplate.exchange(new URI(branchUrl + "lastUpdated/" +
                                numberOfBranch), HttpMethod.GET,
                        entity, new ParameterizedTypeReference<List<BranchDTO>>() {
                        });
                response.setData(branchDTOs.getBody());
                response.setMessage(successMessage);
            }
            return response;
        } catch (RestClientResponseException | URISyntaxException e) {
            log.error("Inside BranchServiceImpl :: getLatestNBranches() {}", e.getMessage());
            throw new ServiceException("", "", 780);
        }
    }

    /**
     * @param header     DFHeader
     * @param branchCode String
     * @return Response<BranchDTO>
     * @throws Exception exception
     */
    @Override
    public Response<BranchDTO> getBranchByCode(DFHeader header, String branchCode) throws Exception {
        Response<BranchDTO> result;
        try {
            String object = branchUri.replace("/", "");
            Response<BranchDTO> response = new Response<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                BranchDTO branchDTO = restTemplate.exchange(new URI(branchUrl + "branchCode/" + branchCode),
                        HttpMethod.GET, entity, BranchDTO.class)
                        .getBody();
                List<BranchDTO> branchDTOS = new ArrayList<>();
                branchDTOS.add(branchDTO);
                response.setData(branchDTOS);
                response.setMessage(successMessage);
            }
            result = response;
        } catch (RestClientResponseException e) {
            throw new ServiceException("", "", 780);
        }
        return result;
    }

    /**
     * @param header         DFHeader
     * @param searchCriteria String
     * @param criteriaValue  String
     * @return Response<BranchDTO>
     * @throws Exception exception
     */

    @Override
    public Response<BranchDTO> getBranchDetailsByCriteria(DFHeader header, String searchCriteria, String criteriaValue) throws Exception {
        try {
            String object = branchUri.replace("/", "");
            Response<BranchDTO> response = new Response<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                ResponseEntity<List<BranchDTO>> branchDTO = restTemplate.exchange(new URI(branchUrl + searchCriteria + "/" + criteriaValue),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<BranchDTO>>() {
                        });
                response.setData(branchDTO.getBody());
                response.setMessage(successMessage);
            }
            return response;
        } catch (RestClientResponseException e) {
            throw new ServiceException("RestTemplate Exception", e.getMessage());
        }
    }

    /**
     * @param header DFHeader
     * @return Response<BranchDTO>
     */
    @Override
    public Response<BranchDTO> getAllBranch(DFHeader header) {
        try {
            String object = branchUri.replace("/", "");
            Response<BranchDTO> response = new Response<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<String> entity = new HttpEntity<>(BaseUtil.payload(header));
                ResponseEntity<List<BranchDTO>> branchDTOs = restTemplate.exchange(new URI(branchUrl),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<BranchDTO>>() {
                        });
                response.setData(branchDTOs.getBody());
                response.setMessage(successMessage);
            }
            return response;
        } catch (RestClientException | URISyntaxException | ServiceException e) {
            throw new ServiceException("RestTemplate Exception", e.getMessage());
        }
    }

    /**
     * @param header     DFHeader
     * @param branchList List<BranchInputDTO>
     * @return Response<HierarchyBranchListDTO>
     * @throws Exception exception
     */
    public Response<HierarchyBranchListDTO> getBranchHierarchyDetails(DFHeader header, List<UserBranchMappingDTO> branchList) throws Exception {
        try {
            String object = branchUri.replace("/", "");
            Response<HierarchyBranchListDTO> response = new Response<>();
            HttpEntity<List<UserBranchMappingDTO>> entity = new HttpEntity<>(branchList, BaseUtil.payload(header));
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.POST)) {
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
        } catch (RestClientException | URISyntaxException e) {
            throw new ServiceException("RestTemplate Exception", e.getMessage());
        }
    }

    /**
     * This method will return the branches based on wild search on branch name
     *
     * @param header     DFHeader
     * @param branchName String
     * @return Response<BranchDTO>
     */
    public Response<BranchDTO> getBranchByName(DFHeader header, String branchName) {
        Response<BranchDTO> result;
        try {
            String object = branchUri.replace("/", "");
            Response<BranchDTO> response = new Response<>();
            if (CommonBFFUtil.isPermitted(header, object, AuthUtil.permissionTypeEnum.GET)) {
                HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
                List<BranchDTO> branchDTOs = restTemplate.exchange(new URI(branchUrl + branchName),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<BranchDTO>>() {
                        }).getBody();
                response.setData(branchDTOs);
                response.setMessage(successMessage);
            }
            result = response;
        } catch (RestClientResponseException | URISyntaxException e) {
            throw new ServiceException("", "", 780);
        }
        return result;
    }

}
