package com.auction.retailService.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.auction.retailService.constant.ErrorMessageConstant;
import com.auction.retailService.constant.RoleConstant;
import com.auction.retailService.entity.Product;
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
@RequestMapping("/retail/v1")
public class ProductResource {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@ApiOperation(value = "Create Product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product is created Successfully"),
            @ApiResponse(code = 400, message = "Input JSON is wrong"),
            @ApiResponse(code =401 , message = "User is Not authorized for this operation"),
            @ApiResponse(code = 412, message = "Validation failed Product Data."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
	@PostMapping("/products")
	public Product addProduct(@RequestHeader (value="userId" , required = true) Long userId , @Valid @RequestBody Product product) {
		if(userService.isUserExist(userId)) {
			if(userService.getUserRole(userId) == RoleConstant.RETAIL_ADMIN) {
				return productService.addProduct(product);
			}
			throw new UserUnAuthorizedException(ErrorMessageConstant.USER_UNAUTHORIZED_EXCEPTION);
		}
		throw new UserNotExistToPerformOperationException(ErrorMessageConstant.USER_NOT_EXIST_FOR_OPERATION);
	}

	@ApiOperation(value = "Get list of Products")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product is retrieved Successfully"),
            @ApiResponse(code = 400, message = "Input JSON is wrong"),
            @ApiResponse(code =401 , message = "User is Not authorized for this operation"),
            @ApiResponse(code = 412, message = "Validation failed Product Data."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
	@GetMapping("/products")
	public List<Product> getProducts()
	{
		return productService.getProducts();
	}
	
	@ApiOperation(value = "Get Product By ProductId")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product is retrieved Successfully by productId"),
            @ApiResponse(code = 400, message = "Input JSON is wrong"),
            @ApiResponse(code =401 , message = "User is Not authorized for this operation"),
            @ApiResponse(code = 412, message = "Validation failed Product Data."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
	@GetMapping("products/id/{productId}")
	public Optional<Product> getProductById(@PathVariable ("productId") Long id) {
		return productService.getProductById(id);
	}
	
	@ApiOperation(value = "Get list of Products  by ProductName")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product is retreived Successfully by productName"),
            @ApiResponse(code = 400, message = "Input JSON is wrong"),
            @ApiResponse(code =401 , message = "User is Not authorized for this operation"),
            @ApiResponse(code = 412, message = "Validation failed Product Data."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
	@GetMapping("/products/name/{productName}")
	public List<Product> getProductByName(@PathVariable ("productName") String name) {
		return productService.getProductByName(name);
	}
	
	@ApiOperation(value = "Delete Product by ProductId")
	@ApiResponses(value = {
            @ApiResponse(code =401 , message = "User is Not authorized for this operation"),
            @ApiResponse(code = 412, message = "Validation failed Product Data."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
	@DeleteMapping("/products/id/{productId}")
	public ResponseEntity<Void> deleteProductById(@RequestHeader (value="userId" , required = true) Long userId  ,@PathVariable ("productId") Long id) {
		if(userService.isUserExist(userId)) {
			if(userService.getUserRole(userId) == RoleConstant.RETAIL_ADMIN) {
				productService.deleteProductById(id);
				return ResponseEntity.noContent().build();
			}
			throw new UserUnAuthorizedException(ErrorMessageConstant.USER_UNAUTHORIZED_EXCEPTION);
		}else {
			throw new UserNotExistToPerformOperationException(ErrorMessageConstant.USER_NOT_EXIST_FOR_OPERATION);
		}
	}
	
	/*@DeleteMapping("/products/name/{productName}")
	public void deleteProductByName(@PathVariable ("productName") String name) {
		productService.deleteProductByName(name);
	}*/
}
