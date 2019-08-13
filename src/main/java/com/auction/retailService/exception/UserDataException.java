package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessageConstant;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = ErrorMessageConstant.INVALID_USER_DATA)
public class UserDataException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserDataException(String message) {
		super(message);
	}
}
