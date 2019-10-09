package com.cronotesys.APIrest;

import java.util.concurrent.CompletableFuture;

import javax.validation.constraints.Email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.cronoteSys.model.vo.EmailVO;

public class EmailUtil {
	private final String sUser = "cronotesys@gmail.com";
	private final String sPass = "qfkmpdycmduuzfmr";
	private final int iSmtpPort = 465;
	private final String sHostName = "smtp.googlemail.com";
	public boolean genericEmail(EmailVO emailVO) {
		try {
			SimpleEmail email = new SimpleEmail();
			email.setHostName(sHostName);
			email.setSmtpPort(iSmtpPort);
			email.setAuthenticator(new DefaultAuthenticator(sUser, sPass));
			email.setSSLOnConnect(true);
			email.setFrom(sUser,"Cronote");
			email.setSubject(emailVO.getSubject());
			email.setMsg(emailVO.getMessage());		
			if(emailVO.getReceiver().length == 0) {
				return false;
			}else {
				String[] receiver = emailVO.getReceiver();
				for (int i = 0; i < receiver.length; i++) {
					email.addTo(receiver[i]);
				}
			}
			sendEmail(email);
			return true;
		} catch (EmailException e) {
			e.getMessage();
		}
		return false;		
	}
	
	public void sendEmail(SimpleEmail email) {
		CompletableFuture.runAsync(new Runnable() {
		    @Override
		    public void run() {
				try {
					email.send();
				} catch (EmailException e) {
					e.printStackTrace();
				}
		    }
		});
	}
}
