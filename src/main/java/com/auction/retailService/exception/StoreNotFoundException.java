package com.auction.retailService.exception;

import com.auction.retailService.constant.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorMessage.STORE_NOT_FOUND)
public class StoreNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StoreNotFoundException(String storeId) {
		super(String.format(ErrorMessage.STORE_NOT_FOUND, storeId));
	}

}
