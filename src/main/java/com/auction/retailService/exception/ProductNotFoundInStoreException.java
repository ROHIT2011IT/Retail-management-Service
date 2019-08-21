package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessage;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFoundInStoreException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductNotFoundInStoreException(Long storeID, Long productID) {
		super(String.format(ErrorMessage.PRODUCT_NOT_FOUND_IN_STORE, productID, storeID));
	}

}
