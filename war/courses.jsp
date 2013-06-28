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
	<script src="jquery.js"></script>
	<script src="jquery-ui-autocomplete.js"></script>
	<script src="jquery.select-to-autocomplete.min.js"></script>
	<script type="text/javascript" charset="utf-8">
	  (function($){
	    $(function(){
	      $('#department-selector').selectToAutocomplete();
	      $('form').submit(function(){
	        alert( $(this).serialize() );
	        return false;
	      });
	    });
	  })(jQuery);
	</script>
	<style type="text/css" media="screen">
	  body {
	    font-family: Arial, Verdana, sans-serif;
	    font-size: 13px;
	  }
    .ui-autocomplete {
      padding: 0;
      list-style: none;
      background-color: #fff;
      width: 218px;
      border: 1px solid #B0BECA;
      max-height: 350px;
      overflow-y: scroll;
    }
    .ui-autocomplete .ui-menu-item a {
      border-top: 1px solid #B0BECA;
      display: block;
      padding: 4px 6px;
      color: #353D44;
      cursor: pointer;
    }
    .ui-autocomplete .ui-menu-item:first-child a {
      border-top: none;
    }
    .ui-autocomplete .ui-menu-item a.ui-state-hover {
      background-color: #D5E5F4;
      color: #161A1C;
    }
	</style>
  </head>

  <body>
    <form action="courses.jsp" method="get">
      <select id="department-selector" name="department" autofocus="autofocus" autocorrect="off" autocomplete="off">
        <option value="" selected="selected">Select Department</option>
        <c:forEach var="department" items="${departments}">
          <option value="${department.id}" data-alternative-spellings="${department.alternatives}">${department.name}</option>
        </c:forEach>
      </select>
      <select name="quarter" >
        <c:forEach var="quarter" items="${quarters}">
          <option value="${quarter.value}">${quarter.name}</option>
        </c:forEach>
      </select>
      <input type="submit" value="Search" />
    </form>
    
    <h3>results</h3>
    
    <!--- List of Courses Available --->
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
