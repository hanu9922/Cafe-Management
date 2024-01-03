package com.inn.cafe.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.dao.CategoryDao;
import com.inn.cafe.jwt.JwtFilter;
import com.inn.cafe.pojo.Category;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.utils.CafeUtils;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private JwtFilter jwtFilter;

	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				
				if(validateCategoryMap(requestMap,false)){
					//java.util.Optional<Category> optional=categoryDao.findById(Integer.parseInt(requestMap.get("id")));
					categoryDao.save(getCategoryFromMap( requestMap,false));
					return CafeUtils.getResponseEntity("Category added sucessfully",HttpStatus.OK);
				}
			}
			else {
				
				return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.UNAUTHORIZED);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.UNAUTHORIZED);
	}

	private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
		if(requestMap.containsKey("name")) {
			if(requestMap.containsKey("id") && validateId) {
				return true;
			}else if(!validateId) {
				return true;
			}
		}
		return false;
	}
	
	    private Category getCategoryFromMap(Map<String, String> requestMap,boolean isAdd){
	    	Category category=new Category();
	    	if(isAdd) {
	        category.setId(Integer.parseInt(requestMap.get("id")));
	    	}
	    	category.setName(requestMap.get("name"));
			return category;
		
	}

		@Override
		public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
			try {
				if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
					System.out.println("Inside if Block");
					return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(),HttpStatus.OK);
					
				}
				System.out.println("Outside if Block");
				return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
			
		}

		@Override
		public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
			try {
				if(jwtFilter.isAdmin()) {
					if(validateCategoryMap(requestMap,true)) {
						java.util.Optional<Category> optional=categoryDao.findById(Integer.parseInt(requestMap.get("id")));
						if(!optional.isEmpty()) {
							categoryDao.save(getCategoryFromMap( requestMap,true));
							return CafeUtils.getResponseEntity("Category Updated Sucessfully",HttpStatus.OK);
						}else {
							return CafeUtils.getResponseEntity("Category id does not exit",HttpStatus.OK);
						}
					}
					return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA,HttpStatus.BAD_REQUEST);
				}else {
					return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.UNAUTHORIZED);
				}
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.UNAUTHORIZED);
		}

}
