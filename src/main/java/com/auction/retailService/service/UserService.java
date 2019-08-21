package com.auction.retailService.service;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.auction.retailService.constant.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auction.retailService.constant.Role;
import com.auction.retailService.domain.UserEntity;
import com.auction.retailService.exception.RoleException;
import com.auction.retailService.exception.UserAlreadyExistException;
import com.auction.retailService.exception.UserDataException;
import com.auction.retailService.exception.UserNotExistToPerformOperationException;
import com.auction.retailService.exception.UserNotFoundException;
import com.auction.retailService.exception.UserUnAuthorizedException;
import com.auction.retailService.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private StoreService storeService;

	public ResponseEntity<UserEntity> addUser(Long userId, UserEntity user) {
		user.setUserId(null);
		user.setAddedDate(Instant.now().toString());
		int roleId = user.getRoleId();

		switch (roleId) {
		case Role.CUSTOMER:
			user.setStoreId(null);
			break;
		case Role.RETAIL_ADMIN:
			if (isUserExist(userId)) {
				if (userId != null && getUserRole(userId) == Role.RETAIL_ADMIN) {
					user.setAddedBy(userId);
					user.setStoreId(null);
					break;
				} else {
					throw new UserUnAuthorizedException(ErrorMessage.USER_UNAUTHORIZED_EXCEPTION);
				}
			} else {
				throw new UserNotExistToPerformOperationException(ErrorMessage.USER_NOT_EXIST_FOR_OPERATION);
			}
		case Role.STORE_ADMIN:
			if (isUserExist(userId)) {
				if (userId != null
						&& ((getUserRole(userId) == Role.RETAIL_ADMIN) || (getUserRole(userId) == Role.STORE_ADMIN))
						&& storeService.isStoreExist(user.getStoreId())) {
					user.setAddedBy(userId);
					break;
				} else {
					throw new UserUnAuthorizedException(ErrorMessage.USER_UNAUTHORIZED_EXCEPTION);
				}
			} else {
				throw new UserNotExistToPerformOperationException(ErrorMessage.USER_NOT_EXIST_FOR_OPERATION);
			}
		default:
			throw new RoleException(ErrorMessage.USER_ALREADY_EXIST);
		}
		Optional<UserEntity> userbyEmail = userRepo.findByEmailIdIgnoreCase(user.getEmailId().toUpperCase());
		if (!userbyEmail.isPresent()) {
			user = userRepo.save(user);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(user.getUserId()).toUri();

			return ResponseEntity.created(location).build();
		}
		throw new UserAlreadyExistException(ErrorMessage.USER_ALREADY_EXIST);
	}

	public List<UserEntity> getUsers(Long userId) {
		int userRole = getUserRole(userId);
		if (userRole == Role.RETAIL_ADMIN || userRole == Role.STORE_ADMIN) {
			return userRepo.findAll();
		}
		throw new UserUnAuthorizedException("User is not authorized for this operation");
	}

	public Optional<UserEntity> getUser(Long Id, Long userId) {
		if (Id != null) {
			Optional<UserEntity> userEntity = userRepo.findById(Id);
			if (userEntity.isPresent()) {
				if (userEntity.get().getRoleId() != Role.CUSTOMER) {
					Optional<UserEntity> adminUser = userRepo.findById(userId);
					if (adminUser.isPresent() && adminUser.get().getRoleId() != Role.CUSTOMER) {
						return userEntity;
					} else {
						throw new UserUnAuthorizedException(ErrorMessage.USER_UNAUTHORIZED_EXCEPTION);
					}
				}
				return userEntity;
			}
		}
		throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
	}

	public UserEntity getUserByEmailId(String emailId) {
		if (emailId != null) {

			return userRepo.findByEmailIdIgnoreCase(emailId)
					.orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
		}
		throw new UserDataException(ErrorMessage.INVALID_USER_DATA);
	}

	public void deleteUserById(Long id) {
		if (id != null) {
			Optional<UserEntity> user = userRepo.findById(id);
			if (user.isPresent()) {
				userRepo.deleteById(id);
			} else {
				throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
			}
		}
	}

	public void deleteUserByEmailId(String emailId, Long userId) {
		if (userId != null && emailId != null) {
			Optional<UserEntity> user = userRepo.findByEmailId(emailId);
			if (user.isPresent() && user.get().getUserId() == userId) {
				userRepo.deleteByEmailIdIgnoreCase(emailId.toUpperCase());
			} else {
				throw new UserDataException("User Data Invalid to delete");
			}
		} else {
			throw new UserDataException(ErrorMessage.INVALID_USER_DATA);
		}
	}

	public int getUserRole(Long userId) {
		if (userId != null) {
			Optional<UserEntity> user = userRepo.findById(userId);
			if (user.isPresent()) {
				return user.get().getRoleId();
			}
		}
		throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
	}

	public boolean isStoreAdmin(Long userId, Long storeId) {
		if (userId != null && storeId != null) {
			Optional<UserEntity> user = userRepo.findByUserIdAndStoreId(userId, storeId);
			return user.isPresent();
		}
		throw new UserDataException(ErrorMessage.INVALID_USER_DATA);
	}

	public boolean isUserExist(Long userId, String emailId) {
		Optional<UserEntity> user = userRepo.findByUserIdAndEmailIdIgnoreCase(userId, emailId);
		return user.isPresent();
	}

	public UserEntity updateUserData(@Valid UserEntity user, Long userId) {
		if (isUserExist(userId, user.getEmailId())) {
			user.setUserId(userId);
			return userRepo.save(user);
		}
		throw new UserNotFoundException("User not found with these details");
	}

	public boolean isUserExist(@NotNull Long userId) {
		System.out.println("userId: " + userId);
		Optional<UserEntity> user = userRepo.findById(userId);
		return user.isPresent();

	}

	public boolean isUserStoreAdmin(Long storeId, Long userId) {
		Optional<UserEntity> user = userRepo.findByUserIdIdAndStoreIdAndRoleId(userId, storeId, Role.RETAIL_ADMIN);

		return user.isPresent();
	}

}
