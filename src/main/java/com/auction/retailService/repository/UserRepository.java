package com.auction.retailService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.auction.retailService.domain.UserEntity;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	final String USER_BY_USERID_STOREID_ROLEID = "Select * from user where user_id = :userId and store_id = :storeId and role_id = :roleId";

	Optional<UserEntity> findByEmailId(String upperCase);

	Optional<UserEntity> findByUserIdAndStoreId(Long userId, Long storeId);

	@Query(value = USER_BY_USERID_STOREID_ROLEID, nativeQuery = true)
	Optional<UserEntity> findByUserIdIdAndStoreIdAndRoleId(@Param("userId") Long userId, @Param("storeId") Long storeId,
			@Param("roleId") int roleId);

	Optional<UserEntity> findByEmailIdIgnoreCase(String upperCase);

	Optional<UserEntity> findByUserIdAndEmailIdIgnoreCase(Long userId, String emailId);

	void deleteByEmailIdIgnoreCase(String upperCase);

}
