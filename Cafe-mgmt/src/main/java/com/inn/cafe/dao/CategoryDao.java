package com.inn.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.inn.cafe.pojo.Category;
import com.inn.cafe.pojo.User;

@Repository
public interface CategoryDao extends JpaRepository<Category,Integer>{

	List<Category> getAllCategory();

	//Category findByName(@Param("name")String name);
	
}
