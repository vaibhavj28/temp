package com.safexpress.propeli.bff.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safexpress.propeli.bff.dto.BranchDTO;
import com.safexpress.propeli.bff.dto.EntityDTO;
import com.safexpress.propeli.bff.dto.LookUpDTO;
import com.safexpress.propeli.bff.dto.ModuleObjectDTO;
import com.safexpress.propeli.bff.dto.ResponseDTO;
import com.safexpress.propeli.bff.service.ObjectSerivce;
import com.safexpress.propeli.servicebase.annotation.SFXApi;
import com.safexpress.propeli.servicebase.model.DFHeader;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Object Operation service", tags = "Object Operation service", description = "Object Operation service")
@RestController
@SFXApi
@RequestMapping("/secure/um/v1")
public class ObjectController {

	@Autowired
	private ObjectSerivce service;

	@Autowired
	private ResponseDTO responsedto;

	@PostMapping(path = "/object", produces = "application/json")
	public ResponseEntity<ResponseDTO> createObject(@Valid DFHeader header,
			@ApiParam(value = "Object data to be inserted", required = true) @Valid @RequestBody ModuleObjectDTO moduleObject)
			throws Exception {

		ModuleObjectDTO response = service.saveObject(header, moduleObject);
		responsedto.setMessage("success");
		responsedto.setData(response);
		return ResponseEntity.status(HttpStatus.OK).body(responsedto);
	}

	@ApiOperation(value = "Object List Details", notes = "Get a list of all Object Details")
	@GetMapping(path = "/object", produces = "application/json")
	public ResponseEntity<ResponseDTO> getAllObject(DFHeader header) throws Exception {
		List<ModuleObjectDTO> reponse = service.getAllObject(header);
		responsedto.setMessage("success");
		responsedto.setData(reponse);
		return ResponseEntity.status(HttpStatus.OK).body(responsedto);
	}

	@ApiOperation(value = "Update object Details", notes = "Update object Details")
	@PutMapping("/object")
	public ResponseEntity<ResponseDTO> updateObject(@Valid DFHeader header, @RequestBody ModuleObjectDTO moduleObject)
			throws Exception {
		Integer response = service.updateObject(header, moduleObject);
		if (!(response == 0)) {
			responsedto.setMessage("Succefully updated");
		} else {
			responsedto.setMessage("Update Failed");
		}
		responsedto.setData("Status :  " + response);
		return ResponseEntity.status(HttpStatus.OK).body(responsedto);
	}

	@ApiOperation(value = "Get object Details by Name", notes = "Get object Details")
	@GetMapping("/object/name/{objectName}")
	public ResponseEntity<ResponseDTO> getObjectByName(@Valid DFHeader header, @PathVariable String objectName)
			throws Exception {
		List<ModuleObjectDTO> response = service.getObjectByName(header, objectName);
		responsedto.setMessage("success");
		responsedto.setData(response);
		return ResponseEntity.status(HttpStatus.OK).body(responsedto);
	}

	// section

	@ApiOperation(value = "Object section Details", notes = "Get a list of all Object section Details")
	@GetMapping("/section/{menuId}")
	public ResponseEntity<ResponseDTO> getSectionList(@Valid DFHeader header, @PathVariable long menuId)
			throws Exception {
		List<EntityDTO> response = service.getSectionList(header, menuId);
		responsedto.setMessage("success");
		responsedto.setData(response);
		return ResponseEntity.status(HttpStatus.OK).body(responsedto);
	}

	@ApiOperation(value = "Object section Details", notes = "save Object section Details")
	@PostMapping("/section")
	public ResponseEntity<ResponseDTO> saveSection(@Valid DFHeader header, @RequestBody EntityDTO entityDto)
			throws Exception {
		Integer response = service.saveSection(header, entityDto);
		if (!(response == 0)) {
			responsedto.setMessage("Succefully updated");
		} else {
			responsedto.setMessage("Update Failed");
		}
		responsedto.setData("Status :  " + response);
		return ResponseEntity.status(HttpStatus.OK).body(responsedto);
	}

	// get lookup list
	

}
