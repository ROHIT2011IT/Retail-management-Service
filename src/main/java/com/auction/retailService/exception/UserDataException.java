package com.auction.retailService.exception;

import com.auction.retailService.constant.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = ErrorMessage.INVALID_USER_DATA)
public class UserDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserDataException(String message) {
		super(message);
	}
}
