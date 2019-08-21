package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessage;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = ErrorMessage.USER_NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
