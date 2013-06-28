package com.nguyenmp.neogold;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class CourseServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Entity entity = new Entity("User");
		entity.setProperty("username", username);
		entity.setProperty("password", password);
		entity.setProperty("created", System.currentTimeMillis());
		datastoreService.put(entity);
		
		response.sendRedirect("login");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Forward GET requests to the JSP file
		RequestDispatcher view = request.getRequestDispatcher("course.jsp");
		view.forward(request, response);
	}
}
