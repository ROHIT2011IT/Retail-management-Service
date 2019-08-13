package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessageConstant;

@ResponseStatus (value = HttpStatus.PRECONDITION_FAILED, reason = ErrorMessageConstant.PRODUCT_OUT_OF_ORDER)
public class ProductOrderQuantityException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ProductOrderQuantityException(Long storeId, Long productQty, Long orderQty) {
		super("Available Product Quantity in store: "+storeId+" is "+productQty+", and you ordered quantity is: "+orderQty);
	}

}
