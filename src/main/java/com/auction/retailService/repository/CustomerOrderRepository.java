package com.auction.retailService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auction.retailService.domain.CustomerOrderEntity;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrderEntity, Long> {

	List<CustomerOrderEntity> findByStoreId(Long storeId);

	List<CustomerOrderEntity> findByOrderBy(Long userId);

	Optional<CustomerOrderEntity> findByOrderIdAndOrderBy(Long orderId, Long orderBy);

}
