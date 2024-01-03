package com.inn.cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class CafeMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeMgmtApplication.class, args);
		
		System.out.println("Application is Runnning......................................................");
	}

}
