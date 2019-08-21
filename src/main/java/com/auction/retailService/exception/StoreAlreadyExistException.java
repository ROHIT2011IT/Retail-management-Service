package com.auction.retailService.exception;

import com.auction.retailService.constant.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = ErrorMessage.STORE_ALREDAY_EXIST)
public class StoreAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StoreAlreadyExistException(String message) {
		super(message);
	}

}
