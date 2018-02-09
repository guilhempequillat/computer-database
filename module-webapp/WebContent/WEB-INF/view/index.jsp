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
	        	<div class="col-xs-12 col-sm-8 col-sm-offset-2 btn-group  btn-group-toggle" id="groupe-btn" data-toggle="buttons">
		        	<label class="btn btn-default col-xs-6 active" id="label-register">
						<input type="radio" name="options"  id="btn-register"><spring:message code="Register"/>
					</label>
					<label class="btn btn-default col-xs-6" id="label-login">
						<input type="radio" name="options"  id="btn-login"><spring:message code="Login"/>
					</label>
	        	</div>
        	</div>
            <div class="row">
                <div class="col-xs-12 col-sm-8 col-sm-offset-2 box">
                    <form action="perform-login" method="POST" id="login-form" style="display:none;">
                     <h1><spring:message code="Login"/></h1>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="UserName"/></label>
                                <input type="text" class="form-control" id="username" name="username" placeholder="">
                            </div>
                            <div class="form-group">
                                <label for="password"><spring:message code="Password"/></label>
                                <input type="password" class="form-control" id="password" name="password" placeholder="">
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="Login"/>" class="btn btn-primary">
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12 col-sm-8 col-sm-offset-2 box">
                    <form action="register" method="POST" id="register-form">
                    <h1><spring:message code="Register"/></h1>
                        <fieldset>
                            <div class="form-group">
                                <label for="username"><spring:message code="UserName"/></label>
                                <input type="text" class="form-control" id="username" name="username" placeholder="">
                            </div>
                            <div class="form-group">
                                <label for="username">Email</label>
                                <input type="text" class="form-control" id="email" name="email" placeholder="">
                            </div>
                            <div class="form-group">
                                <label for="repassword"><spring:message code="Password"/></label>
                                <input type="password" class="form-control" id="repassword" name="repassword" placeholder="">
                            </div>
                            <div class="form-group">
                                <label for="password"><spring:message code="ConfirmPassword"/></label>
                                <input type="password" class="form-control" id="password" name="password" placeholder="">
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="Register"/>" class="btn btn-primary">
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="js/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
	<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>

	<script src="js/index.js"></script> 
</body>
</html>