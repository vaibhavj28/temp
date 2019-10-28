package com.safexpress.propeli.bff.service;

import com.safexpress.propeli.bff.dto.BranchDTO;
import com.safexpress.propeli.bff.dto.BranchInputDTO;
import com.safexpress.propeli.bff.dto.HierarchyBranchListDTO;
import com.safexpress.propeli.bff.dto.Response;
import com.safexpress.propeli.servicebase.model.DFHeader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface BranchService {
    /**
     *
     * @param numberOfBranch numberOfBranch
     * @return List<BranchDTO> List<BranchDTO>
     */
    Response<BranchDTO> getLatestNBranches(DFHeader header, int numberOfBranch) throws Exception;

    Response<BranchDTO> getBranchByCode(DFHeader header, String numberOfBranch) throws Exception;

    Response<BranchDTO> getBranchDetailsByCriteria(DFHeader header, String searchCriteria, String criteriaValue) throws Exception;

    Response<BranchDTO> getAllBranch(DFHeader header) throws Exception;

    Response<HierarchyBranchListDTO> getBranchHierarchyDetails(DFHeader header, List<BranchInputDTO> branchList ) throws Exception ;
}
