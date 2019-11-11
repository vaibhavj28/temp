package com.safexpress.propeli.bff.controller;

import com.safexpress.propeli.bff.dto.BranchDTO;
import com.safexpress.propeli.bff.dto.Response;
import com.safexpress.propeli.bff.service.BranchService;
import com.safexpress.propeli.servicebase.annotation.SFXApi;
import com.safexpress.propeli.servicebase.model.DFHeader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "Branch MDM BFF Controller")
@SFXApi
@RestController
@RequestMapping("/secure/v1/branches/")
public class BranchController {
    @Autowired
    private BranchService service;

    @ApiOperation(value = "Get the list of latest N created branches based on input number", notes = "Get the list of latest N created Branches")
    @GetMapping("{lastNCreatedBranches}")
    public ResponseEntity<Response<BranchDTO>>getNLastCreatedBranches(@Valid DFHeader header, @PathVariable("lastNCreatedBranches") int numberOfBranch) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getLatestNBranches(header, numberOfBranch));
    }

    @ApiOperation(value = "Get Branch Details by branchCode", notes = "Get Branch Details by branchCode")
    @GetMapping("branchCode/{branchcode}")
    public ResponseEntity<Response<BranchDTO>>getBranchByBranchCode(@Valid DFHeader header,
                                                                    @PathVariable ("branchcode") String branchcode) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getBranchByCode(header, branchcode));
    }

    @ApiOperation(value = "Get Branch Details by search criteria", notes = "Searchriteria is the key for search : area," +
            " region, pincode, branchtype, branchName")
    @GetMapping("{searchCriteria}/{criteriaValue}")
    public ResponseEntity<Response<BranchDTO>>getBranchDetailsByCriteria(@Valid DFHeader header,
                        @PathVariable ("searchCriteria") String searchCriteria,
                        @PathVariable ("criteriaValue") String criteriaValue) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getBranchDetailsByCriteria(header, searchCriteria, criteriaValue));
    }


    @ApiOperation(value = "Branch List Details sorted alphabetically by branchName", notes = "Get a list of all Branch Details, alphabetically sorted by branchName ")
    @GetMapping()
    public ResponseEntity<Response<BranchDTO>> getAllBranches(DFHeader header) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllBranch(header));
    }



    @ApiOperation(value = "Get list of branches with wildSearch", notes = "Get list of branches by wildSearch on branch name")
    @GetMapping("branchName/{branchName}")
    public ResponseEntity<Response<BranchDTO>> getAllBranchesWithBranchName(@Valid DFHeader header, @PathVariable("branchName") String branchName) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(service.getBranchByName(header, branchName));
    }


    @ApiOperation(value = "Get list of all types of branches", notes = "Get list of all types of branches")
    @GetMapping("types")
    public ResponseEntity<Response<String>> getAllBranchesTypes(@Valid DFHeader header) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllBranchesTypes(header));
    }

}
