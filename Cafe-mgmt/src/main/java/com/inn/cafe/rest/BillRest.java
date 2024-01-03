package com.inn.cafe.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.cafe.pojo.Bill;

@RequestMapping("/bill")
public interface BillRest {
       
	@PostMapping("/generateReport")
	ResponseEntity<String> generateReport(@RequestBody (required=true) Map<String,Object> requestMap);
	
	@GetMapping("/getBills")
	ResponseEntity<List<Bill>> getBills();
	
	@GetMapping("/getPdf")
	ResponseEntity<byte[]> getPdf(@RequestBody (required=true)Map<String,Object> requestMap);
	
	@DeleteMapping("/delete/{id}")
     ResponseEntity<String> deleteBill(@PathVariable("id")Integer id);
	
	
}
