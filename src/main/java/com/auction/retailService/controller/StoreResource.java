package com.auction.retailService.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import com.auction.retailService.domain.StoreEntity;
import com.auction.retailService.dto.StoreProductDescription;
import com.auction.retailService.dto.StoreProducts;
import com.auction.retailService.exception.ProductNotFoundException;
import com.auction.retailService.exception.StoreNotFoundException;
import com.auction.retailService.exception.UserNotExistToPerformOperationException;
import com.auction.retailService.exception.UserUnAuthorizedException;
import com.auction.retailService.service.ProductService;
import com.auction.retailService.service.StoreProductService;
import com.auction.retailService.service.StoreService;
import com.auction.retailService.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Store Management API", description = "API for Store management")
@RestController
@RequestMapping("/retail/v1/stores")
public class StoreResource {

	@Autowired
	private StoreService storeService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService prodService;

	@Autowired
	private StoreProductService storeProductService;

	@ApiOperation(value = "Create Store")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Store is created Successfully"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Store Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping
	public ResponseEntity<StoreEntity> addStore(@RequestHeader(value = "userId", required = true) Long userId,
			@Valid @RequestBody StoreEntity store) {
		if (userId != null && userService.isUserExist(userId)) {
			return storeService.addStore(userId, store);
		}
		throw new UserNotExistToPerformOperationException(ErrorMessage.USER_NOT_EXIST_FOR_OPERATION);
	}

	@ApiOperation(value = "Get list of Stores")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Store is retrieved Successfully"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Store Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping
	public List<StoreEntity> getStores() {
		return storeService.getStores();
	}

	@ApiOperation(value = "Get Store by storeId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Store is retrieved Successfully by storeId"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Store Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/{storeId}")
	public Optional<StoreEntity> getStore(@PathVariable("storeId") Long storeId) {
		return storeService.getStoreById(storeId);
	}

	@ApiOperation(value = "Get Store by storeName")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Store is retrieved Successfully by storeName"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Store Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/name/{storeName}")
	public Optional<StoreEntity> getStoreByName(@PathVariable("storeName") @NotNull String storeName) {
		return storeService.getStoreByName(storeName);
	}

	@ApiOperation(value = "Delete Store by storeId")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Store has been deleted successfully"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Store Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@DeleteMapping("/{storeId}")
	public ResponseEntity<Void> deleteStoreById(@RequestHeader(value = "userId", required = true) Long userId,
			@PathVariable("storeId") Long storeId) {
		if (userId != null && userService.isUserExist(userId)) {
			if (userService.getUserRole(userId) == Role.RETAIL_ADMIN || userService.isStoreAdmin(userId, storeId)) {
				storeService.deleteStoreById(storeId);
				return ResponseEntity.noContent().build();
			} else {
				throw new UserUnAuthorizedException(ErrorMessage.USER_UNAUTHORIZED_EXCEPTION);
			}
		} else {
			throw new UserNotExistToPerformOperationException(ErrorMessage.USER_NOT_EXIST_FOR_OPERATION);
		}

	}

	@ApiOperation(value = "Delete Store by storeName")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Store has been deleted successfully"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Store Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@DeleteMapping("/name/{storeName}")
	public ResponseEntity<Void> deleteStoreByName(@RequestHeader(value = "userId", required = true) Long userId,
			@PathVariable("storeName") String storeName) {
		if (userId != null && userService.isUserExist(userId)) {
			storeService.deleteStoreByStoreName(storeName);
			return ResponseEntity.noContent().build();
		} else {
			throw new UserNotExistToPerformOperationException(ErrorMessage.USER_NOT_EXIST_FOR_OPERATION);
		}

	}

	@ApiOperation(value = "Add Products in Store")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product is added in store Successfully"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed store product Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping("/store-products")
	public StoreProducts addStoreProducts(@RequestHeader(value = "userId", required = true) Long userId,
			@Valid @RequestBody StoreProducts storeProducts) {
		Long storeId = storeProducts.getStoreId();
		if (storeService.isStoreExist(storeId)) {
			if (userId != null && userService.isStoreAdmin(userId, storeId)) {
				return storeProductService.addStoreProduct(storeProducts);
			} else {
				throw new UserUnAuthorizedException("User is not authorized to do this operaion");
			}
		}
		throw new StoreNotFoundException(storeId.toString());
	}

	@ApiOperation(value = "Get list of Products in Store by StoreId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Products retrieved Successfully of paticular store"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Store Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/{storeId}/products")
	public List<StoreProductDescription> getStoreProducts(@PathVariable("storeId") @NotNull Long storeId) {
		if (storeId != null && storeService.isStoreExist(storeId)) {
			return storeProductService.getStoreProducts(storeId);
		}
		throw new StoreNotFoundException(storeId.toString());
	}

	@ApiOperation(value = "Get Product in Store by StoreId and ProductId")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Products retrieved Successfully of paticular store and paticular Product"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed either store or product Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/{storeId}/products/{productId}")
	public StoreProductDescription getStoreProductById(@PathVariable("storeId") Long storeId,
			@PathVariable("productId") Long productId) {
		if (storeId != null && storeService.isStoreExist(storeId)) {
			if (productId != null && prodService.isProductExist(productId)) {
				return storeProductService.getStoreProductById(storeId, productId);
			} else {
				throw new ProductNotFoundException("Product Not found");
			}
		}
		throw new StoreNotFoundException(storeId.toString());
	}

}
