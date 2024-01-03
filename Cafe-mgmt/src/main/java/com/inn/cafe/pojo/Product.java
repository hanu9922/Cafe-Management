package com.inn.cafe.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NamedQuery(name="Product.getAllProduct" ,query="Select new com.inn.cafe.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name)from Product p")
@NamedQuery(name="Product.updateStatus",query="Update Product p set p.status=:status where p.id=:id")
//@NamedQuery(name="product.getCategoryById" ,query="Select new com.inn.cafe.wrapper.ProductWrapper(p.category.id,p.category.name)from Product p where p.category.id=:id and p.status='true'")
//@NamedQuery(name="product.getProductById" ,query="Select new com.inn.cafe.wrapper.ProductWrapper(p.id,p.name,p.description,p.price)from Product p where p.id=:id")
@DynamicUpdate                             //variable copy from  ProductWrapper and Value get from product
@DynamicInsert
@Entity
@Table (name="product")
public class Product implements Serializable {
	public static final Long serialVersionUID=1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_fk"  , nullable=false)
	private Category category;
	
	private String description;
	
	private int price;
	
	private String status;

	public Product() {
		
	}


	public Product(int id, String name, Category category, String description, int price, String status) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	
	
}
