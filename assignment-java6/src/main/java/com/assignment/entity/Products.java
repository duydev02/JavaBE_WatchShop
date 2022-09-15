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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

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
	@Size(min = 3, max = 255)
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
	@Min(value = 0)
	@Max(value = 10000)
	private Integer quantity;
	
	@Column(name = "price")
	@Min(value = 0L)
	@Max(value = 1000000)
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
	@Size(min = 3, max = 255)
	private String slug;
	
	@Column(name = "isDeleted")
	private Boolean isDeleted;
}
