package com.auction.retailService.controller;

import java.util.List;

import javax.validation.Valid;

import com.auction.retailService.constant.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.retailService.constant.Role;
import com.auction.retailService.domain.ProductEntity;
import com.auction.retailService.exception.UserNotExistToPerformOperationException;
import com.auction.retailService.exception.UserUnAuthorizedException;
import com.auction.retailService.service.ProductService;
import com.auction.retailService.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Product Management API", description = "API for Product management")
@RestController
@RequestMapping("/retail/v1/products")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Create Product")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Product is created Successfully"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Product Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping
	public ResponseEntity<ProductEntity> addProduct(@RequestHeader(value = "userId", required = true) Long userId,
			@Valid @RequestBody ProductEntity product) {
		if (userService.isUserExist(userId)) {
			if (userService.getUserRole(userId) == Role.RETAIL_ADMIN) {
				return productService.addProduct(product);
			}
			throw new UserUnAuthorizedException(ErrorMessage.USER_UNAUTHORIZED_EXCEPTION);
		}
		throw new UserNotExistToPerformOperationException(ErrorMessage.USER_NOT_EXIST_FOR_OPERATION);
	}

	@ApiOperation(value = "Get list of Products")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product is retrieved Successfully"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Product Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping
	public List<ProductEntity> getProducts() {
		return productService.getProducts();
	}

	@ApiOperation(value = "Get Product By ProductId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product is retrieved Successfully by productId"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Product Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/{productId}")
	public ProductEntity getProductById(@PathVariable("productId") Long id) {
		return productService.getProductById(id);
	}

	@ApiOperation(value = "Get list of Products  by ProductName")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product is retreived Successfully by productName"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Product Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/name/{productName}")
	public List<ProductEntity> getProductByName(@PathVariable("productName") String name) {
		return productService.getProductByName(name);
	}

	@ApiOperation(value = "Delete Product by ProductId")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Product has been deleted successfully"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Product Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deleteProductById(@RequestHeader(value = "userId", required = true) Long userId,
			@PathVariable("productId") Long id) {
		if (userService.isUserExist(userId)) {
			if (userService.getUserRole(userId) == Role.RETAIL_ADMIN) {
				productService.deleteProductById(id);
				return ResponseEntity.noContent().build();
			}
			throw new UserUnAuthorizedException(ErrorMessage.USER_UNAUTHORIZED_EXCEPTION);
		} else {
			throw new UserNotExistToPerformOperationException(ErrorMessage.USER_NOT_EXIST_FOR_OPERATION);
		}
	}

}
