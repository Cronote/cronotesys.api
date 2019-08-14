package com.cronotesys.APIrest;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
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
			Email email = new SimpleEmail();
			email.setHostName(sHostName);
			email.setSmtpPort(iSmtpPort);
			email.setAuthenticator(new DefaultAuthenticator(sUser, sPass));
			email.setSSLOnConnect(true);
			email.setFrom(sUser,"Cronote");
			email.setSubject(emailVO.getSubject());
			String message = "";
			if(emailVO.getMessage().contains("team")) {
				email.setMsg(new MessageUtil().generateMessage("team"));
			}else {
				
			}
			email.setMsg(emailVO.getMessage());
			if(emailVO.getReceiver().length == 0) {
				System.out.println("Erro, não há destinatario.");
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
	
	public void sendEmail(Email email) {
			try {
				email.send();
			} catch (EmailException e) {
				e.printStackTrace();
			}
	}
}
