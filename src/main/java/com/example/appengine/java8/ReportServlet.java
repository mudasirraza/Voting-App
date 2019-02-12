package com.example.appengine.java8;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class ReportServlet extends HttpServlet {
	
	private static final Logger LOGGER = Logger.getLogger(VotingServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		if(validateElection()) {
			int totalCastedVotes = DbUtil.countEntities("Vote");
			int totalVotes = DbUtil.countEntities("Student");
			double percent = (totalCastedVotes / totalVotes) * 100;
			
			Map<Entity, Integer> candidatesVotesHash = new HashMap<Entity, Integer>();
					
			
			Iterable<Entity> candidates = DbUtil.getAll("Candidate");
			for(Entity c: candidates) {
				int totalCandidateVotes = DbUtil.countEqualEntities("Vote", "candidateId", String.valueOf(c.getKey().getId()));
				candidatesVotesHash.put(c, totalCandidateVotes);			
			}
			
			req.setAttribute("totalCastedVotes", totalCastedVotes);
			req.setAttribute("totalVotes", totalVotes);
			req.setAttribute("percent", percent);
			req.setAttribute("candidatesVotesHash", candidatesVotesHash);
			req.getRequestDispatcher("/jsps/report.jsp").forward(req, res);
		} else {
			res.sendRedirect("/jsps/unauthorized.jsp");
		}
		
		
	}
	
	private Boolean validateElection() {
		Entity election = DbUtil.getElection();
		Date startDate = (Date) election.getProperty("startDate");
		Date endDate = (Date) election.getProperty("endDate");
		
		Date now = new Date();
		if(startDate == null || endDate == null || now.before(endDate)){
			LOGGER.info("Election is not finished yet");
			return false;
		}

		return true;
	}
}
