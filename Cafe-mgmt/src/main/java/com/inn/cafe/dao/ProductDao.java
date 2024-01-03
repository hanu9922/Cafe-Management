package com.inn.cafe.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.inn.cafe.pojo.Product;
import com.inn.cafe.wrapper.ProductWrapper;

@Repository
public interface ProductDao extends JpaRepository<Product,Integer> {

	List<ProductWrapper> getAllProduct();
	
	@Transactional
    @Modifying
	Integer updateStatus(@Param("status") String status,@Param("id") Integer id);
	
	   @Query("Select new com.inn.cafe.wrapper.ProductWrapper(p.id,p.name)from Product p where p.category.id=:id and p.status='true'")
       List<ProductWrapper> getCategoryById(@Param("id")  Integer id);
     
       @Query("Select new com.inn.cafe.wrapper.ProductWrapper(p.id,p.name,p.description,p.price)from Product p where p.id=:id")
	   ProductWrapper getProductById(@Param("id") Integer id);
       

	


	

}
