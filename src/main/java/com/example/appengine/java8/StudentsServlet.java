package com.example.appengine.java8;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

public class StudentsServlet extends HttpServlet {
	
	private static final Logger LOGGER = Logger.getLogger(StudentsServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		Iterable<Entity> students = DbUtil.getAll("Student");
		req.setAttribute("students", students);
		req.getRequestDispatcher("/jsps/students.jsp").forward(req, res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String email = req.getParameter("email");
		
		if(validateInputs(email)) {
			Entity student = new Entity("Student");
			student.setProperty("email", email);
			
			DbUtil.put(student);
		}
		res.sendRedirect("/admin/students");
	}
	
	private boolean validateInputs(String email) {
		if(email == null) {
			LOGGER.warning("Student error. email is empty");
			return false;
		}
		
		try {
	      InternetAddress emailAddr = new InternetAddress(email);
	      emailAddr.validate();
	    } catch (AddressException ex) {
	    	LOGGER.warning("Student error. email is invalid");
			return false;
	    }
		
		return true;
	}
}
