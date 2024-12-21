package com.jdc.spring.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "region_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Region implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@OneToMany(mappedBy = "region",orphanRemoval = true, cascade = CascadeType.ALL)
	private List<State> states;
	
	
	@Override
	public String toString() {
		return name;
	}
	
	
	
	
	
	
}
