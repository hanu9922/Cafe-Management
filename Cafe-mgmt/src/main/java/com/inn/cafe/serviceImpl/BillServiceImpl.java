package com.inn.cafe.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.dao.BillDao;
import com.inn.cafe.jwt.JwtFilter;
import com.inn.cafe.pojo.Bill;
import com.inn.cafe.rest.BillRest;
import com.inn.cafe.service.BillService;
import com.inn.cafe.utils.CafeUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class BillServiceImpl implements BillService{
     
	@Autowired
	private BillDao billDao;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	
	@Override
	public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
		try {
			String fileName;
			if(validateRequestMap(requestMap)) {
				if(requestMap.containsKey("isGenerate")&& !(Boolean)requestMap.get("isGenerate")) {
					fileName=(String)requestMap.get("uuid");
				}else {
					fileName=CafeUtils.getUUID();
					requestMap.put("uuid", fileName);
					insertBill(requestMap);
				}
				String data= "Name: "+requestMap.get("name")+"\n"+"Email: "+requestMap.get("email")+"\n"
			+"ContactNumber: "+requestMap.get("contactNumber")+"\n"+"PaymentMethod: "+requestMap.get("paymentMethod");
				Document document=new Document();
				PdfWriter.getInstance(document, new FileOutputStream(CafeConstant.STORED_LOCATION+"\\"+fileName+".pdf"));
				document.open();
				setRectangleInPdf(document);
				
				Paragraph chunk=new Paragraph("Cafe Management System",getFont("Header"));
				  chunk.setAlignment(Element.ALIGN_CENTER);
				  document.add(chunk);
				  Paragraph paragraph=new Paragraph(data+"\n \n",getFont("Data"));
				  document.add(paragraph);
				  PdfPTable table=new PdfPTable(5);
				  table.setWidthPercentage(100);
				  addTableHeader(table);
				  
				  JSONArray jsonArray=CafeUtils.getJsonArrayFromString((String) requestMap.get("productDetails"));
				  for(int i=0;i<jsonArray.length();i++) {
					  addRows(table,CafeUtils.getMapFromJson(jsonArray.getString(i)));
				  }
			          document.add(table);	
			          Paragraph footer=new Paragraph("Total : "+requestMap.get("totalAmount")+"\n"
			        		  +"Thank you for visiting.Please visit again !",getFont("Data"));
			          document.add(footer);
			          document.close();
			          return new ResponseEntity<>("{\"uuid\":\""+fileName+"\"}",HttpStatus.OK);
				 
				  
			}
			return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}



	
	private boolean validateRequestMap(Map<String, Object> requestMap) {
		return requestMap.containsKey("name") && requestMap.containsKey("email")
				&& requestMap.containsKey("contactNumber") && requestMap.containsKey("productDetails");
				
	}
	
	private void insertBill(Map<String, Object> requestMap) {
		try {
			System.out.println("inside insert bill");
		Bill bill=new Bill();
		bill.setUuid((String)requestMap.get("uuid"));
		bill.setName((String) requestMap.get("name"));
		bill.setEmail((String) requestMap.get("email"));
		bill.setContactNumber((String) requestMap.get("contactNumber"));
		bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
		bill.setTotal(Integer.parseInt((String)requestMap.get("totalAmount")));
		bill.setProductDetails((String) requestMap.get("productDetails"));
		bill.setCreatedBy(jwtFilter.getCurrentUser());
		System.out.println(bill.getEmail());
		billDao.save(bill);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	private void setRectangleInPdf(Document document) throws DocumentException{
		System.out.println("inside rectangle ");
		Rectangle rect=new Rectangle(577,825,18,15);
        rect.enableBorderSide(1);	
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(3);
        document.add(rect);
	}
	
	private Font getFont(String type) {
		System.out.println("inside getFont");
		switch(type) {
		case "Header":
			Font headerFont=FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
			headerFont.setStyle(Font.BOLD);
			return headerFont;
		case "Data" :
			Font dataFont=FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
			dataFont.setStyle(Font.BOLD);
			return dataFont;
	  default:
		  return new Font();
		}
		
	}
	

	

	private void addTableHeader(PdfPTable table) {
	     // log.info("Inside the addTableHeader");
		Stream.of("Name","Category","Quantity","Price","Sub Total")
		       .forEach(columnTitle->{
		    	   PdfPCell header=new PdfPCell();
		    	   header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		    	   header.setBorderWidth(2);
		    	   header.setPhrase(new Phrase(columnTitle));
		    	   header.setBackgroundColor(BaseColor.ORANGE);
		    	   header.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	   header.setVerticalAlignment(Element.ALIGN_CENTER);
		    	   table.addCell(header);
		       });
	}
	private void addRows(PdfPTable table, Map<String, Object> data) {
		
		table.addCell((String) data.get("name"));
		table.addCell((String) data.get("category"));
		table.addCell((String) data.get("quantity"));
		table.addCell(Double.toString((Double) data.get("price")));
		table.addCell(Double.toString((Double) data.get("total")));
		//table.addCell((String) data.get("amount"));
		
	}




	@Override
	public ResponseEntity<List<Bill>> getBills() {
		List<Bill> list=new ArrayList<>();
		try {
			if(jwtFilter.isAdmin()) {
				System.out.println("getBill");
				list= billDao.getAllBills();
			}else {
			     list= billDao.getBillByUserName(jwtFilter.getCurrentUser());
			}
			return new ResponseEntity(list,HttpStatus.OK);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity (new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR) ;
	}




	@Override
	public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
		try {
		byte[] byteArray=new byte[0];
		if(!requestMap.containsKey("uuid") && validateRequestMap(requestMap)){
		return new ResponseEntity<>(byteArray,HttpStatus.BAD_REQUEST);	
		}
		
		String filePath=CafeConstant.STORED_LOCATION+"\\" +(String)requestMap.get("uuid")+".pdf" ;
		if(CafeUtils.isFileExit(filePath)) {
			
			byteArray=getByteArray(filePath);
		return new ResponseEntity<>(byteArray,HttpStatus.OK);
	}
	else {
		requestMap.put("isGenerate", false);
		generateReport(requestMap);
		byteArray=getByteArray(filePath);
		return new ResponseEntity<>(byteArray,HttpStatus.OK);
	}}catch(Exception ex) {
		ex.printStackTrace();
	}
		return null;
	}




	private byte[] getByteArray(String filePath) throws Exception {
		File initialFile=new File(filePath);
		InputStream targetStream=new FileInputStream(initialFile);
		byte [] byteArray=IOUtils.toByteArray(targetStream);
		targetStream.close();
		return byteArray;
	}
	
	@Override
	public ResponseEntity<String> deleteBill(Integer id) {
		try {
			if(jwtFilter.isAdmin()) {
				java.util.Optional<Bill> bill=billDao.findById(id);
				if(!bill.isEmpty()) {
				billDao.deleteById(id);
				return CafeUtils.getResponseEntity(" Bill successfully deleted", HttpStatus.OK);
				}else {
					return CafeUtils.getResponseEntity(" Bill does'nt exit", HttpStatus.OK);	
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.OK);
	}




}
