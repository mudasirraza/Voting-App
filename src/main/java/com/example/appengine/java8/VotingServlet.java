package com.example.appengine.java8;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;

public class VotingServlet extends HttpServlet {
	
	private static final Logger LOGGER = Logger.getLogger(VotingServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String token = req.getParameter("token").trim();
		if(validateElection(token)) {
			Iterable<Entity> candidates = DbUtil.getAll("Candidate");	
			req.setAttribute("candidates", candidates);
			req.setAttribute("token", token);
			req.getRequestDispatcher("/jsps/voting.jsp").forward(req, res);
		} else {
			res.sendRedirect("/jsps/unauthorized.jsp");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String token = req.getParameter("token").trim();
		if(validateElection(token)) {
			String candidateId = req.getParameter("c_id");
			Entity vote = new Entity("Vote");
			vote.setProperty("candidateId", candidateId);						
			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			TransactionOptions options = TransactionOptions.Builder.withXG(true);
			Transaction txn = datastore.beginTransaction(options);
			try {
				datastore.put(txn, vote);
				datastore.delete(KeyFactory.createKey("Token", token));
				txn.commit();
			} finally {
				if (txn.isActive()) {
				    txn.rollback();
				    LOGGER.warning("Voting failed");
				}
			}
			
			res.sendRedirect("/jsps/success.jsp");
		} else {
			res.sendRedirect("/jsps/unauthorized.jsp");
		}
	}
	
	private Boolean validateElection(String token) {
		try {
			DbUtil.getByKey("Token", token);
		} catch (EntityNotFoundException e) {
			LOGGER.info("Voting error. Token is not valid");
			return false;
		}

		Entity election = DbUtil.getElection();
		Date startDate = (Date) election.getProperty("startDate");
		Date endDate = (Date) election.getProperty("endDate");
		
		Date now = new Date();
		if(startDate == null || endDate == null || now.before(startDate) || now.after(endDate)){
			LOGGER.info("Election is not active");
			return false;
		}

		return true;
	}
}
