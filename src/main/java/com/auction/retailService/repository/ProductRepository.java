package com.auction.retailService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.retailService.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/*String FIND_BY_PRODUCT_NAME = " Select * from Product where product_name = :productName";
	
	String DELETE_BY_PRODUCT_NAME = " delete from Product where product_name = :productName";
	
	@Query(value = FIND_BY_PRODUCT_NAME, nativeQuery = true) 
	public List<Product> findByProductName(@Param ("productName") String productName);

	@Modifying
	@Query (value = DELETE_BY_PRODUCT_NAME)
	public void deleteByProductName(@Param ("productName") String productName);*/
	
	String DELETE_BY_PRODUCT_ID = " update Product set is_Deleted = 1 where product_id = :productId";
	
	@Modifying(clearAutomatically = true)
	@Query(value = DELETE_BY_PRODUCT_ID)
	public int softDeleteById(@Param("productId") Long id); 

	public List<Product> findBySkuNumber(String skuNumber);

	public Optional<List<Product>> findByProductName(String name);

	public void deleteByProductName(String name);


	public Optional<Product> findByProductNameAndCompanyNameAndSkuNumber(String productName, String companyName,
			String skuNumber);


}
