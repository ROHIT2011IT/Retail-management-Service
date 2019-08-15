package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value =HttpStatus.NOT_FOUND , reason = "Product data Exception/Product Not Found")
public class ProductDataException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ProductDataException(String message) {
		super(message);
	}

}
