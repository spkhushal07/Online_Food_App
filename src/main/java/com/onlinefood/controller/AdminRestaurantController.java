package com.onlinefood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onlinefood.Service.RestauratService;
import com.onlinefood.Service.UserService;
import com.onlinefood.entity.Restaurant;
import com.onlinefood.entity.User;
import com.onlinefood.request.CreateRestaurantRequest;
import com.onlinefood.response.MessageResponse;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
	
	
	@Autowired
	private RestauratService restauratService;
	
	@Autowired
	private UserService userService;

	
	@PostMapping
	private ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req,@RequestHeader("Authorization") String jwt)throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		Restaurant restaurant=restauratService.createRestaurant(req, user);
			
		return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
		
	}
	

	@PutMapping("/{id}")
	private ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest req,@RequestHeader("Authorization") String jwt,@PathVariable Long id)throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		Restaurant restaurant=restauratService.updateRestaurat(id, req);
			
		return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
		
	}
	
	

	@DeleteMapping("/{id}")
	private ResponseEntity<MessageResponse> deleteRestaurant(@RequestHeader("Authorization") String jwt,@PathVariable Long id)throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		Restaurant restaurant=restauratService.deleteRestaurantId(id);
			MessageResponse messageResponse=new MessageResponse();
			
			messageResponse.setMessage("Restaurant deleted successfully");
		return new ResponseEntity<>(messageResponse,HttpStatus.OK);
		
	}
	
	
	@PutMapping("/{id}/status")
	private ResponseEntity<Restaurant> updateRestaurantStatus(@RequestHeader("Authorization") String jwt,@PathVariable Long id)throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		Restaurant restaurant=restauratService.updateRestaurantStatus(id);
		
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
		
	}
	
	
	
	@GetMapping("/user")
	private ResponseEntity<Restaurant> updateRestaurantStatus(@RequestHeader("Authorization") String jwt)throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		Restaurant restaurant=restauratService.getRestaurantByUserId(user.getId());
		
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
		
	}
	
	
		
	
}

