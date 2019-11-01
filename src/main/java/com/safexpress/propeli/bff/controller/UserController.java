package com.safexpress.propeli.bff.controller;

import com.safexpress.propeli.bff.dto.Response;
import com.safexpress.propeli.bff.dto.RoleDTO;
import com.safexpress.propeli.bff.dto.UserBranchMappingDTO;
import com.safexpress.propeli.bff.dto.UserDTO;
import com.safexpress.propeli.bff.service.UserService;
import com.safexpress.propeli.servicebase.annotation.SFXApi;
import com.safexpress.propeli.servicebase.dto.ResponseDTO;
import com.safexpress.propeli.servicebase.model.DFHeader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author IBM India
 **/
@Api(value = "User Management BFF Controller")
@SFXApi
@RestController
@RequestMapping("/secure/um/v1/")
public class UserController {

    @Autowired
    private UserService service;

    @ApiOperation(value = "Creates an User")
    @PostMapping("users")
    public ResponseEntity<ResponseDTO> createUser(@Valid DFHeader header,
                                                  @ApiParam(value = "User data to be inserted", required = true)
                                                  @Valid @RequestBody UserDTO newUser) throws Exception {

        String responseStatus = service.saveUser(header, newUser);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("success");
        responseDTO.setData(responseStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @ApiOperation(value = "Fetches all Users")
    @GetMapping("users")
    public ResponseEntity<Response<UserDTO>> getAllUsers(@Valid DFHeader header) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllUsers(header));
    }

    @ApiOperation(value = "Fetches an User")
    @GetMapping("users/{uid}")
    public ResponseEntity<Response<UserDTO>> getUser(@Valid DFHeader header,
                                                     @ApiParam(value = "user id for which user has to be retrieved from database", required = true)
                                                     @PathVariable("uid") String userId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getUser(header, userId));
    }

    @ApiOperation(value = "Fetches the list of previlege branches for an User")
    @GetMapping("users/{uid}/previlegeBranches")
    public ResponseEntity<Response<UserBranchMappingDTO>> getUserPrevilegeBranches(@Valid DFHeader header,
                                                                                   @ApiParam(value = "user id for which previlege branches have to be retrieved from database", required = true)
                                                                                   @PathVariable("uid") String userId) throws Exception {
        Response<UserBranchMappingDTO> response = service.getPrevilegeBranches(header, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Fetches the list of roles for an User")
    @GetMapping("users/{uid}/roles")
    public ResponseEntity<Response<RoleDTO>> getUserRoles(@Valid DFHeader header,
                                                          @ApiParam(value = "user id for which roles have to be retrieved from database", required = true)
                                                          @PathVariable("uid") String userId) throws Exception {
        Response<RoleDTO> response = service.getUserRoles(header, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Updates an User")
    @PutMapping("users")
    public ResponseEntity<ResponseDTO> updateUser(@Valid DFHeader header,
                                                  @ApiParam(value = "User data to be updated", required = true)
                                                  @Valid @RequestBody UserDTO updatedUser) throws Exception {

        String responseStatus = service.updateUser(header, updatedUser);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setMessage("success");
        responseDTO.setData(responseStatus);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @ApiOperation(value = "Updates the list of previlege branches for an User")
    @PutMapping("users/{uid}/{idKey}/previlegeBranches")
    public ResponseEntity<ResponseDTO> updateUserPrevilegeBranches(@Valid DFHeader header,
                                                                   @ApiParam(value = "user id for which Previlege Branches have to be Updated", required = true)
                                                                   @PathVariable("uid") String userId,
                                                                   @ApiParam(value = "user key for which Previlege Branches have to be Updated", required = true)
                                                                   @PathVariable String idKey,
                                                                   @ApiParam(value = "Previlege Branch List to be Updated", required = true)
                                                                   @Valid @RequestBody List<UserBranchMappingDTO> previlegeBranches) throws Exception {

        String responseStatus = service.updateUserPrevilegeBranches(header, userId, idKey, previlegeBranches);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setMessage("success");
        responseDTO.setData(responseStatus);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @ApiOperation(value = "Updates the list of roles for an User")
    @PutMapping("users/{uid}/{idKey}/roles")
    public ResponseEntity<ResponseDTO> updateUserRoles(@Valid DFHeader header,
                                                       @ApiParam(value = "user id for which Roles have to be Updated", required = true)
                                                       @PathVariable("uid") String userId,
                                                       @ApiParam(value = "user key for which Roles have to be Updated", required = true)
                                                       @PathVariable String idKey,
                                                       @ApiParam(value = "Role List to be Updated", required = true)
                                                       @Valid @RequestBody List<RoleDTO> userRoles) throws Exception {

        String responseStatus = service.updateUserRoles(header, userId, idKey, userRoles);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setMessage("success");
        responseDTO.setData(responseStatus);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


    @ApiOperation(value = "Get last n updated User Details", notes = "Get a list of all User Details")
    @GetMapping("users/lastUpdated/{number}")
    public ResponseEntity<Response<UserDTO>> getLastNUpdatedUsers(@Valid DFHeader header,
                                                                  @PathVariable int number) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getLastNUpdatedUsers(header, number));
    }

}
