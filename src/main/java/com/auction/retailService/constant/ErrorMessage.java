package com.auction.retailService.constant;

public class ErrorMessage {

	public static final String USER_UNAUTHORIZED_EXCEPTION = "Use is not authorized for this operation";
	public static final String USER_ALREADY_EXIST = "User is alreday exist";
	public static final String USER_NOT_FOUND = "User not found with the given data";
	public static final String INVALID_USER_DATA = "Invalid User Data";
	public static final String USER_NOT_EXIST_FOR_OPERATION = "User Not exist for this operation";
	public static final String PRODUCT_ALREADY_EXIST = "Product is already exist with productName: %s and companyName: %s and skuNumber:  %s";
	public static final String PRODUCT_NOT_FOUND = "Product not found with given data";
	public static final String STORE_ALREDAY_EXIST = "Store is already Exist";
	public static final String STORE_NOT_FOUND = "%s Store not found with the given data";
	public static final String PRODUCT_NOT_FOUND_IN_STORE = "Product %d not found in store %d";
	public static final String PRODUCT_OUT_OF_ORDER = "Product out of stock";
	public static final String PRODUCT_OUT_OF_ORDER_MESSAGE = "Available Product Quantity in store: %d is %d , and you ordered quantity is: %d";
}
