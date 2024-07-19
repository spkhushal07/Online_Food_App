package com.onlinefood.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
public class ContactInformation {
	
	private String email;
	
	private String mobile;
	private String twitter;
	private String instagram;

}
