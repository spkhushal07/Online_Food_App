package com.onlinefood.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.onlinefood.Dto.RestaurantDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String fullName;
	
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	
	
	private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;
	
	@JsonIgnore
	
	//ORDERENTITY AND USERENTITY USER HAS ONE TO MANY RELATION WITH ORDER
	@OneToMany(cascade = CascadeType.ALL,mappedBy="customer")
	private List<OrderEntity> order= new ArrayList<>();
	
	@ElementCollection
	
	private List<RestaurantDto> favorites= new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	private List<AddressEntity> addresses=new ArrayList<>();

}
