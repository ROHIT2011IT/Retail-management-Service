package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessageConstant;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = ErrorMessageConstant.USER_UNAUTHORIZED_EXCEPTION)
public class UserUnAuthorizedException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

	public UserUnAuthorizedException(String message) {
        super(message);
    }
}

