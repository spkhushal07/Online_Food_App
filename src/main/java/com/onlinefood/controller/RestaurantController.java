package com.onlinefood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlinefood.Dto.RestaurantDto;
import com.onlinefood.Service.RestauratService;
import com.onlinefood.Service.UserService;
import com.onlinefood.entity.Restaurant;
import com.onlinefood.entity.User;

@RestController 
@RequestMapping("/api/restaurants")
public class RestaurantController {
	
	@Autowired
	private RestauratService restauratService;
	
	@Autowired
	private UserService userService;

	
	@GetMapping("/search")
	private ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization") String jwt,@RequestParam String keyword)throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		List<Restaurant> restaurant=restauratService.searchRestaurant(keyword);
			
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
		
	}
	

	@GetMapping
	private ResponseEntity<List<Restaurant>> getAllRestaurant(@RequestHeader("Authorization") String jwt)throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		List<Restaurant> restaurant=restauratService.getAllRestaurant();
			
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
		
	}
	@GetMapping("/{id}")
	private ResponseEntity<Restaurant> findRestaurantById(@RequestHeader("Authorization") String jwt, @PathVariable Long id)throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		Restaurant restaurant=restauratService.findRestaurantById(id);
		
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
		
	}
	
	@PutMapping("/{id}/add-favorite")
	private ResponseEntity<RestaurantDto> addToFovrites(@RequestHeader("Authorization") String jwt, @PathVariable Long id)throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		RestaurantDto restaurant=restauratService.addToFavorites(id,user);
		
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
		
	}

}
