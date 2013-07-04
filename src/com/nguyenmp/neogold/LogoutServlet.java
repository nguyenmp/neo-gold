package com.nguyenmp.neogold;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nguyenmp.neogold.beans.UserKey;
import com.nguyenmp.neogold.dao.UserDAO;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 6292013L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserKey userKey = LoginFilter.getCredentials(request);

		// If there are active cookies
		if (userKey != null) {
			
			// Remove them from the database
			UserDAO.remove(userKey);
			
			// Remove them from the user's browser cache
			Cookie nullCookie = new Cookie(LoginFilter.COOKIE_NAME, "");
			response.addCookie(nullCookie);
		}
		
		// Redirect to the homepage
		response.sendRedirect("/");
	}
}
