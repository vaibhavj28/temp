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

@Api(value = "User Management BFF Controller")
@SFXApi
@RestController
@RequestMapping("/secure/v1/users")
public class UserController {

    @Autowired
    private UserService service;
    
    private static final String SUCCESS_MSG = "success";

    /**
	 * This api creates an User along with mapping of a default branch,one or multiple privilege branches,multiple roles(atleast one)
	 * @param header - contains various information such as about loggedin user
	 * @param newUser - user details to be saved into database
	 * @return - success or failure message as response
	 */
    @ApiOperation(value = "Creates an User along with mapping of a default branch,one or multiple previlege branches,multiple roles(atleast one)")
    @PostMapping
    public ResponseEntity<ResponseDTO<String>> createUser(@Valid DFHeader header,
                                                  @ApiParam(value = "User data to be inserted", required = true)
                                                  @Valid @RequestBody UserDTO newUser) throws Exception {

        String responseStatus = service.saveUser(header, newUser);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setMessage(SUCCESS_MSG);
        responseDTO.setData(responseStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    
    
    /**
	 * This api fetches details of all users 
	 * @param header - contains various information such as about loggedin user
	 * @return - list of users as response
	 */
    @ApiOperation(value = "Fetches details of all users")
    @GetMapping
    public ResponseEntity<Response<UserDTO>> getAllUsers(@Valid DFHeader header) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllUsers(header));
    }
    
    
    
    /**
	 * This api fetches details about an User along with details of its default branch
	 * @param header - contains various information such as about loggedin user
	 * @param uid - user id for which details needed
	 * @return - user detail along with mapped default branch as response
	 */
    @ApiOperation(value = "Fetches details about an User along with details of its default branch")
    @GetMapping("{uid}")
    public ResponseEntity<Response<UserDTO>> getUser(@Valid DFHeader header,
                                                     @ApiParam(value = "user id for which user has to be retrieved from database", required = true)
                                                     @PathVariable("uid") String userId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getUser(header, userId));
    }
    
    

	/**
	 * This api Fetches the list of privilege branch codes mapped for an User
	 * @param header - contains various information such as about loggedin user
	 * @param uid - user id for which details needed
	 * @return list of branch mapping details
	 */
    @ApiOperation(value = "Fetches the list of previlege branch codes mapped for an User")
    @GetMapping("{uid}/previlegeBranches")
    public ResponseEntity<Response<UserBranchMappingDTO>> getUserPrevilegeBranches(@Valid DFHeader header,
                                                                                   @ApiParam(value = "user id for which previlege branches have to be retrieved from database", required = true)
                                                                                   @PathVariable("uid") String userId) throws Exception {
        Response<UserBranchMappingDTO> response = service.getPrevilegeBranches(header, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
	 * This api Fetches the list of roles mapped for an User
	 * @param header - contains various information such as about loggedin user
	 * @param uid - user id for which details needed
	 * @return list of roles
	 */
    @ApiOperation(value = "Fetches the list of roles mapped for an User")
    @GetMapping("{uid}/roles")
    public ResponseEntity<Response<RoleDTO>> getUserRoles(@Valid DFHeader header,
                                                          @ApiParam(value = "user id for which roles have to be retrieved from database", required = true)
                                                          @PathVariable("uid") String userId) throws Exception {
        Response<RoleDTO> response = service.getUserRoles(header, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
    /**
	 * This api Updates an User and/or its default branch code
	 * @param header - contains various information such as about loggedin user
	 * @param updatedUser - user detail to be updated along with default branch code
	 * @return - success or failure message as response
	 */
    @ApiOperation(value = "Updates an User and/or its default branch code")
    @PutMapping
    public ResponseEntity<ResponseDTO<String>> updateUser(@Valid DFHeader header,
                                                  @ApiParam(value = "User data to be updated", required = true)
                                                  @Valid @RequestBody UserDTO updatedUser) throws Exception {

        String responseStatus = service.updateUser(header, updatedUser);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setMessage(SUCCESS_MSG);
        responseDTO.setData(responseStatus);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
    

	/**
	 * This api Updates the list of privilege branch codes mapped for an User
	 * @param header - contains various information such as about loggedin user
	 * @param uid - user id for which details needed
	 * @param idKey - user table key
	 * @param previlegeBranches - list of branch codes need to be updated
	 * @return - success or failure message as response
	 */
    @ApiOperation(value = "Updates the list of privilege branch codes mapped for an User")
    @PutMapping("{uid}/{idKey}/previlegeBranches")
    public ResponseEntity<ResponseDTO<String>> updateUserPrevilegeBranches(@Valid DFHeader header,
                                                                   @ApiParam(value = "user id for which Previlege Branches have to be Updated", required = true)
                                                                   @PathVariable("uid") String userId,
                                                                   @ApiParam(value = "user key for which Previlege Branches have to be Updated", required = true)
                                                                   @PathVariable String idKey,
                                                                   @ApiParam(value = "Previlege Branch List to be Updated", required = true)
                                                                   @Valid @RequestBody List<UserBranchMappingDTO> previlegeBranches) throws Exception {

        String responseStatus = service.updateUserPrevilegeBranches(header, userId, idKey, previlegeBranches);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setMessage(SUCCESS_MSG);
        responseDTO.setData(responseStatus);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    /**
	 * This api Updates the list of roles mapped for an User
	 * @param header - contains various information such as about loggedin user
	 * @param uid - user id for which details needed
	 * @param idKey - user table key for which Roles have to be Updated
	 * @param userRoles -Role List to be Updated
	 * @return - success or failure message as response
	 */
    @ApiOperation(value = "Updates the list of roles mapped for an User")
    @PutMapping("{uid}/{idKey}/roles")
    public ResponseEntity<ResponseDTO<String>> updateUserRoles(@Valid DFHeader header,
                                                       @ApiParam(value = "user id for which Roles have to be Updated", required = true)
                                                       @PathVariable("uid") String userId,
                                                       @ApiParam(value = "user key for which Roles have to be Updated", required = true)
                                                       @PathVariable String idKey,
                                                       @ApiParam(value = "Role List to be Updated", required = true)
                                                       @Valid @RequestBody List<RoleDTO> userRoles) throws Exception {

        String responseStatus = service.updateUserRoles(header, userId, idKey, userRoles);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setMessage(SUCCESS_MSG);
        responseDTO.setData(responseStatus);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


	/**
	 * This api fetches last n no. of updated User Details
	 * @param header - contains various information such as about loggedin user
	 * @param number - number of users to be fetched
	 * @return - list of users
	 * @throws Exception
	 */
    @ApiOperation(value = "Fetches last n no. of updated User Details")
    @GetMapping("lastUpdated/{number}")
    public ResponseEntity<Response<UserDTO>> getLastNUpdatedUsers(@Valid DFHeader header,
                                                                  @PathVariable int number) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getLastNUpdatedUsers(header, number));
    }

}
