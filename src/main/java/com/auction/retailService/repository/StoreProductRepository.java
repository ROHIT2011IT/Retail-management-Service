package com.auction.retailService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.retailService.domain.StoreProductEntity;
import com.auction.retailService.domain.StoreProductEmbeddable;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProductEntity, StoreProductEmbeddable> {

	String QTY_BY_STOREID_AND_PRODUCTID = "Select * from store_product where store_id = :storeId and product_id= :productId";

	List<StoreProductEntity> findByStoreProductIdentityStoreId(Long storeId);

	void deleteByStoreProductIdentityStoreId(Long storeId);

	@Query(value = QTY_BY_STOREID_AND_PRODUCTID, nativeQuery = true)
	Optional<StoreProductEntity> findQuantityByStoreIdAndProductId(@Param("storeId") Long storeID,
			@Param("productId") Long productId);

}
