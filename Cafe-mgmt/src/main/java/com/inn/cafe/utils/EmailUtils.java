package com.inn.cafe.utils;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.inn.cafe.wrapper.UserWrapper;

@Service
public class EmailUtils {

	
	@Autowired
	private JavaMailSender emailSender;
	
	public void sendSimpleMessage(String to,String Subject,String text,List<String> list) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("hdhane71@gmail.com");
		message.setTo(to);
		message.setSubject(Subject);
		message.setText(text);
		if(list!=null&&list.size()>0) 
		message.setCc(getCcArray(list));
		emailSender.send(message);
		
	}
	
	private String[] getCcArray(List<String> ccList) {
	    return ccList.toArray(new String[0]);
	}

//	private String[] getCcArray(List<String> ccList) {
//		String[] cc=new String[ccList.size()];
//		for(int i=0;i<ccList.size();i++) {
//			cc[i]=ccList.get(i);
//		}
//		return cc;
//	}
	
	public void forgetMail(String to,String subject,String password)throws MessagingException{
		MimeMessage message=emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true);
		helper.setFrom("hdhane71@gmail.com");
		helper.setTo(to);
		helper.setSubject(subject);
		String htmlMsg="<p><b> Your Login details for cafe Management System</b><br>Email:</b>" +to + "<br><b>Password: </b> " +password + "<br><a href=\"http://localhost:4200/\"> Click here to login</a></p>";
		message.setContent(htmlMsg,"text/html");
		emailSender.send(message);
	}
	
}
