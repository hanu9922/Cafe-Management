package com.inn.cafe.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.cafe.dao.BillDao;
import com.inn.cafe.dao.CategoryDao;
import com.inn.cafe.dao.ProductDao;
import com.inn.cafe.dao.UserDao;
import com.inn.cafe.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {
	
	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private BillDao billDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public ResponseEntity<Map<String, Object>> getCount() {
		try {
			Map<String,Object> map=new HashMap<>();
			map.put("category", categoryDao.count());
			map.put("product", productDao.count());
			map.put("bill", billDao.count());
			//map.put("User", userDao.count());
			return new ResponseEntity<>(map,HttpStatus.OK);
			
		} catch(Exception ex) {
	    	 ex.printStackTrace();
	     }
		return null;
	}

}
