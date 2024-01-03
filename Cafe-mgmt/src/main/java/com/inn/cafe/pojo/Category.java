package com.inn.cafe.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@NamedQuery(name="Category.findByName" , query = "select c from Category c where c.name=:name")
@NamedQuery(name="Category.getAllCategory" , query="Select c from Category c where c.id in (select p.category from Product p where p.status='true') ")
@Data
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Entity
@Table (name="category")
public class Category implements Serializable {
	private static final long serialVersionUID=1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	
	
}
