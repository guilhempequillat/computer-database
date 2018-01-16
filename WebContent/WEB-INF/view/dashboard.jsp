<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
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
                ${ listComputer.size() } Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="add-computer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
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
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                       <!--  <i class="fa fa-trash-o fa-lg"></i> -->
                                       <img src="image/trash.png" style="width:20px;"/>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                    <c:forEach items="${listComputer}" var="computer" varStatus="status">
                    	<c:if test="${status.count > beginComputerDisplay && status.count < beginComputerDisplay+numberComputerToShow}">
						    <tr>
							    <td class="editMode">
		                            <input type="checkbox" name="cb" class="cb" value="0">
		                        </td>
						       	<td>
							       	<a href="edit-computer?id=${ computer.id }" onclick="">
							       		${ computer.name }
							       	<a/>
						       	</td>
						       </a>
						       <td>${ computer.introduced }</td>
						       <td>${ computer.discontinued }</td>
						       <td>${ computer.company }</td>
						   </tr> 
						</c:if>
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
              <c:forEach var="i" begin="${beginComputerDisplay}" end="${listComputer.size()-numberComputerToShow}" step="${numberComputerToShow}" varStatus="status">
              	<c:if test="${status.count < 5}">
              		<li><a href="dashboard?beginComputerDisplay=${ i + numberComputerToShow }"> ${ i + numberComputerToShow } </a></li>
              	</c:if>
              	<c:if test="${status.count == 5}">
              		<li><a href="dashboard?beginComputerDisplay=${ i + numberComputerToShow }">... </a></li>
              	</c:if>
              </c:forEach>
              <c:if test="${beginComputerDisplay+numberComputerToShow <= listComputer.size()}">
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