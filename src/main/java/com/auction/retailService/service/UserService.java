package com.auction.retailService.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.retailService.constant.ErrorMessageConstant;
import com.auction.retailService.constant.RoleConstant;
import com.auction.retailService.entity.Role;
import com.auction.retailService.entity.Store;
import com.auction.retailService.entity.User;
import com.auction.retailService.exception.RoleException;
import com.auction.retailService.exception.StoreNotFoundException;
import com.auction.retailService.exception.UserAlreadyExistException;
import com.auction.retailService.exception.UserDataException;
import com.auction.retailService.exception.UserNotExistToPerformOperationException;
import com.auction.retailService.exception.UserNotFoundException;
import com.auction.retailService.exception.UserUnAuthorizedException;
import com.auction.retailService.repository.RoleRepository;
import com.auction.retailService.repository.StoreRepository;
import com.auction.retailService.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	StoreService storeService;
	
	public User addUser(Long userId, User user) {
		user.setEmailId(user.getEmailId().toUpperCase());
		user.setUserId(null);
		user.setAddedDate(Instant.now()+"");
		int roleId = user.getRoleId();
		
		switch(roleId) {
		case RoleConstant.CUSTOMER :
			user.setStoreId(null);
			break;
		case RoleConstant.RETAIL_ADMIN :
			if(isUserExist(userId)){
				if(userId!=null && getUserRole(userId) == RoleConstant.RETAIL_ADMIN) {
					user.setAddedBy(userId);
					user.setAddedDate(Instant.now()+"");
					user.setStoreId(null);
					break;
				}else {
					throw new UserUnAuthorizedException(ErrorMessageConstant.USER_UNAUTHORIZED_EXCEPTION);
				}	
			}
			else {
				throw new UserNotExistToPerformOperationException(ErrorMessageConstant.USER_NOT_EXIST_FOR_OPERATION);
			}
		case RoleConstant.STORE_ADMIN:
			if(isUserExist(userId)) {
				if(userId !=null && ((getUserRole(userId) == RoleConstant.RETAIL_ADMIN) || 
						(getUserRole(userId) == RoleConstant.STORE_ADMIN)) 
						&& storeService.isStoreExist(user.getStoreId())) {
					user.setAddedBy(userId);
					user.setAddedDate(Instant.now()+"");
					break;
				}else {
					throw new UserUnAuthorizedException(ErrorMessageConstant.USER_UNAUTHORIZED_EXCEPTION);
				}
			}else {
				throw new UserNotExistToPerformOperationException(ErrorMessageConstant.USER_NOT_EXIST_FOR_OPERATION);
			}
			default:
				throw new RoleException(ErrorMessageConstant.USER_ALREADY_EXIST);
		}
		Optional<User> userbyEmail = userRepo.findByEmailId(user.getEmailId().toUpperCase());
		if(!userbyEmail.isPresent()) {
			return userRepo.save(user);
		}
		throw new UserAlreadyExistException(ErrorMessageConstant.USER_ALREADY_EXIST);
	}
	
	public List<User> getUsers(){
		return userRepo.findAll();
	}
	
	public Optional<User> getUser (Long Id, Long userId) {
		if(Id != null) {
		 Optional<User> user = userRepo.findById(Id);
			if(user.isPresent()) {
				if(user.get().getRoleId() != RoleConstant.CUSTOMER )
				{
					Optional<User> adminUser = userRepo.findById(userId);
					if(adminUser.isPresent() && adminUser.get().getRoleId() != RoleConstant.CUSTOMER) {
						return user;
					}
					else {
						throw new UserUnAuthorizedException(ErrorMessageConstant.USER_UNAUTHORIZED_EXCEPTION);
					}
				}
				return user;
			}
		}
		throw new UserNotFoundException(ErrorMessageConstant.USER_NOT_FOUND);
	}

	public List<User> getUserByName(String name) {
		return userRepo.findByFirstName(name);
	}
	
	public Optional<User> getUserByEmailId(String emailId){
		if(emailId !=null) {
			Optional<User> user = userRepo.findByEmailId(emailId.toUpperCase());
			if(user.isPresent()) {
				return user;
			}else {
				throw new UserNotFoundException(ErrorMessageConstant.USER_NOT_FOUND);
			}
		}
		throw new UserDataException(ErrorMessageConstant.INVALID_USER_DATA);
	}

	public void deleteUserById(Long id) {
		if(id !=null) {
			Optional<User> user = userRepo.findById(id);
			if(user.isPresent()) {
				userRepo.deleteById(id);
			}else
			{
				throw new UserNotFoundException(ErrorMessageConstant.USER_NOT_FOUND);
			}
		}
	}
	
	public void deleteUserByEmailId(String emailId, Long userId) {
		if(userId !=null && emailId != null) {
			Optional<User> user = userRepo.findByEmailId(emailId);
			if(user.isPresent() && user.get().getUserId() == userId) {				
				userRepo.deleteByEmailId(emailId.toUpperCase());
			}else {
				throw new UserDataException("User Data Invalid to delete");
			}
		}else
		{
			throw new UserDataException(ErrorMessageConstant.INVALID_USER_DATA);
		}
	}
	
	public int getUserRole(Long userId) {
		System.out.println("user Id:  ----------"+userId);
		if(userId != null) {
			Optional<User> user = userRepo.findById(userId);
			if(user.isPresent()) {
				return user.get().getRoleId();
			}	
		}
		throw new UserNotFoundException(ErrorMessageConstant.USER_NOT_FOUND);
	}

	public boolean isStoreAdmin(Long userId,Long storeId) {
		if(userId !=null && storeId != null) {
			Optional<User> user = userRepo.findByUserIdAndStoreId(userId,storeId);
			return user.isPresent();
		}
		throw new UserDataException(ErrorMessageConstant.INVALID_USER_DATA);
	}

	public boolean isUserExist(Long userId, String emailId) {
		Optional<User> user = userRepo.findByUserIdAndEmailId(userId, emailId);
		return user.isPresent();
	}

	public User updateUserData(@Valid User user) {
		return userRepo.save(user);
	}

	public boolean isUserExist(@NotNull Long userId) {
		System.out.println("userId: "+userId);
		Optional<User> user = userRepo.findById(userId);
		return user.isPresent();
		
	}

	public boolean isUserStoreAdmin(Long storeId, Long userId) {
		Optional<User> user = userRepo.findByUserIdIdAndStoreIdAndRoleId(userId,storeId,2);
		
		return user.isPresent();
	}
	
}
