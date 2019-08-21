package com.auction.retailService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auction.retailService.domain.StoreEntity;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

	Optional<StoreEntity> findByStoreNameIgnoreCase(String storeName);

	void deleteByStoreNameIgnoreCase(String storeName);

}
