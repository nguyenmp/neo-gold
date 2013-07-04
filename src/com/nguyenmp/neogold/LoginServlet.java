package com.nguyenmp.neogold;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.nguyenmp.grawler.GoldSession;
import com.nguyenmp.grawler.Grawler;
import com.nguyenmp.grawler.HttpClientFactory.SSHException;
import com.nguyenmp.neogold.beans.UserKey;
import com.nguyenmp.neogold.dao.UserDAO;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String ERROR_MESSAGE_SSL_EXCEPTION = "We think someone's pretending to be GOLD right now! Sorry.  Try again later?",
			ERROR_MESSAGE_AUTHENTICATION = "Wrong username/password! :(";
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			GoldSession session = Grawler.login(username, password);
			
			if (session == null) {
				request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_AUTHENTICATION);
				RequestDispatcher view = request.getRequestDispatcher("login.jsp");
				view.forward(request, response);
				return;
			}
			
		} catch (SSHException e) {
			e.printStackTrace();
			request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_SSL_EXCEPTION);
			RequestDispatcher view = request.getRequestDispatcher("login.jsp");
			view.forward(request, response);
			return;
		}
		
		UserKey userKey = UserDAO.put(username, password);
		Cookie cookie = new Cookie(LoginFilter.COOKIE_NAME, userKey.toString());
		response.addCookie(cookie);
		
		response.sendRedirect("courses");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserKey userKey = LoginFilter.getCredentials(request);
		
		if (userKey == null) {
			// No cookies
			// Forward to regular login page
			RequestDispatcher view = request.getRequestDispatcher("login.jsp");
			view.forward(request, response);
			return;
		}
		
		else {
			// User has cookies
			try {
				// We verify if the cookies are still valid
				UserDAO.get(userKey);
				
				// If the cookies are still valid and we can get a user from them,
				// Forward to the home page
				response.sendRedirect("courses.jsp");
				return;
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
				
				// If the user cannot be found, then the cookies have been invalidated 
				// and the user needs to log in again
				
				RequestDispatcher view = request.getRequestDispatcher("login.jsp");
				view.forward(request, response);
				return;
			}
			
		}
	}
}
