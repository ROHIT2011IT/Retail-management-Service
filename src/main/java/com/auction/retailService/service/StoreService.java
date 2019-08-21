package com.auction.retailService.service;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.auction.retailService.constant.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auction.retailService.constant.Role;
import com.auction.retailService.domain.StoreEntity;
import com.auction.retailService.exception.StoreAlreadyExistException;
import com.auction.retailService.exception.StoreNotFoundException;
import com.auction.retailService.exception.UserUnAuthorizedException;
import com.auction.retailService.repository.StoreProductRepository;
import com.auction.retailService.repository.StoreRepository;

@Transactional
@Service
public class StoreService {

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private StoreProductRepository storePrdouctrepository;

	@Autowired
	private UserService userService;

	public ResponseEntity<StoreEntity> addStore(Long userId, StoreEntity store) {
		if (userService.getUserRole(userId) == Role.RETAIL_ADMIN) {
			store.setStoreID(null);
			store.setCreatedBy(userId);
			store.setCreatedDate(Instant.now().toString());
			Optional<StoreEntity> store1 = storeRepository.findByStoreNameIgnoreCase(store.getStoreName());
			if (!store1.isPresent()) {
				store = storeRepository.save(store);
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{storeId}")
						.buildAndExpand(store.getStoreID()).toUri();
				return ResponseEntity.created(location).build();
			}
			throw new StoreAlreadyExistException(ErrorMessage.STORE_ALREDAY_EXIST);

		} else {
			throw new UserUnAuthorizedException(ErrorMessage.USER_UNAUTHORIZED_EXCEPTION);
		}
	}

	public List<StoreEntity> getStores() {
		return storeRepository.findAll();
	}

	public Optional<StoreEntity> getStoreById(Long storeId) {
		if (storeId != null) {
			Optional<StoreEntity> store = storeRepository.findById(storeId);
			if (store.isPresent()) {
				return storeRepository.findById(storeId);
			}
		}
		throw new StoreNotFoundException("by storeID: " + storeId);
	}

	@Transactional
	public void deleteStoreById(Long storeId) {
		boolean isStoreexist = false;
		if (storeId != null) {
			Optional<StoreEntity> store = storeRepository.findById(storeId);
			if (store.isPresent()) {
				isStoreexist = true;
				storePrdouctrepository.deleteByStoreProductIdentityStoreId(storeId);
				storeRepository.deleteById(storeId);
			}
		}
		if (!isStoreexist) {
			throw new StoreNotFoundException(storeId.toString());
		}
	}

	public boolean isStoreExist(Long storeId) {
		System.out.println("StoreId: " + storeId);
		Optional<StoreEntity> store = storeRepository.findById(storeId);
		if (store.isPresent()) {
			return true;
		}
		throw new StoreNotFoundException(storeId.toString());
	}

	public Optional<StoreEntity> getStoreByName(@NotNull String storeName) {
		Optional<StoreEntity> store = storeRepository.findByStoreNameIgnoreCase(storeName);
		if (store.isPresent()) {
			return store;
		}
		throw new StoreNotFoundException(storeName);
	}

	public void deleteStoreByStoreName(String storeName) {

		Optional<StoreEntity> store = storeRepository.findByStoreNameIgnoreCase(storeName);
		if (store.isPresent()) {
			storeRepository.deleteByStoreNameIgnoreCase(storeName);
		} else {
			throw new StoreNotFoundException("by storeName: " + storeName);

		}

	}

}
