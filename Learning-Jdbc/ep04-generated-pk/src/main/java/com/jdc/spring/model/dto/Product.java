package com.jdc.spring.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {
	
	private int id;
	private String name;
	private Category category;
	private int price;



}
