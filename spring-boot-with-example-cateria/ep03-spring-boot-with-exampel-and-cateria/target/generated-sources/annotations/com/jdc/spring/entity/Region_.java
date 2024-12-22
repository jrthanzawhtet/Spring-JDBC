package com.jdc.spring.entity;

import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Region.class)
public abstract class Region_ {

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String STATES = "states";

	
	/**
	 * @see com.jdc.spring.entity.Region#name
	 **/
	public static volatile SingularAttribute<Region, String> name;
	
	/**
	 * @see com.jdc.spring.entity.Region#id
	 **/
	public static volatile SingularAttribute<Region, Integer> id;
	
	/**
	 * @see com.jdc.spring.entity.Region
	 **/
	public static volatile EntityType<Region> class_;
	
	/**
	 * @see com.jdc.spring.entity.Region#states
	 **/
	public static volatile ListAttribute<Region, State> states;

}

