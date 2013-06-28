<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Hello App Engine</title>
  </head>

  <body>
  	<form action="courses.jsp" method="get">
  		<input type="text" name="value" />
  		<input type="submit" value="Search" />
  	</form>
  	
  	<h3>results</h3>
  	
  	<ul>
  		<li>
  			<h4><a href="course?id=1234">CLASS 40  - GREEK MYTHOLOGY</a></h4>
  			<form action="course" method="post">
  				<input type="hidden" name="action" value="add" />
  				<input type="submit" value="Add"/>
  			</form>
  		</li>
  		<li>
  			<h4><a href="course?id=12sd224">CLASS 40H  - GREEK MYTH-HONORS</a></h4>
  		</li>
  	<ul>
  </body>
</html>
