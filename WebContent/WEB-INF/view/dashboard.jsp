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
        </div>
    </header>
	
    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${ count } Computers found
            </h1>
            <button id="toggleFilter" class="btn btn-default">Filter</button>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
	                <div id="filter">
	                    <form id="searchForm" action="dashboard" method="POST" class="form-inline">
	                    	<div class="row">
	                        	<div class = "col-sm-5">
	                        		<label>By Name : </label>
	                        	</div>
	                        	<div class = "col-sm-7">
	                        		<input type="search" id="filterName" class="form-control filter" name="filterName" placeholder="Search name" />
	                        	</div>
	                        </div>
	                        <div class="row">
		                        <div class = "col-sm-5">
		                        	<label>By Introduced : </label>
		                        </div>
		                        <div class = "col-sm-7">
		                        	<input type="search" id="filterIntroduced" class="form-control filter" name="filterIntroduced" placeholder="Search Introduced" />
								</div>
							</div>
							<div class="row">
		                        <div class = "col-sm-5">
	                        		<label>By Discontinued : </label>
	                        	</div>
	                        	<div class = "col-sm-7">
	                        		<input type="search" id="filterDiscontinued" class="form-control filter" name="filterDiscontinued" placeholder="Search Discontinued" />
	                        	</div>
	                        </div>
	                        <div class="row">
		                        <div class = "col-sm-5">
	                        		<label>By Company : </label>
	                        	</div>
	                        	<div class = "col-sm-7">
	                        		<input type="search" id="filterCompany" class="form-control filter" name="filterCompany" placeholder="Search Company" />
	                        	</div>
	                        </div>
	                        <input type="submit" id="searchsubmit" value="Apply Filter" class="btn btn-primary" />
	                    </form>
                    </div>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="add-computer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
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
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

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
                            <a href="dashboard?order=name">Computer name</a>
                        </th>
                        <th>
                            <a href="dashboard?order=introduced">Introduced date</a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <a href="dashboard?order=discontinued">Discontinued date</a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <a href="dashboard?order=company">Company</a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                    <c:forEach items="${listComputer}" var="computer" varStatus="status">
                    	<%-- <c:if test="${status.count > beginComputerDisplay && status.count < beginComputerDisplay+numberComputerToShow}"> --%>
							<ex:Link computer="${computer}" />
						<%-- </c:if> --%>
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