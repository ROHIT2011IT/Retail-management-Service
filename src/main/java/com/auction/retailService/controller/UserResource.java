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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.retailService.domain.UserEntity;
import com.auction.retailService.exception.UserUnAuthorizedException;
import com.auction.retailService.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User Management API")
@RestController
@RequestMapping("/retail/v1/users")
class UserResource {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Create User")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "User is created Successfully"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed User Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping
	public ResponseEntity<UserEntity> addUser(
			@RequestHeader(value = "userId", required = false, defaultValue = "0") Long userId,
			@Valid @RequestBody UserEntity user) {

		return userService.addUser(userId, user);
	}

	@ApiOperation(value = "Get User List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User retrieve Successfully"),
			@ApiResponse(code = 400, message = "Input JSON is wrong"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed User Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping
	public List<UserEntity> getUser(@RequestHeader(value = "userId", required = true) Long userId) {
		return userService.getUsers(userId);
	}

	@ApiOperation(value = "Get User By UserId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User retrieve Successfully by UserID"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed User Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/{id}")
	public Optional<UserEntity> getUserById(
			@RequestHeader(value = "userId", required = false, defaultValue = "0") Long userId,
			@PathVariable("id") Long id) {
		return userService.getUser(id, userId);
	}

	@ApiOperation(value = "Get User By emailId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User retrieve Successfully by user EmailID"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed User Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/email/{emailId}")
	public UserEntity getUserByEmail(@PathVariable("emailId") String emailId) {
		return userService.getUserByEmailId(emailId);
	}

	@ApiOperation(value = "Delete User By UserId")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "User is deleted successfully"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed User Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletUserById(@RequestHeader(value = "userId", required = true) Long userId,
			@PathVariable("id") Long id) {
		if (userId != null && userId == id) {
			userService.deleteUserById(id);
			return ResponseEntity.noContent().build();
		} else {
			throw new UserUnAuthorizedException("User is not authorized for this operation");
		}
	}

	@ApiOperation(value = "Delete User By emailId")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "User has been deleted successfully"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed User Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@DeleteMapping("/emailId/{emailId}")
	public ResponseEntity<Void> deletUserByEmailId(@RequestHeader(value = "userId", required = true) Long userId,
			@PathVariable("emailId") String emailId) {
		userService.deleteUserByEmailId(emailId, userId);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Update User")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User updated Successfully"),
			@ApiResponse(code = 401, message = "User is Not authorized for this operation"),
			@ApiResponse(code = 412, message = "Validation failed User Data."),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PutMapping
	public UserEntity updateUser(@RequestHeader(value = "userId", required = true) Long userId,
			@Valid @RequestBody UserEntity user) {

		return userService.updateUserData(user, userId);
	}

}
