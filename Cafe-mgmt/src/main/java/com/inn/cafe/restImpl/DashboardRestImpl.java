package com.inn.cafe.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.rest.DashboardRest;
import com.inn.cafe.service.DashboardService;

@RestController
public class DashboardRestImpl implements DashboardRest {
	
	
	@Autowired
	private DashboardService dashboardService;

	@Override
	public ResponseEntity<Map<String, Object>> getCount() {
		try {
			return dashboardService.getCount();
		} catch(Exception ex) {
	    	 ex.printStackTrace();
	     }
		return null;
	}
	
	

}
