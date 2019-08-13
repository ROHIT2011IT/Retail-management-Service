package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "User Role not exist")
public class RoleException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RoleException(String message) {
		super(message);
	}

}
