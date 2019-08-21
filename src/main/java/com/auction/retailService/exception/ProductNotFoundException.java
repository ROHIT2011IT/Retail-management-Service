package com.auction.retailService.exception;

import com.auction.retailService.constant.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = ErrorMessage.PRODUCT_NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String message) {
		super(String.format(ErrorMessage.PRODUCT_NOT_FOUND, message));
	}

}
