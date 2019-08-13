package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessageConstant;

@ResponseStatus (value = HttpStatus.NOT_FOUND)
public class ProductNotFoundInStoreException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ProductNotFoundInStoreException(String message, Long storeID, Long productID) {
		super("Product: "+productID+" not found in store :"+storeID);
	}

}
