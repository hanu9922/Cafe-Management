package com.inn.cafe.rest;


import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.cafe.wrapper.UserWrapper;

@RequestMapping("/user")
public interface UserRest {
	
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody(required=true)Map<String,String> requestMap);

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody(required=true)Map<String,String> requestMap);
	
	@GetMapping("/get")
	public ResponseEntity<List<UserWrapper>> getAll();
	
	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody(required=true)Map<String,String> requestMap);
	
	@GetMapping("/checkTocken")
	public ResponseEntity<String> checkTocken();
	
	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestMap);
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<String> forgetPassword(@RequestBody Map<String,String> requestMap);
	
}
