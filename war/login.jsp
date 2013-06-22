<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->

<%@ page import="java.util.*" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Hello App Engine</title>
  </head>

  <body>
  	<form action="login" method="post">
  		<label for="username">Username</label>
  		<input type="text" name="username" />
  		<br />
  		
  		<label for="password">Password</label>
  		<input type="password" name="password" />
  		<br />
  		
  		<input type="submit" value="Submit" />
  	</form>
  	
  	<ul>
  		<%
  		List<Entity> users = (List<Entity>) request.getAttribute("users");
  		for (Entity user : users) { %>
  		
  		<li><% out.print("" + user.getProperty("username") + user.getProperty("created")); %> </li>
  		
  		<% } %>
  	<ul>
  </body>
</html>
