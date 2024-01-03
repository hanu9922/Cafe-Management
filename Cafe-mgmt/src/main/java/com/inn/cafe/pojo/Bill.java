package com.inn.cafe.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
@Data
@DynamicUpdate
@DynamicInsert
@Entity
@Table
public class Bill implements Serializable {
    public static final Long serialVersionUID=1L;
    
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String uuid;
	private String name;
	private String email;
	@Column (name="contactnumber")
	private String contactNumber;
	@Column (name="paymentmethod")
	private String paymentMethod;
	private int total;
	@Column (name="productdetails", columnDefinition="json")
	private String productDetails;
	@Column (name="createdby")
	private String createdBy;
	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Bill(int id, String uuid, String name, String email, String contactNumber, String paymentMethod, int total,
			String productDetails, String createdBy) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.email = email;
		this.contactNumber = contactNumber;
		this.paymentMethod = paymentMethod;
		this.total = total;
		this.productDetails = productDetails;
		this.createdBy = createdBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public static Long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
