<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${ idComputer }
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="edit-computer" method="POST">
                        <input type="hidden" value="0" id="id"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                        	<c:choose>
                        		<c:when test="${ computer.name != null}">
		                            <div class="form-group">
		                                <label for="computerName">Computer name</label>
		                                <input type="text" class="form-control" id="computerName" value="${ computer.name }" name="computerName">
		                            </div>
		                        </c:when>
		                        <c:otherwise>
		                        	<div class="form-group">
		                                <label for="computerName">Computer name</label>
		                                <input type="text" class="form-control" id="computerName" placeholder="Computer Name" name="computerName">
		                            </div>
		                        </c:otherwise>
	                        </c:choose>
                            <c:choose>
	                            <c:when test="${ computer.introduced != null}">
		                            <div class="form-group">
		                                <label for="introduced">Introduced date</label>
		                                <input type="date" class="form-control" id="introduced" placeholder="Introduced date" value="${ computer.introduced }" name="introduced">
		                            </div>
	                            </c:when> 
	                            <c:otherwise>
							    	<div class="form-group">
		                                <label for="introduced">Introduced date</label>
		                                <input type="date" class="form-control" id="introduced" placeholder="Introduced date" name="introduced">
	                            	</div>
							    </c:otherwise> 
                            </c:choose>
                            <c:choose>
	                            <c:when test="${ computer.discontinued != null}">
		                            <div class="form-group">
		                                <label for="discontinued">Introduced date</label>
		                                <input type="date" class="form-control" id="discontinued" placeholder="Discontinued date" value="${ computer.discontinued }" name="discontinued">
		                            </div>
	                            </c:when> 
	                            <c:otherwise>
							    	<div class="form-group">
		                                <label for="discontinued">Discontinued date</label>
		                                <input type="date" class="form-control" id="discontinued" placeholder="Discontinued date" name="discontinued">
	                            	</div>
							    </c:otherwise> 
                            </c:choose>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                	<c:forEach items="${listCompany}" var="company">
                                		<c:choose>
		                            		<c:when test="${ company.id == computer.company_id }">
	                                    		<option value="${company.id}" selected="selected" >${company.name}</option>
				                            </c:when> 
				                            <c:otherwise>
				                           		<option value="${company.id}" >${company.name}</option>
				                            </c:otherwise>
			                            </c:choose>
                                    </c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>