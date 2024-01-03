package com.inn.cafe.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.dao.ProductDao;
import com.inn.cafe.jwt.JwtFilter;
import com.inn.cafe.pojo.Category;
import com.inn.cafe.pojo.Product;
import com.inn.cafe.service.ProductService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.ProductWrapper;
@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				if(validateProduct(requestMap,false)) {
					System.out.println("Validate product");
					 productDao.save(getProductFromMap(requestMap,false));
					 return CafeUtils.getResponseEntity("Product successfully Saved",HttpStatus.OK);
				}else
				return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA,HttpStatus.BAD_REQUEST);
			}
			else {
				return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	

	private boolean validateProduct(Map<String, String> requestMap, boolean validateId) {
		if(requestMap.containsKey("name")) {
			if(requestMap.containsKey("id") && validateId) {
				return true;
			}else if(!validateId) {
				return true;
			}
		}
		return false;
	}
	
	private Product getProductFromMap(Map<String,String> requestMap,boolean isAdd) {
		Category category=new Category();
		category.setId(Integer.parseInt(requestMap.get("categoryId")));
		Product product=new Product();
		if(isAdd) {
			product.setId(Integer.parseInt(requestMap.get("id")));
		}
			else {
				product.setStatus("true");
			}
		product.setCategory(category);
		product.setName(requestMap.get("name"));
		product.setDescription(requestMap.get("description"));
		product.setPrice(Integer.parseInt(requestMap.get("price")));
		
		return product;
	}



	@Override
	public ResponseEntity<List<ProductWrapper>> getAllProduct() {
		try {
			return new ResponseEntity<>(productDao.getAllProduct(),HttpStatus.OK);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}



	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
		try {
			if(validateProduct(requestMap,true)) {
				java.util.Optional<Product> optional=productDao.findById(Integer.parseInt(requestMap.get("id")));
				if(!optional.isEmpty()) {
					System.out.println("isEmty");
					     Product product =getProductFromMap(requestMap,true);
					     product.setStatus(optional.get().getStatus());
					     productDao.save(product);
					     return CafeUtils.getResponseEntity("Product updated Sucessfully", HttpStatus.OK);
				}else {
					CafeUtils.getResponseEntity("Product id does'nt exit", HttpStatus.BAD_REQUEST);
				}
			}else {
				CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}



	@Override
	public ResponseEntity<String> deleteProduct(Integer id) {
		try {
			if(jwtFilter.isAdmin()) {
		java.util.Optional<Product> optional=productDao.findById(id);
		if(!optional.isEmpty()) {
			productDao.deleteById(id);
			return CafeUtils.getResponseEntity("Product successfully deleted", HttpStatus.OK);
		}else {
			return CafeUtils.getResponseEntity("Product id does'nt exit", HttpStatus.OK);
		}}else {
			CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
		}
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}



	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
			java.util.Optional<Product> optional=productDao.findById(Integer.parseInt(requestMap.get("id")));
			if(!optional.isEmpty()) {
			productDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
			return CafeUtils.getResponseEntity("Product sucessfully updated", HttpStatus.OK);
			}else {
				return CafeUtils.getResponseEntity("Product id doesn'nt exit", HttpStatus.OK);
			}}
		else {
			return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
		}}
			catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}



	@Override
	public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
		try {
			
			return new ResponseEntity<>(productDao.getCategoryById(id),HttpStatus.OK);
		}catch(Exception ex) {
			ex.printStackTrace();
		} 
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}



	@Override
	public ResponseEntity<ProductWrapper> getProductById(Integer id) {
		  try {
			 ProductWrapper product =productDao.getProductById(id);
			  System.out.println(product.getId()+" "+product.getName()+" "+product.getDescription()+" "+product.getPrice());
			  return new ResponseEntity<>(productDao.getProductById(id),HttpStatus.OK);
		  }catch(Exception ex) {
			  ex.printStackTrace();
		  }
		return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
         
}
