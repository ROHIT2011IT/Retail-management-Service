package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessageConstant;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = ErrorMessageConstant.USER_ALREADY_EXIST)
public class UserAlreadyExistException extends RuntimeException{
	
	public UserAlreadyExistException(String message) {
		super(message);
	}

}
