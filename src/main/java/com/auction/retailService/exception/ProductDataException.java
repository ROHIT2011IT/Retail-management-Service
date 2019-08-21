package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessage;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorMessage.PRODUCT_NOT_FOUND)
public class ProductDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductDataException(String message) {
		super(message);
	}

}
