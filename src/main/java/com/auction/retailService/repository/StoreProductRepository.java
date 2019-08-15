package com.auction.retailService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.retailService.entity.StoreProduct;
import com.auction.retailService.entity.StoreProductEmbeddable;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, StoreProductEmbeddable>{
	
	String QTY_BY_STOREID_AND_PRODUCTID = "Select * from Store_Product where store_id = :storeId and product_id= :productId";
	
	List<StoreProduct> findByStoreProductIdentityStoreId(Long storeId);

	void deleteByStoreProductIdentityStoreId(Long storeId);

	@Query (value = QTY_BY_STOREID_AND_PRODUCTID, nativeQuery= true)
	Optional<StoreProduct> findQuantityByStoreIdAndProductId(@Param ("storeId") Long storeID, @Param ("productId") Long productId);


}
