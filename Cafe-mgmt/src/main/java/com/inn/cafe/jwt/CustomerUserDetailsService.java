package com.inn.cafe.jwt;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inn.cafe.dao.UserDao;


import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {
   
	@Autowired
	 UserDao userDao;
	
	private com.inn.cafe.pojo.User userDetail;
	
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//log.info("Inside the loadUserByUsername{}",username)
	userDetail=userDao.findByEmail(username);
	if(!Objects.isNull(userDetail)) {
	return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());
	} 
	else {
		throw new UsernameNotFoundException("user not found.");
	}
	}
	public com.inn.cafe.pojo.User getUserDetail(){
		
		return userDetail;
		
	}
	
	
}
