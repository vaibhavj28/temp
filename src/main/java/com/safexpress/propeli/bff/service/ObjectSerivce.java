package com.safexpress.propeli.bff.service;

import java.util.List;
import com.safexpress.propeli.bff.dto.EntityDTO;
import com.safexpress.propeli.bff.dto.ModuleObjectDTO;
import com.safexpress.propeli.bff.dto.Response;
import com.safexpress.propeli.servicebase.model.DFHeader;

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
	List<ModuleObjectDTO> getObjectByName(DFHeader header,String objectName) throws Exception;
	
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

	/**
	 * @param entityDto
	 * @return integer result 0: fail , 1: success
	 * @throws Exception
	 */
	/* int saveSection(DFHeader header, EntityDTO entityDto) throws Exception; */

}
