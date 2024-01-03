package com.inn.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.inn.cafe.pojo.Bill;
@Repository
public interface BillDao extends JpaRepository<Bill,Integer> {
	
    @Query("Select b from Bill b order by b.id desc")
	List<Bill> getAllBills();
    
      @Query("Select b from Bill b where b.createdBy=:username order by b.id desc")
     List<Bill> getBillByUserName(@Param("username")String username);

}
