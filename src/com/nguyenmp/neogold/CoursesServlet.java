package com.nguyenmp.neogold;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class CoursesServlet extends HttpServlet {
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
		// Pass the list of departments
		List<Department> departments = new ArrayList<Department>();
		
		ServletContext context = getServletContext();
		InputStream is = context.getResourceAsStream("/WEB-INF/departments");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		String line;
		while ((line = reader.readLine()) != null) {
			// Read department data
			String name = line;
			String id = reader.readLine();
			String alts = reader.readLine();
			reader.readLine(); // Consume the extra newline
			
			// Generate bean
			Department department = new Department();
			department.setName(name);
			department.setId(id);
			department.setAlternatives(alts);
			
			// Add bean to list
			departments.add(department);
		}
		
		request.setAttribute("departments", departments);
		
		List<Quarter> quarters = new ArrayList<Quarter>();
		for (int i = 2011; i < 2014; i++) {
			for (int j = 1; j < 5; j++) {
				Quarter quarter = new Quarter();
				quarter.setQuarter(j);
				quarter.setYear(i);
				quarters.add(quarter);
			}
		}
		
		Collections.sort(quarters);
		Collections.reverse(quarters);
		request.setAttribute("quarters", quarters);
		
		// Forward GET requests to the JSP file
		
		RequestDispatcher view = request.getRequestDispatcher("courses.jsp");
		view.forward(request, response);
	}
}
