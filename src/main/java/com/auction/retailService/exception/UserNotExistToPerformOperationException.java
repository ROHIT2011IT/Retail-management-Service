package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessageConstant;

@ResponseStatus (value = HttpStatus.PRECONDITION_FAILED , reason = ErrorMessageConstant.USER_NOT_EXIST_FOR_OPERATION)
public class UserNotExistToPerformOperationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UserNotExistToPerformOperationException(String message) {
		super(message);
	}

}
