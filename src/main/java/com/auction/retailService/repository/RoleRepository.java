package com.auction.retailService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auction.retailService.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{

}
