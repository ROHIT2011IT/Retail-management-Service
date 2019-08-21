package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessage;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = ErrorMessage.USER_ALREADY_EXIST)
public class UserAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(String message) {
		super(message);
	}

}
