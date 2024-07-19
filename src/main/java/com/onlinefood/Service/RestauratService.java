package com.onlinefood.Service;

import java.util.List;

import com.onlinefood.Dto.RestaurantDto;
import com.onlinefood.entity.Restaurant;
import com.onlinefood.entity.User;
import com.onlinefood.request.CreateRestaurantRequest;

public interface RestauratService {
	
	public Restaurant createRestaurant(CreateRestaurantRequest req,User user);
	
	public Restaurant updateRestaurat(Long restaurantId, CreateRestaurantRequest updatedRestaurat)throws Exception;
	
	public Restaurant deleteRestaurantId(Long restauratId) throws Exception;
	
	public List<Restaurant> getAllRestaurant();
	
	public List<Restaurant> searchRestaurant(String keyword);
	
	public Restaurant findRestaurantById(Long Id) throws Exception;
	
	public Restaurant getRestaurantByUserId(Long UserId) throws Exception;
	
	public RestaurantDto addToFavorites(Long restaurantId , User user) throws Exception;	
	
	public Restaurant updateRestaurantStatus(Long id)throws Exception;
	
	
	
}
