package com.nguyenmp.neogold;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
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
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("User");
		query.addProjection(new PropertyProjection("username", String.class));
		List<Entity> resultSet = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
		request.setAttribute("users", resultSet);
		RequestDispatcher view = request.getRequestDispatcher("login.jsp");
		view.forward(request, response);
	}
}
