package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessageConstant;

@ResponseStatus (value = HttpStatus.PRECONDITION_FAILED , reason = ErrorMessageConstant.PRODUCT_NOT_FPOUND)
public class ProductNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ProductNotFoundException(String message) {
		super("Product not found +"+message);
	}

}
