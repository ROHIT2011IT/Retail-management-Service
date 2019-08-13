package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessageConstant;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = ErrorMessageConstant.STORE_ALREDAY_EXIST)
public class StoreAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public StoreAlreadyExistException(String message) {
		super(message);
	}

}
