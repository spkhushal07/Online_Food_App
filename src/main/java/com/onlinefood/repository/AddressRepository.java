package com.onlinefood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinefood.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

}
