package com.example.appengine.java8;

import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

public class EmailUtil {
	
	private static final Logger LOGGER = Logger.getLogger(EmailUtil.class.getName());
	private static String fromEmail = "no-reply@voting-system-228917.appspotmail.com";
	private static String appName = "Voting Application";
	
	private String toEmail;
	private List<String> toEmails;
	private String subject;
	private String emailBody;
	private boolean bulk = false;
	
	EmailUtil(String sub, String body) {
		subject = sub;
		emailBody = body;
	}
	
	public void setToEmail(String to) {
		toEmail = to;
	}
	
	public void setToEmails(List<String> to) {
		toEmails = to;
	}

	public void sendEmail() {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
		  Message msg = new MimeMessage(session);
		  msg.setFrom(new InternetAddress(fromEmail, appName));
		  if(bulk == true) {
			  for(String e: toEmails) {
				  msg.addRecipient(Message.RecipientType.TO, new InternetAddress(e));
			  }
		  } else {
			  msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		  }
		  msg.setSubject(subject);
		  msg.setText(emailBody);
		  Transport.send(msg);
		} catch (AddressException e) {
			LOGGER.warning("Email sending error: Address is not valid");
		} catch (MessagingException e) {
			LOGGER.warning("Email sending errror: Email message is not valid");
		} catch (UnsupportedEncodingException e) {
			LOGGER.warning("Email sending error: Encoding is not valid");
		}
	}
	
	public void sendBulkEmails() {
		if(toEmails == null) {
			LOGGER.warning("Email sending error: sender list is not set");
		} else {
			bulk = true;
			sendEmail();
		}
	}
}
