package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessageConstant;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorMessageConstant.STORE_NOT_FOUND)
public class StoreNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public StoreNotFoundException(String message) {
		super(ErrorMessageConstant.STORE_NOT_FOUND+" "+message);
	}

}
