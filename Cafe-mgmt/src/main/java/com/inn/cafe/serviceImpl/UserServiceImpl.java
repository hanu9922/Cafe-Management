package com.inn.cafe.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.dao.UserDao;
import com.inn.cafe.jwt.CustomerUserDetailsService;
import com.inn.cafe.jwt.JwtFilter;
import com.inn.cafe.jwt.JwtUtil;
import com.inn.cafe.pojo.User;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.utils.EmailUtils;
import com.inn.cafe.wrapper.UserWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	CustomerUserDetailsService customeUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private EmailUtils emailUtils;
	
	//SignUp

	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		  //log.info("Inside signUpMap{}",requestMap);
		
		   try {
		  if(validateSignUpMap( requestMap)) {
			User user=userDao.findByEmailId(requestMap.get("email")) ;
			//System.out.println(requestMap);
			if(Objects.isNull(user)) {
				userDao.save(getUserFromMap(requestMap));
				return CafeUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
			}
			else {
				return CafeUtils.getResponseEntity("Email aready exit",HttpStatus.BAD_REQUEST);
			}
		  }
		  else {
			return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
		  }}
		   catch(Exception ex) {
			   ex.printStackTrace();
		   }
		   return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	public boolean validateSignUpMap(Map<String, String> requestMap) {
		if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") &&requestMap.containsKey("password")) {
		return true;	
		}
		else {
			return false;	
		}}
		private User getUserFromMap(Map<String,String> requestMap) {
			User user=new User();
			System.out.println("My printed Email== "+requestMap.get("email"));
			user.setName(requestMap.get("name"));
			user.setContactNumber(requestMap.get("contactNumber"));
			user.setEmail(requestMap.get("email"));
			user.setPassword(requestMap.get("password"));
			user.setStatus("true");
			user.setRole("user");
			return user;
		}
		

		//login
		@Override
		public ResponseEntity<String> login(Map<String, String> requestMap) {
			
			
			try {
				
				Authentication auth=authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password")));
				if(auth.isAuthenticated()) {
					
					if(customeUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
						return new ResponseEntity<String>("{\"token\":\"" +jwtUtil.generateToken(customeUserDetailsService.getUserDetail().getEmail(),customeUserDetailsService.getUserDetail().getRole())+"\"}",HttpStatus.OK);
				}
					else {
						return new ResponseEntity<String>("{\"token\":\"" +"wait for admin approval."+"\"}",HttpStatus.BAD_REQUEST);
					}
					}
				}
				catch(Exception ex) {
				 ex.printStackTrace();
			}
			return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		@Override
		public ResponseEntity<List<UserWrapper>> getAllUser() {
			try {
				
				if(jwtFilter.isAdmin()) {
					return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);
				}
				else {
					
					return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
				}
				
			}
			catch(Exception ex) {
				
				ex.printStackTrace();
				
			}
			return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		@Override
		public ResponseEntity<String> update(Map<String, String> requestMap) {
			try {
				if(jwtFilter.isAdmin()) {
					Optional<User> optional=userDao.findById(Integer.parseInt(requestMap.get("id")));
					if(!optional.isEmpty()) {
						userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
						sendMailToAllAdmin(requestMap.get("status"),optional.get().getEmail(),userDao.getAllAdmin());
						return CafeUtils.getResponseEntity("User status updated successfully", HttpStatus.OK);
						
					}
					else {
						return CafeUtils.getResponseEntity("User id Doesn't exit", HttpStatus.OK);
					}
				}
				else {
				return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			 return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
			allAdmin.remove(jwtFilter.getCurrentUser());
			if(status!=null && status.equalsIgnoreCase("true")) {
				emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved","USER:-"+ user +"\n is approved by \n ADMIN:-"+jwtFilter.getCurrentUser(),allAdmin);
			}
			else {
				emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Disable","USER:-"+ user +"\n is disabled by \n ADMIN:-"+jwtFilter.getCurrentUser(),allAdmin);
			}
		}
		@Override
		public ResponseEntity<String> checkTocken() {
			
			return CafeUtils.getResponseEntity("true", HttpStatus.OK);
		}
		@Override
		public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
			try {
				
			  User userObj=userDao.findByEmail(jwtFilter.getCurrentUser());
			  if(!userObj.equals(null)) {
				if(userObj.getPassword().equals(requestMap.get("oldPassword"))) {
					userObj.setPassword(requestMap.get("newPassword"));
					userDao.save(userObj);
					return CafeUtils.getResponseEntity("Password change Successfully", HttpStatus.OK);
				}
				return CafeUtils.getResponseEntity("Incorrect Old PassWord", HttpStatus.BAD_REQUEST);
			  }
			  return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			 return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		@Override
		public ResponseEntity<String> forgetPassword(Map<String, String> requestMap) {
			try {
				User user =userDao.findByEmail(requestMap.get("email"));
				System.out.println(user.getEmail());
				if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) 
				emailUtils.forgetMail(user.getEmail(),"Credential by Cafe Management System", user.getPassword());
			 return CafeUtils.getResponseEntity("Check email for your credential", HttpStatus.OK);
			}catch(Exception ex) {
				ex.printStackTrace();	
			}
			 return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}