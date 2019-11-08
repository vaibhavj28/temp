package com.safexpress.propeli.bff.exception;

import com.safexpress.propeli.servicebase.exception.ServiceException;

public class CommonBffException extends ServiceException{
	
	private static final long serialVersionUID = 1L;

	public CommonBffException(String errorCode, String errorMessage, int statusCode) {
		super(errorCode,errorMessage, statusCode);
	}
	
	public CommonBffException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
	public CommonBffException(String errorMessage) {
		super("Common-Bff", errorMessage);
	}

}
