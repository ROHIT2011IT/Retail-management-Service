package com.auction.retailService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.auction.retailService.entity.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	final String USER_BY_USERID_STOREID_ROLEID ="Select * from User where user_id = :userId and store_id = :storeId and role_id = :roleId";
	
	Optional<User> findByEmailId(String upperCase);

	List<User> findByFirstName(String name);

	void deleteByEmailId(String upperCase);


	Optional<User> findByUserIdAndStoreId(Long userId, Long storeId);

	Optional<User> findByUserIdAndEmailId(Long userId, String emailId);
	
	@Query (value = USER_BY_USERID_STOREID_ROLEID , nativeQuery=true)
	Optional<User> findByUserIdIdAndStoreIdAndRoleId(@Param ("userId") Long userId, @Param ("storeId") Long storeId, @Param ("roleId") int roleId);
	
}
