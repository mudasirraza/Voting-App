package com.example.appengine.java8;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

public class CandidatesServlet extends HttpServlet {
	
	private static final Logger LOGGER = Logger.getLogger(DbUtil.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		Iterable<Entity> candidates = DbUtil.getAll("Candidate");
		req.setAttribute("candidates", candidates);
		req.getRequestDispatcher("/jsps/candidates.jsp").forward(req, res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String faculty = req.getParameter("faculty");
		
		if(validateInputs(firstname, lastname, faculty)) {
			Entity candidate = new Entity("Candidate");
			candidate.setProperty("firstname", firstname);
			candidate.setProperty("lastname", lastname);
			candidate.setProperty("faculty", faculty);
			
			DbUtil.put(candidate);
		}
		res.sendRedirect("/admin/candidates");
	}
	
	private boolean validateInputs(String firstname, String lastname, String faculty) {
		if(firstname == null || lastname == null || faculty == null || firstname.length() == 0 || lastname.length() == 0 || faculty.length() == 0) {
			LOGGER.warning("Candidate error. Some inputs are empty");
			return false;
		}
		
		Entity election = DbUtil.getElection();
		Date startDate = (Date) election.getProperty("startDate");
		Date endDate = (Date) election.getProperty("endDate");
		
		Date now = new Date();
		if(startDate != null && endDate != null && now.after(startDate)){
			LOGGER.info("Add candidate error. Election has already started!");
			return false;
		}

		return true;
	}

}
