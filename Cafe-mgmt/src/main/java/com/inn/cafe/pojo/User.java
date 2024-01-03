
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

//@NamedQuery(name="User.findByEmail" , query = "select user from User user where user.email=:email")  
@NamedQuery(name="User.findByEmailId" , query = "select u from User u where u.email=:email")  //Do not need to write this query
@NamedQuery (name="User.getAllUser" , query="select new com.inn.cafe.wrapper.UserWrapper(u.id,u.name,u.contactNumber,u.email,u.status) From User u where u.role='user'")
@NamedQuery (name="User.updateStatus" , query="update User u set u.status=:status where u.id=:id")
@NamedQuery (name="User.getAllAdmin" , query="select u.email From User u where u.role='admin'")
@Data
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name="user")
public class User implements Serializable {
	private static final long serialVersionUID=1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String contactNumber;
	private String email;
	private String password;
	private String status;
	private String role;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getStatus() {
		return status;
	}
	public String getRole() {
		return role;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}