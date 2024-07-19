package com.onlinefood.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinefood.Dto.RestaurantDto;
import com.onlinefood.entity.AddressEntity;
import com.onlinefood.entity.Restaurant;
import com.onlinefood.entity.User;
import com.onlinefood.repository.AddressRepository;
import com.onlinefood.repository.RestaurantRepository;
import com.onlinefood.repository.UserRepository;
import com.onlinefood.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImpl implements RestauratService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

		AddressEntity address = addressRepository.save(req.getAddress());
		Restaurant restaurant = new Restaurant();
		restaurant.setAddress(address);
		restaurant.setContactInfo(req.getContactInformation());
		restaurant.setCuisineType(req.getCuisineType());
		restaurant.setDescription(req.getDescription());
		restaurant.setImages(req.getImages());
		restaurant.setName(req.getName());
		restaurant.setOpeningHours(req.getOpeningHours());
		restaurant.setRegistrationDate(LocalDateTime.now());
		restaurant.setOwner(user);

		return restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurat(Long restaurantId, CreateRestaurantRequest updatedRestaurat) throws Exception {
		Restaurant restaurant = findRestaurantById(restaurantId);

		if (restaurant.getCuisineType() != null) {
			restaurant.setCuisineType(updatedRestaurat.getCuisineType());
		}
		if (restaurant.getDescription() != null) {
			restaurant.setDescription(updatedRestaurat.getDescription());
		}

		if (restaurant.getName() != null) {
			restaurant.setName(updatedRestaurat.getName());

		}
		if (restaurant.getContactInfo() != null) {
			restaurant.setContactInfo(updatedRestaurat.getContactInformation());

		}

		if (restaurant.getOpeningHours() != null) {
			restaurant.setOpeningHours(updatedRestaurat.getOpeningHours());
		}
		return restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant deleteRestaurantId(Long restauratId) throws Exception {
		Restaurant restaurant = findRestaurantById(restauratId);
		restaurantRepository.delete(restaurant);
		return restaurant;
	}

	@Override
	public List<Restaurant> getAllRestaurant() {

		return restaurantRepository.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurant(String keyword) {

		return restaurantRepository.findByName(keyword);
	}

	@Override
	public Restaurant findRestaurantById(Long Id) throws Exception {
		Optional<Restaurant> opt = restaurantRepository.findById(Id);

		if (opt.isEmpty()) {
			throw new Exception("Restaurant not found with id " + Id);
		}
		return opt.get();
	}

	@Override
	public Restaurant getRestaurantByUserId(Long UserId) throws Exception {
		Restaurant restaurant=restaurantRepository.findByOwnerId(UserId);
		if(restaurant==null) {
			throw new Exception("restaurant not found with owner id "+UserId);
		}
		return restaurant;
	}

	@Override
	public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
		
		Restaurant restaurant=findRestaurantById(restaurantId);
		RestaurantDto dto=new RestaurantDto();
		dto.setDescription(restaurant.getDescription());
		dto.setId(restaurantId);
		dto.setImages(restaurant.getImages());
		dto.setTitle(restaurant.getName());
		
		
		if(user.getFavorites().contains(dto))
		{
			user.getFavorites().remove(dto);
		}
		else
		{
			user.getFavorites().add(dto);
		}
		
		userRepository.save(user);
		
		
		
		return dto;
	}

	@Override
	public Restaurant updateRestaurantStatus(Long id) throws Exception {
		Restaurant restaurant=findRestaurantById(id);
		restaurant.setOpen(!restaurant.isOpen());
		return restaurantRepository.save(restaurant);
	}

}
