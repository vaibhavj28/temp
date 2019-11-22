package com.safexpress.propeli.bff.service;

import java.util.List;

import com.safexpress.propeli.bff.dto.BranchDTO;
import com.safexpress.propeli.bff.dto.HierarchyBranchListDTO;
import com.safexpress.propeli.bff.dto.PincodeDTO;
import com.safexpress.propeli.bff.dto.Response;
import com.safexpress.propeli.bff.dto.UserBranchMappingDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;

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

    Response<HierarchyBranchListDTO> getBranchHierarchyDetails(DFHeader header, List<UserBranchMappingDTO> branchList ) throws Exception ;

    Response<BranchDTO> getBranchByName(DFHeader header, String branchName) throws Exception;

    Response<String> getAllBranchesTypes(DFHeader header) throws Exception;

    Response<PincodeDTO> getDetailsByPincode(DFHeader header, String pincode) throws Exception;
}
