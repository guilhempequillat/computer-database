<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Computer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Computer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Results found</h1><br>
	<h3>Number of results : ${listComputer.size()} </h3><br>
	<table>
		<tr>
	       <th>ID</th>
	       <th>Name</th>
	       <th>Introduced Date</th>
	       <th>Discontinued Date</th>
	       <th>Company</th>
	   </tr>
		<c:forEach items="${listComputer}" var="computer">
		   <tr>
		       <td>${ computer.id }</td>
		       <td>${ computer.name }</td>
		       <td>${ computer.introduced }</td>
		       <td>${ computer.discontinued }</td>
		       <td>${ computer.company }</td>
		   </tr>
	    </c:forEach>
	</table>
	<a href = "/computer-database/indexServlet"><button>Back to Menu</button></a>
</body>
</html>