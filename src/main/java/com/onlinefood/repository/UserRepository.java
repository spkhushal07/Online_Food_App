package com.onlinefood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinefood.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

	public User findByEmail(String username);
}
