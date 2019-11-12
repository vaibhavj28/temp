package com.safexpress.propeli.bff.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safexpress.propeli.bff.dto.MenuHierarchyDTO;
import com.safexpress.propeli.bff.dto.ModuleObjectDTO;
import com.safexpress.propeli.bff.dto.Response;
import com.safexpress.propeli.bff.service.ObjectSerivce;
import com.safexpress.propeli.servicebase.annotation.SFXApi;
import com.safexpress.propeli.servicebase.model.DFHeader;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Object Operation service", tags = "Object Operation service", description = "Object Operation service")
@RestController
@SFXApi
@RequestMapping("/secure/v1/objects")
public class ObjectController {

    @Autowired
    private ObjectSerivce service;

   /* @Autowired
    private ResponseDTO responsedto;*/

    /*
     * @PostMapping(path = "/object", produces = "application/json") public
     * ResponseEntity<ResponseDTO> createObject(@Valid DFHeader header,
     *
     * @ApiParam(value = "Object data to be inserted", required =
     * true) @Valid @RequestBody ModuleObjectDTO moduleObject) throws Exception {
     *
     * ModuleObjectDTO response = service.saveObject(header, moduleObject);
     * responsedto.setMessage("success"); responsedto.setData(response); return
     * ResponseEntity.status(HttpStatus.OK).body(responsedto); }
     */

    @ApiOperation(value = "Object List Details", notes = "Get a list of all Object Details")
    @GetMapping
    public ResponseEntity<Response<ModuleObjectDTO>> getAllObject(DFHeader header) throws Exception {
        Response<ModuleObjectDTO> response = service.getAllObject(header);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*
     * @ApiOperation(value = "Update object Details", notes =
     * "Update object Details")
     *
     * @PutMapping("/object") public ResponseEntity<ResponseDTO> updateObject(@Valid
     * DFHeader header, @RequestBody ModuleObjectDTO moduleObject) throws Exception
     * { Integer response = service.updateObject(header, moduleObject); if
     * (!(response == 0)) { responsedto.setMessage("Succefully updated"); } else {
     * responsedto.setMessage("Update Failed"); } responsedto.setData("Status :  " +
     * response); return ResponseEntity.status(HttpStatus.OK).body(responsedto); }
     */

    @ApiOperation(value = "Get object Details by Name", notes = "Get object Details")
    @GetMapping("name/{objectName}")
    public ResponseEntity<Response<ModuleObjectDTO>> getObjectByName(@Valid DFHeader header, @PathVariable String objectName)
            throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getObjectByName(header, objectName));
    }


	/*
	 * @ApiOperation(value = "Object section Details", notes =
	 * "Get a list of all Object section Details")
	 * 
	 * @GetMapping("/section/{menuId}") public ResponseEntity<Response<EntityDTO>>
	 * getSectionList(@Valid DFHeader header, @PathVariable long menuId) throws
	 * Exception { Response<EntityDTO> response = service.getSectionList(header,
	 * menuId); return ResponseEntity.status(HttpStatus.OK).body(response); }
	 */

    /*
     * @ApiOperation(value = "Object section Details", notes =
     * "save Object section Details")
     *
     * @PostMapping("/section") public ResponseEntity<ResponseDTO>
     * saveSection(@Valid DFHeader header, @RequestBody EntityDTO entityDto) throws
     * Exception { Integer response = service.saveSection(header, entityDto); if
     * (!(response == 0)) { responsedto.setMessage("Succefully updated"); } else {
     * responsedto.setMessage("Update Failed"); } responsedto.setData("Status :  " +
     * response); return ResponseEntity.status(HttpStatus.OK).body(responsedto); }
     */


    @ApiOperation(value = "Get last n updated object Details", notes = "Get a list of all Object Details")
    @GetMapping("lastUpdated/{number}")
    public ResponseEntity<Response<ModuleObjectDTO>> getLastNUpdatedObjects(@Valid DFHeader header, @PathVariable int number) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getLastNUpdatedObjects(header, number));
    }
    
    @ApiOperation(value = "Menu Hierarchy List Details", notes = "Get a list of all Menu Hierarchies")
	@GetMapping("menuHierarchies")
	public ResponseEntity<Response<MenuHierarchyDTO>> getMenuHierarchy(DFHeader header) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(service.getMenuHierarchy(header));
	}

}
