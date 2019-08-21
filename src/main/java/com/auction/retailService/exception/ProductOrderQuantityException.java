package com.auction.retailService.exception;

import com.auction.retailService.constant.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = ErrorMessage.PRODUCT_OUT_OF_ORDER)
public class ProductOrderQuantityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductOrderQuantityException(Long storeId, Long productQty, Long orderQty) {
		super(String.format(ErrorMessage.PRODUCT_OUT_OF_ORDER_MESSAGE, storeId, productQty, orderQty));
	}

}
