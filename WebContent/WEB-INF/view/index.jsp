<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Computer" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Computer Database</title>
</head>
<body>
	<h1>Computer Data base</h1>
	 <c:out value="test"/><br/>

	 
	 
	<form  action="/computer-database/ShowResultServlet" method="post" >
		<h3>What do you want to do?</h3>
		<input type="radio" name="action" value="listComputers"/><label>List computers</label><br>
		<input type="radio" name="action" value="listCompanies"><label>List companies</label><br>
		<input type="radio" name="action" value="computerDetails"><label>Show computer details</label><br>
		<input type="radio" name="action" value="updateComputer"><label>Update a computer</label><br>
		<input type="radio" name="action" value="deleteComputer"><label>Delete a computer</label><br>
		<input type="radio" name="action" value="createComputer"><label>Create a computer</label><br>
		<input type="submit" value="Send">
	</form>
</body>
</html>