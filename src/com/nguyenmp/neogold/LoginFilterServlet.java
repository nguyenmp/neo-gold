package com.nguyenmp.neogold;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * Servlet Filter to check if a user is authenticated or not.
 * @author Mark Nguyen
 *
 */
public class LoginFilterServlet implements Filter {
	public static final String ATTRIBUTE_USER_BEAN = "userBean";

	@Override
	public void init(FilterConfig config) throws ServletException {
		// Do nothing
	}

	@Override
	public void destroy() {
		// Do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		// Ignore stuff from root and login
		String requestedURI = ((HttpServletRequest) request).getRequestURI();
		if (requestedURI.toLowerCase().startsWith("/login")) {
			chain.doFilter(request, response);
			return;
		}
		
		UserKey userKey = getCredentials(request);
		
		if (userKey == null) {
			redirectToLogin(response);
		} else {
			try {
				User user = UserDAO.get(userKey);
				request.setAttribute(ATTRIBUTE_USER_BEAN, user);
				chain.doFilter(request, response);
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
				redirectToLogin(response);
			}
		}
	}
	
	/**
	 * We take our specific cookie from the ServletRequest and extract the 
	 * salt and entity ID from the value.  From here, someone can go to the 
	 * database and extract the username/password and other things stored in 
	 * {@link User} from the DB.  We expect the value of the cookie to be in 
	 * the form of two string joined by an ampersand (&) as specified by 
	 * {@link UserKey#toString()}
	 * @param request the ServletRequest to parse and get credentials from
	 * @return the {@link UserKey} to extract the credentials from the DB, or null 
	 * if either the cookie does not exist or the cookie's value is malformed
	 * @see {@link UserDAO#get(UserKey)}
	 * @see {@link UserKey#fromString(String)}
	 */
	private UserKey getCredentials(ServletRequest request) {
		// Get cookies from the request object
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Cookie[] cookies = httpRequest.getCookies();
		
		// Iterate through cookies and find ours
		Cookie ourCookie = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("NEO_GOLD")) ourCookie = cookie;
		}
		
		// If there is no cookie, we return null
		if (ourCookie == null) return null;
		// If cookie exists, parse it
		else return UserKey.fromString(ourCookie.getValue());
	}
	
	
	private void redirectToLogin(ServletResponse response) throws IOException {
		((HttpServletResponse) response).sendRedirect("login");
	}
}
