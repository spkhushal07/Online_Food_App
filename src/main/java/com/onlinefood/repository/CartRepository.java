package com.onlinefood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinefood.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
