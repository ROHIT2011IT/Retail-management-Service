package com.auction.retailService.controller;

import java.util.List;

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

import com.auction.retailService.dto.CustomerOrder;
import com.auction.retailService.dto.CustomerOrderDetail;
import com.auction.retailService.exception.InvalidDataException;
import com.auction.retailService.exception.UserNotExistToPerformOperationException;
import com.auction.retailService.service.CustomerOrderService;
import com.auction.retailService.service.StoreService;
import com.auction.retailService.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "CustomerOrder Management API")
@RestController
@RequestMapping("/retail/v1")
public class CustomerOrderResource {

	@Autowired
	private CustomerOrderService customerOrderService;

	@Autowired
	private UserService userSservice;

	@Autowired
	private StoreService storeService;

	@ApiOperation(value = "Create Order")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Order is created Successfully"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Order Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping("/orders")
	public ResponseEntity<CustomerOrder> addOrder(@RequestHeader(value = "userId", required = true) Long userId,
			@RequestBody CustomerOrder order) {
		if (userId != null && userSservice.isUserExist(userId) && storeService.isStoreExist(order.getStoreId())) {
			return customerOrderService.addOrder(order, userId);
		}
		throw new UserNotExistToPerformOperationException(ErrorMessage.USER_NOT_EXIST_FOR_OPERATION);
	}

	@ApiOperation(value = "Get Order List by storeId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OrderList retrieved Successfully by storeID"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Order Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("stores/{storeId}/orders")
	public List<CustomerOrderDetail> getOrdersByStoreId(@RequestHeader(value = "userId") Long userId,
			@PathVariable("storeId") Long storeId) {
		if (userId != null && storeId != null) {
			return customerOrderService.getOrdersByStoreId(storeId, userId);
		}
		throw new InvalidDataException("userId and storeId should not be null");
	}

	@ApiOperation(value = "Get OrderList of user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OrderList retrieved Successfully by userID"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Order Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("users/{userId}/orders")
	public List<CustomerOrderDetail> getOrdersByUserId(@PathVariable("userId") Long userId) {
		if (userId != null) {
			return customerOrderService.getOrdersByUserId(userId);
		}
		throw new InvalidDataException("Please provide valid userId");
	}

	@ApiOperation(value = "Get Order List by orderID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OrderList retrieved Successfully by orderID"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Order Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/orders/{orderId}")
	public CustomerOrderDetail getOrdersByOrderId(@RequestHeader(value = "userId", required = true) Long userId,
			@PathVariable("orderId") Long orderId) {
		if (userId != null && orderId != null) {
			return customerOrderService.getOrderByOrderId(orderId, userId);
		}
		throw new InvalidDataException("Please provide the valid userId and orderId");
	}

	@ApiOperation(value = "Delete Order by orderID")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Order has been deleted successfully"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed Order Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@DeleteMapping("/orders/{orderId}")
	public ResponseEntity<Void> deleteUserOrders(@RequestHeader(value = "userId") Long userId,
			@PathVariable("orderId") Long orderId) {
		if (userId != null && orderId != null) {
			customerOrderService.deleteByOrderId(orderId, userId);
			return ResponseEntity.noContent().build();
		}
		throw new InvalidDataException("Please provide the valid userId and orderId");
	}

}
