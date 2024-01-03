package com.inn.cafe.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.pojo.Category;
import com.inn.cafe.rest.CategoryRest;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.utils.CafeUtils;
@RestController
public class CategoryRestImpl implements CategoryRest{
	
	@Autowired
	private CategoryService categoryService;

	@Override
	public ResponseEntity<String> addNewCategory(Map<String,String> requestMap) {
		try {
			System.out.println("Inside the add newcategory");
			return categoryService.addNewCategory(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.UNAUTHORIZED);
	}

	@Override
	public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
		try {
			System.out.println(filterValue);
			return categoryService.getAllCategory(filterValue);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
		
	}

	@Override
	public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
		try {
			return categoryService.updateCategory(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.UNAUTHORIZED);
	}

}
