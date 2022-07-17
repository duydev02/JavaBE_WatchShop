package com.assignment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Products implements Serializable {

	private static final long serialVersionUID = -5156432008326665781L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "typeId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private ProductTypes productType;
	
	@ManyToOne
	@JoinColumn(name = "brandId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private BrandTypes brandType;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "price")
	private Double price;
	
	@ManyToOne
	@JoinColumn(name = "unitId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private UnitTypes unitType;
	
	@Column(name = "imgUrl")
	private String imgUrl;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "slug")
	private String slug;
	
	@Column(name = "isDeleted")
	private Boolean isDeleted;
}
