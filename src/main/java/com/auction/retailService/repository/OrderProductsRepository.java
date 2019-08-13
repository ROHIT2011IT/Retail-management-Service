package com.auction.retailService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.retailService.entity.OrderProductsEntity;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProductsEntity, Long>{
	
	List<OrderProductsEntity> findByOrderId(Long orderId);

	void deleteByOrderId(Long orderId);
	
}
