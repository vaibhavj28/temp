package com.safexpress.propeli.bff.service;

import com.safexpress.propeli.bff.dto.ReferenceDTO;
import com.safexpress.propeli.bff.dto.Response;
import com.safexpress.propeli.bff.dto.BranchDTO;
import com.safexpress.propeli.bff.dto.BranchInputDTO;
import com.safexpress.propeli.bff.dto.UserBranchMappingDTO;
import com.safexpress.propeli.bff.dto.UserDTO;
import com.safexpress.propeli.bff.dto.HierarchyBranchListDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;
import com.safexpress.propeli.servicebase.util.BaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.branchService.branch_url}")
    private
    String branchUrl;

    @Autowired
    private UserBranchMappingDTO userBranchMappingDTO;

    @Autowired
    private Response response;

    @Value("${message.success}")
    private
    String successMessage;

    private static final Logger log = LoggerFactory.getLogger(BranchServiceImpl.class);


    /**
     *
     * @param header DFHeader
     * @param numberOfBranch numberOfBranch
     * @return Response<BranchDTO>
     * @throws Exception e
     */
    @Override
    public Response<BranchDTO> getLatestNBranches(DFHeader header, int numberOfBranch) throws Exception  {
        try {
            HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
            ResponseEntity<List<UserDTO>> branchDTOs = restTemplate.exchange(new URI(branchUrl+
                            numberOfBranch + "/details"), HttpMethod.GET,
                    entity, new ParameterizedTypeReference<List<UserDTO>>() {
                    });
            response.setData(branchDTOs.getBody());
            response.setMessage(successMessage);
            return response;
        }
        catch (RestClientResponseException | URISyntaxException e){
            throw e;
        }
    }

    /**
     *
     * @param header  DFHeader
     * @param branchCode String
     * @return Response<BranchDTO>
     * @throws Exception exception
     */
    @Override
    public Response<BranchDTO> getBranchByCode(DFHeader header, String branchCode) throws Exception  {
        try {
            HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
            BranchDTO branchDTO = restTemplate.exchange(new URI( branchUrl+ "branchCode/"+ branchCode ),
                    HttpMethod.GET, entity, BranchDTO.class)
                    .getBody();
            List<BranchDTO> branchDTOS = new ArrayList<>();
            branchDTOS.add(branchDTO);
            response.setData(branchDTOS);
            ReferenceDTO referenceDTO = new ReferenceDTO();
            Optional.ofNullable(branchDTO.getBranchCode()).ifPresent(userBranchMappingDTO::setBranchCode);
            userBranchMappingDTO.setIsDefault(branchDTO.getIsDefault());
            userBranchMappingDTO.setAddOrRemove(branchDTO.getAddOrRemove());
            referenceDTO.setBranch(userBranchMappingDTO);
            response.setRefernceList(referenceDTO);
            response.setMessage(successMessage);
            return response;
        }
        catch (RestClientResponseException | URISyntaxException e){
            throw e;
        }
    }

    /**
     *
     * @param header DFHeader
     * @param searchCriteria String
     * @param criteriaValue String
     * @return Response<BranchDTO>
     * @throws Exception exception
     */

    @Override
    public Response getBranchDetailsByCriteria(DFHeader header, String searchCriteria, String criteriaValue) throws Exception {
        try {
            HttpEntity<DFHeader> entity = new HttpEntity<>(BaseUtil.payload(header));
            ResponseEntity<List<BranchDTO>> branchDTO = restTemplate.exchange(new URI(branchUrl + searchCriteria + "/"+ criteriaValue),
                    HttpMethod.GET, entity, new ParameterizedTypeReference<List<BranchDTO>>() {
                    });
            response.setData(branchDTO.getBody());
            response.setMessage(successMessage);
            return response;
        }
        catch (RestClientResponseException | URISyntaxException e){
            log.error("Error occured while getting branch details by criteria : ", e);
            throw e;
        }
    }

    /**
     *
     * @param header DFHeader
     * @return Response<BranchDTO>
     * @throws Exception exception
     */
    @Override
    public Response<BranchDTO> getAllBranch(DFHeader header) throws Exception{
        try {
            HttpEntity<String> entity = new HttpEntity<>(BaseUtil.payload(header));
            ResponseEntity<List<BranchDTO>> branchDTOs = restTemplate.exchange(new URI(branchUrl ),
                    HttpMethod.GET, entity, new ParameterizedTypeReference<List<BranchDTO>>() {
                    });
            response.setData(branchDTOs.getBody());
            response.setMessage(successMessage);
            return response;
        } catch (RestClientException | URISyntaxException e) {
            log.error("Error occured while fetching all the branches", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *
     * @param header DFHeader
     * @param branchList  List<BranchInputDTO>
     * @return Response<HierarchyBranchListDTO>
     * @throws Exception exception
     */
    public Response<HierarchyBranchListDTO> getBranchHierarchyDetails(DFHeader header, List<BranchInputDTO> branchList ) throws Exception{
        try {
            HttpEntity<List<BranchInputDTO>> entity = new HttpEntity<>(branchList, BaseUtil.payload(header));
            ResponseEntity<List<HierarchyBranchListDTO>> branchHierachies = restTemplate.exchange(new URI(branchUrl +"branchHierarchyDetails"),
                    HttpMethod.POST, entity, new ParameterizedTypeReference<List<HierarchyBranchListDTO>>() {
                    });
            response.setData(branchHierachies.getBody());
            response.setMessage(successMessage);
            return response;
        } catch (RestClientException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
