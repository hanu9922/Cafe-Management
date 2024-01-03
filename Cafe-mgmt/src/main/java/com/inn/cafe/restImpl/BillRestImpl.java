package com.inn.cafe.restImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.pojo.Bill;
import com.inn.cafe.rest.BillRest;
import com.inn.cafe.service.BillService;
import com.inn.cafe.utils.CafeUtils;
@RestController
public class BillRestImpl implements BillRest {

	@Autowired
	private BillService billService;
	
	@Override
	public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
		try {
			System.out.println("Inside the Rest IMPL"+((String)requestMap.get("uuid")));
			return billService.generateReport(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Bill>> getBills() {
		try {
			return billService.getBills();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity (new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR) ;
	}

	@Override
	public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
		try {
			System.out.println("check the file"+((String)requestMap.get("email")));
			return billService.getPdf( requestMap);	
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ResponseEntity<String> deleteBill(Integer id) {
		try {
			return billService. deleteBill(id);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
