package com.auction.retailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auction.retailService.constant.ErrorMessageConstant;

@ResponseStatus (value = HttpStatus.PRECONDITION_FAILED , reason = ErrorMessageConstant.PRODUCT_ALREADY_EXIST)
public class ProductAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ProductAlreadyExistException(String prodName, String skuNumber, String comapnyName) {
		super(ErrorMessageConstant.PRODUCT_ALREADY_EXIST+" productname: "+" sku Number: "+skuNumber+" companyname: "+comapnyName);
	}

}
