<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
   <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
            <div class="pull-right">
            	<a href="?lang=fr"><img src="image/french.png" style="width:20px;"></a>
            	<a href="?lang=en"><img src="image/english.png" style="width:27px;"></a>
            </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="AddComputer"/></h1>
                    <form action="login" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Login</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="Password"/></label>
                                <input type="password" class="form-control" id="introduced" name="introduced" placeholder="">
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Login" class="btn btn-primary">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="js/jquery.min.js"></script>
	<script src="js/addComputer.js"></script>
</body>
</html>