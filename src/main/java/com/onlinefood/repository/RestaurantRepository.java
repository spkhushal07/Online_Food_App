package com.onlinefood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlinefood.entity.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	
	Restaurant findByOwnerId(Long userId);
	
	@Query("select r from Restaurant r where lower(r.name) like '%' || lower(:query) || '%' or lower(r.cuisineType) like '%' || lower(:query) || '%'")
	List<Restaurant> findByQuery(@Param("query") String query);
	List<Restaurant> findByName(String keyword);

}
