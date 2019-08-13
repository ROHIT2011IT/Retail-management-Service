package com.auction.retailService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import com.auction.retailService.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

	String FIND_BY_STORE_NAME_QUERY = "Select * from Store where store_Name = UPPER(:store_Name)";
	
	@Query (value = FIND_BY_STORE_NAME_QUERY , nativeQuery = true)
	Optional<Store> findByStoreName(@Param ("store_Name") String storeName);

	void deleteByStoreName(String upperCase);

	
}
