package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND , reason = "Order not found with this data")
public class OrderNotFoundException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	
	public OrderNotFoundException (String message) {
		super(message);
	}

}
