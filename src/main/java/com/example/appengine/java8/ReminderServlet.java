package com.example.appengine.java8;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

public class ReminderServlet extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(ReminderServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		if (req.getHeader("X-Appengine-Cron") != null && req.getHeader("X-Appengine-Cron").equals("true")) { 
			Entity election = DbUtil.getElection();
			Date startDate = (Date) election.getProperty("startDate");
			Date endDate = (Date) election.getProperty("endDate");
			if(startDate == null || endDate == null) {
				LOGGER.info("Reminder Email: Election is not active yet!");
			} else {
				Date now = new Date();
				double days =  (startDate.getTime() - now.getTime()) / (24.0 * 60 * 60 * 1000); 
				if( days <= 1 && days > 0) {
					String title = "Reminder! Only one day reminaing for university election!";
					String body = "This is a reminder email for university election. Please don't forget to vote tomorrow! Visit election portal to vote: " + AppInfo.appUrl + "/voting/cast";
					List<String> emails = new ArrayList<String>();
					Iterable<Entity> students = DbUtil.getAll("Student");
					for(Entity student : students) {
						emails.add((String) student.getProperty("email"));
					}
					EmailUtil e = new EmailUtil(title, body);
					e.setToEmails(emails);
					e.sendBulkEmails();
				}
			}
		}
		
		res.setStatus(HttpServletResponse.SC_OK);
	}
}
