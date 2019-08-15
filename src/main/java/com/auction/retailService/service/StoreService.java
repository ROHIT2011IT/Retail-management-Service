package com.auction.retailService.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.retailService.constant.ErrorMessageConstant;
import com.auction.retailService.constant.RoleConstant;
import com.auction.retailService.entity.Store;
import com.auction.retailService.exception.StoreAlreadyExistException;
import com.auction.retailService.exception.StoreNotFoundException;
import com.auction.retailService.exception.UserUnAuthorizedException;
import com.auction.retailService.repository.StoreProductRepository;
import com.auction.retailService.repository.StoreRepository;

@Transactional
@Service
public class StoreService {
	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	StoreProductRepository storePrdouctrepository;
	
	@Autowired
	UserService userService;
	
	/*public Store addStore(Store store) {
		Optional<Store> store1 = storeRepository.findByStoreName(store.getStoreName());
		if(!store1.isPresent()) {
			return storeRepository.save(store);
		}
		throw new StoreAlreadyExistException("Store is already exist");
	}*/
	
	public Store addStore(Long userId, Store store) {
		if(userService.getUserRole(userId) == RoleConstant.RETAIL_ADMIN) {
			store.setStoreID(null);
			store.setCreatedBy(userId);
			store.setCreatedDate(Instant.now()+"");
			store.setStoreName(store.getStoreName().toUpperCase());
			Optional<Store> store1 = storeRepository.findByStoreName(store.getStoreName());
			if(!store1.isPresent()) {
				return storeRepository.save(store);
			}
			throw new StoreAlreadyExistException(ErrorMessageConstant.STORE_ALREDAY_EXIST);
			
		}else
		{
			throw new UserUnAuthorizedException(ErrorMessageConstant.USER_UNAUTHORIZED_EXCEPTION);
		}
	}
	
	

	public List<Store> getStores() {
		return storeRepository.findAll();
	}

	public Optional<Store> getStoreById(Long storeId) {
		if(storeId!=null) {
			Optional<Store> stores = storeRepository.findById(storeId);
			if(stores.isPresent()) {
				return storeRepository.findById(storeId);
			}
		}
		throw new StoreNotFoundException("by storeID: "+storeId);
	}

	@Transactional
	public void deleteStoreById(Long storeId) {
		boolean isStoreexist = false;
		if(storeId != null) {
			Optional<Store> store = storeRepository.findById(storeId);
			if(store.isPresent()) {
				isStoreexist = true;
				storePrdouctrepository.deleteByStoreProductIdentityStoreId(storeId);		
				storeRepository.deleteById(storeId);
			}
		}if(!isStoreexist) {
			throw new StoreNotFoundException("by storeID: "+storeId);
		}
	}

	public boolean isStoreExist(Long storeId) {
		System.out.println("StoreId: "+storeId);
		Optional<Store> store = storeRepository.findById(storeId);
		if (store.isPresent()) {
			return true;
		}
		throw new StoreNotFoundException("by storeID: "+storeId);
	}

	public Optional<Store> getStoreByName(@NotNull String storeName) {
		Optional<Store> store = storeRepository.findByStoreName(storeName.toUpperCase());
		if(store.isPresent()) {
			return store;
		}
		throw new StoreNotFoundException("by storeName: "+storeName);
	}



	public void deleteStoreByStoreName(String storeName) {
		
		Optional<Store> store = storeRepository.findByStoreName(storeName.toUpperCase());
		if(store.isPresent()) {
			storeRepository.deleteByStoreName(storeName.toUpperCase());
		}else {
			throw new StoreNotFoundException("by storeName: "+storeName);
		}
		
	}

}
