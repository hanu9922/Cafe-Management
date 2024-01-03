package com.inn.cafe.service;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.cafe.wrapper.UserWrapper;

public interface UserService {

	ResponseEntity<String> signUp(Map<String,String> requestMap);
	
	ResponseEntity<String> login(Map<String,String> requestMap);

	ResponseEntity<List<UserWrapper>> getAllUser();

	ResponseEntity<String> update(Map<String, String> requestMap);

	ResponseEntity<String> checkTocken();

	ResponseEntity<String> changePassword(Map<String, String> requestMap);

	ResponseEntity<String> forgetPassword(Map<String, String> requestMap);
}
