package com.example.appengine.java8;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

public class NotifyStudentsServlet extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(NotifyStudentsServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		Entity election = DbUtil.getElection();
		Date startDate = (Date) election.getProperty("startDate");
		Date endDate = (Date) election.getProperty("endDate");
		
		if(validateSendingEmails(startDate, endDate)) {
			Iterable<Entity> students = DbUtil.getAll("Student");
			String title = "University Elections! from " + startDate + " to " + endDate;
			List<Entity> tokens = new ArrayList<Entity>();

			for(Entity student : students) {
				String token = UUID.randomUUID().toString();
				tokens.add(new Entity("Token", token));
				
				String body = "University Elections! Please visit our website: " + AppInfo.appUrl + " for election." +
						" To cast your vote, please visit " + AppInfo.appUrl + "/voting/cast with your token # " + token +
						" Or click on the following link to cast your vote once election is active: " + AppInfo.appUrl + "/voting?token=" + token;
				EmailUtil e = new EmailUtil(title, body);
				e.setToEmail((String) student.getProperty("email")); 
				e.sendEmail();
			}
			
			DbUtil.putBatch(tokens);
		}
		
		res.sendRedirect("/jsps/success.jsp");
	}
	
	private boolean validateSendingEmails(Date startDate, Date endDate) {
		if(startDate == null || endDate == null) {
			LOGGER.warning("Notificaiton error: Election is not active yet!");
			return false;
		}
		
		if(DbUtil.countEntities("Token") > 0) {
			LOGGER.warning("Notification error: Tokens are already generated");
			return false;
		}
		
		return true;
	}
}
