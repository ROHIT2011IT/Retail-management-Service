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

import com.auction.retailService.constant.RoleConstant;
import com.auction.retailService.entity.User;
import com.auction.retailService.exception.UserNotFoundException;
import com.auction.retailService.exception.UserUnAuthorizedException;
import com.auction.retailService.service.StoreService;
import com.auction.retailService.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User Management API")
@RestController
@RequestMapping("/retail/v1")
class UserResource {
	
	@Autowired
	UserService userService;
	
	@Autowired
	StoreService storeService;
	@ApiOperation(value = "Create User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User is created Successfully"),
            @ApiResponse(code = 400, message = "Input JSON is wrong"),
            @ApiResponse(code =401 , message = "User is Not authorized for this operation"),
            @ApiResponse(code = 412, message = "Validation failed User Data."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
	@PostMapping("/users")
	public User addUser(@RequestHeader(value="userId" , required = false, defaultValue = "0") Long userId, @Valid @RequestBody User user) {
		
		user.setEmailId(user.getEmailId().toUpperCase());
		user.setUserId(null);
		return userService.addUser(userId,user);
	}
	
	@ApiOperation(value = "Get User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User retrieve Successfully"),
            @ApiResponse(code = 400, message = "Input JSON is wrong"),
            @ApiResponse(code =401 , message = "User is Not authorized for this operation"),
            @ApiResponse(code = 412, message = "Validation failed User Data."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
	@GetMapping("/users")
	public List<User> getUser(@RequestHeader(value="userId" , required = true) Long userId) {
		int userRole = userService.getUserRole(userId);
		if( userRole == RoleConstant.RETAIL_ADMIN || userRole == RoleConstant.STORE_ADMIN ) {
			return userService.getUsers();
		}
		throw new UserUnAuthorizedException("User is not authorized for this operation");
	}
	
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "User retrieve Successfully by UserID"),
            @ApiResponse(code =401 , message = "User is Not authorized for this operation"),
            @ApiResponse(code = 412, message = "Validation failed User Data."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
	@GetMapping("/users/id/{id}")
	public Optional<User> getUserById(@RequestHeader(value="userId" , required = false , defaultValue ="0") Long userId ,@PathVariable ("id") Long id) {
		return userService.getUser(id , userId); 
	}
	
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "User retrieve Successfully by UserName"),
            @ApiResponse(code =401 , message = "User is Not authorized for this operation"),
            @ApiResponse(code = 412, message = "Validation failed User Data."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
	@GetMapping("/users/name/{name}")
	public List<User> getUserByName(@PathVariable ("name") String name) {
		return userService.getUserByName(name);
	}
	
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "User retrieve Successfully by user EmailID"),
            @ApiResponse(code =401 , message = "User is Not authorized for this operation"),
            @ApiResponse(code = 412, message = "Validation failed User Data."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
	@GetMapping("/users/email/{emailId}")
	public Optional<User> getUserByEmail(@PathVariable ("emailId") String emailId) {
		return userService.getUserByEmailId(emailId);
	}
	
	@ApiResponses(value = {
            @ApiResponse(code =401 , message = "User is Not authorized for this operation"),
            @ApiResponse(code = 412, message = "Validation failed User Data."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
	@DeleteMapping("/users/id/{id}")
	public ResponseEntity<Void> deletUserById(@RequestHeader(value="userId" , required = true) Long userId , @PathVariable ("id") Long id) {
		if(userId !=null && userId == id) {			
			userService.deleteUserById(id); 
			return ResponseEntity.noContent().build();
		}
		else {
			throw new UserUnAuthorizedException("User is not authorized for this operation");
		}
	}
	
	@ApiResponses(value = {
            @ApiResponse(code =401 , message = "User is Not authorized for this operation"),
            @ApiResponse(code = 412, message = "Validation failed User Data."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
	@DeleteMapping("/users/emailId/{emailId}")
	public ResponseEntity<Void> deletUserByEmailId(@RequestHeader(value="userId" , required = true) Long userId ,@PathVariable ("emailId") String emailId) {
		userService.deleteUserByEmailId(emailId, userId); 
		return ResponseEntity.noContent().build();
	}
	
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "User updated Successfully"),
            @ApiResponse(code =401 , message = "User is Not authorized for this operation"),
            @ApiResponse(code = 412, message = "Validation failed User Data."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
	@PutMapping("/users")
	public User updateUser(@RequestHeader(value="userId" , required = true) Long userId , @Valid @RequestBody User user) {
		if((userService.isUserExist(user.getUserId(), user.getEmailId().toUpperCase()))&& (userId!=null && user.getUserId() == userId)) {
			return userService.updateUserData(user);
		}
		throw new UserNotFoundException("User not found with these details");
		
	}
	
}
