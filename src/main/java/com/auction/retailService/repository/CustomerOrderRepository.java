package com.auction.retailService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auction.retailService.entity.CustomerOrder;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>{

	List<CustomerOrder> findByStoreId(Long storeId);

	List<CustomerOrder> findByOrderBy(Long userId);


	Optional<CustomerOrder> findByOrderIdAndOrderBy(Long orderId, Long orderBy);

}
