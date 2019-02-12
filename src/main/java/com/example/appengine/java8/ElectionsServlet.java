package com.example.appengine.java8;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

public class ElectionsServlet extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(ElectionsServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {	
		Entity election = DbUtil.getElection();
		req.setAttribute("election", election);
		
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = (Date) election.getProperty("startDate");
			Date endDate = (Date) election.getProperty("endDate");
			req.setAttribute("startDate", dateFormat.format(startDate));
			req.setAttribute("endDate", dateFormat.format(endDate));
		} catch (NullPointerException e) {
			LOGGER.info("Dates are not set yet");
		}
		
		req.getRequestDispatcher("/jsps/election.jsp").forward(req, res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {		
		Entity election = DbUtil.getElection();
		String startDate = req.getParameter("start_date");
		String startTime = req.getParameter("start_time");
		String endDate = req.getParameter("end_date");
		String endTime = req.getParameter("start_time");
		
		validateAndUpdate(startDate, startTime, endDate, endTime, election);
		res.sendRedirect("/admin/election");
	}
	
	private boolean validateAndUpdate(String startDate, String startTime, String endDate, String endTime, Entity election) {
		if(startDate == null || startTime == null || endDate == null || endTime == null) {
			LOGGER.info("Election update error: input is empty");
			return false;
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date start;
		Date end;
		try {
			start = dateFormat.parse(startDate + " " + startTime);
			end = dateFormat.parse(endDate + " " + endTime);
			if(start.after(end)) {
				LOGGER.warning("Start date is after end date");
				return false;
			}
		} catch (ParseException e) {
			LOGGER.warning("Election update error: start date or end date is invalid");
			return false;
		}
		
		election.setProperty("startDate", start);
		election.setProperty("endDate", end);
		DbUtil.put(election);
		
		return true;		
	}
}
