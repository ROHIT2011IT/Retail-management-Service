package com.auction.retailService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.retailService.domain.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	final String DELETE_BY_PRODUCT_ID = " update ProductEntity set is_Deleted = true where product_id = :productId";

	@Modifying(clearAutomatically = true)
	@Query(value = DELETE_BY_PRODUCT_ID)
	public int softDeleteById(@Param("productId") Long id);

	public List<ProductEntity> findBySkuNumber(String skuNumber);

	public Optional<List<ProductEntity>> findByProductName(String name);

	public Optional<ProductEntity> findByProductNameAndCompanyNameAndSkuNumber(String productName, String companyName,
			String skuNumber);

	public Optional<List<ProductEntity>> findByProductNameIgnoreCase(String upperCase);

	public void deleteByProductNameIgnoreCase(String name);

	public Optional<ProductEntity> findByProductNameIgnoreCaseAndCompanyNameIgnoreCaseAndSkuNumber(String productName,
			String companyName, String skuNumber);

}
