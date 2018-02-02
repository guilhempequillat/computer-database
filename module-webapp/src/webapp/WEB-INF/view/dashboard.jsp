<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.NumberFormat" %>
<%@ taglib prefix = "ex" uri = "custom.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
            <h1 id="homeTitle">
                ${ count } <spring:message code="label"/><p></p>
            </h1>
            <button id="toggleFilter" class="btn btn-default"><spring:message code="Filter"/></button>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
	                <div id="filter">
	                    <form id="searchForm" action="dashboard" method="POST" class="form-inline">
	                    	<div class="row">
	                        	<div class = "col-sm-5">
	                        		<label><spring:message code="ByName"/></label>
	                        	</div>
	                        	<div class = "col-sm-7">
	                        		<input type="search" id="filterName" class="form-control filter" name="filterName" placeholder="<spring:message code="SearchName"/>" />
	                        	</div>
	                        </div>
	                        <div class="row">
		                        <div class = "col-sm-5">
		                        	<label><spring:message code="ByIntroduced"/></label>
		                        </div>
		                        <div class = "col-sm-7">
		                        	<input type="search" id="filterIntroduced" class="form-control filter-date" name="filterIntroduced" placeholder="<spring:message code="SearchIntroduced"/>" />
								</div>
							</div>
							<div class="row">
		                        <div class = "col-sm-5">
	                        		<label><spring:message code="ByDiscontinued"/></label>
	                        	</div>
	                        	<div class = "col-sm-7">
	                        		<input type="search" id="filterDiscontinued" class="form-control filter-date" name="filterDiscontinued" placeholder="<spring:message code="SearchDiscontinued"/>" />
	                        	</div>
	                        </div>
	                        <div class="row">
		                        <div class = "col-sm-5">
	                        		<label><spring:message code="ByCompany"/></label>
	                        	</div>
	                        	<div class = "col-sm-7">
	                        		<input type="search" id="filterCompany" class="form-control filter" name="filterCompany" placeholder="<spring:message code="SearchCompany"/>" />
	                        	</div>
	                        </div>
	                        <input type="submit" id="searchsubmit" value="<spring:message code="ApplyFilter"/>" class="btn btn-primary" />
	                    </form>
                    </div>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="add-computer"><spring:message code="AddComputer"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="Edit"/></a>
                </div>
            </div>
        </div>
        <form id="deleteForm" action="delete-computer" method="POST">
        	<div class=" container">
        		<div class = "pull-left">
        			<input type="password" name="password" class=" editMode form-control container" placeholder = "Password"/>
        		</div>
        	</div>
            <input type="hidden" name="selection" value="">
        </form>
        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                   <!--  <i class="fa fa-trash-o fa-lg"></i> -->
                                   <img src="image/trash.png" style="width:20px;"/>
                                </a>
                            </span>
                        </th>
                        <th>
                            <a href="dashboard?order=name"><spring:message code="ComputerName"/></a>
                        </th>
                        <th>
                            <a href="dashboard?order=introduced"><spring:message code="IntroducedDate"/></a>
                        </th>
                        <th>
                            <a href="dashboard?order=discontinued"><spring:message code="DiscontinuedDate"/></a>
                        </th>
                        <th>
                            <a href="dashboard?order=company"><spring:message code="Company"/></a>
                        </th>
                    </tr>
                </thead>
                <tbody id="results">
                    <c:forEach items="${listComputer}" var="computer" varStatus="status">
						<ex:Link computer="${computer}" />
				    </c:forEach>
                </tbody>
            </table>
        </div>
    </section>
    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
	            <c:if test="${beginComputerDisplay-numberComputerToShow >= 0}">
	                <li>
	                    <a href="dashboard?beginComputerDisplay=${ beginComputerDisplay - numberComputerToShow }" aria-label="Previous">
	                      <span aria-hidden="true">&laquo;</span>
		                  </a>
		              </li>
	              </c:if>
	              <c:if test="${count-numberComputerToShow > 0}">
		              <c:forEach var="i" begin="${beginComputerDisplay}" end="${count-numberComputerToShow}" step="${numberComputerToShow}" varStatus="status">
		              	<c:if test="${status.count < 5}">
		              		<li>
		              			<a href="dashboard?beginComputerDisplay=${ i + numberComputerToShow }"> 
		              				${  (i + numberComputerToShow)/numberComputerToShow } 
		              		 	</a>
		              		 </li>
		              	</c:if>
		              	<c:if test="${status.count == 5}">
		              		<li><a href="dashboard?beginComputerDisplay=${ i + numberComputerToShow }">... </a></li>
		              	</c:if>
		              </c:forEach>
		         </c:if>
              	<c:if test="${beginComputerDisplay+numberComputerToShow <= count}">
	              <li>
	                <a href="dashboard?beginComputerDisplay=${ beginComputerDisplay + numberComputerToShow }" aria-label="Next">
	                    <span aria-hidden="true">&raquo;</span>
	                </a>
	            </li>
            </c:if>
        </ul>
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <a href="dashboard?numberComputerToShow=10&beginComputerDisplay=0"><button type="button" class="btn btn-default">10</button></a>
            <a href="dashboard?numberComputerToShow=50&beginComputerDisplay=0"><button type="button" class="btn btn-default">50</button></a>
            <a href="dashboard?numberComputerToShow=100&beginComputerDisplay=0"><button type="button" class="btn btn-default">100</button></a>
        </div>
    </footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>
</body>
</html>