package com.onlinefood.request;

import java.util.List;


import com.onlinefood.entity.AddressEntity;
import com.onlinefood.entity.ContactInformation;

import lombok.Data;

@Data
public class CreateRestaurantRequest {
	
	
	private Long id;
	private String name;
	private String description;
	private String cuisineType;
	private AddressEntity address;
	private ContactInformation contactInformation;
	private String openingHours;
	private List<String> images;
	
}
