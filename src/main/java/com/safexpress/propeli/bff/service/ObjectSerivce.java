package com.safexpress.propeli.bff.service;

import com.safexpress.propeli.bff.dto.EntityDTO;
import com.safexpress.propeli.bff.dto.ModuleObjectDTO;
import com.safexpress.propeli.bff.dto.Response;
import com.safexpress.propeli.servicebase.model.DFHeader;

import java.util.List;

public interface ObjectSerivce {
	
	/**
	 * @param moduleObject
	 * @return integer result 0: fail , 1: success
	 * @throws Exception
	 */
	/*
	 * ModuleObjectDTO saveObject(DFHeader header, ModuleObjectDTO moduleObject )
	 * throws Exception;
	 */
	
	/**
	 * @param objectId
	 * @throws Exception
	 */
	ModuleObjectDTO getObjectById(DFHeader header,String objectId ) throws Exception;
	/**
	 * @param objectName
	 * @throws Exception
	 */
	Response<ModuleObjectDTO> getObjectByName(DFHeader header,String objectName) throws Exception;
	
	/**
	 * @param ModuleObjectDTO
	 * @throws Exception
	 */
	/*
	 * Integer updateObject(DFHeader header,ModuleObjectDTO moduleObject) throws
	 * Exception;
	 */
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	Response<ModuleObjectDTO> getAllObject(DFHeader header) throws Exception;
	
	/**
	 * @param menuId
	 * @throws Exception
	 */
	Response<EntityDTO> getSectionList(DFHeader header,long menuId) throws Exception;

	Response<ModuleObjectDTO> getLastNUpdatedObjects(DFHeader header, int number) throws Exception;

}
