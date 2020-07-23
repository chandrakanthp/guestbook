<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Guest Notes Details</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="/css/viewGuest.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<script>
		function approveReject(approve, id)
		{
			if(confirm("Are you sure to "+approve))
			{
				form = document.getElementById("notesForm");
				document.getElementById("id").value=id;
				document.getElementById("status").value=approve;
				form.submit();		
			}
		}
	</script>	
</head>
<body class="security-app">
<form id="notesForm" method="get" action="/approveReject">
	<input type=hidden name="id" id="id"/>
	<input type=hidden name="status" id="status"/>
</form>
<nav class="navbar navbar-light" style="background-color: #34495E">
	<div class="container-fluid">
		<div class="navbar-header">
			<label class="navbar-brand mb-1 h1">Guest Application</label>
		</div>   
		<div class="nav navbar-nav">
			<form:form id="logoutForm" method="POST" action="/logout">
				<input type="submit" class="btn btn-danger" value="Logout"/>  			  			
			</form:form>
		</div>
	</div>
</nav>  
<div class="container">
<div id="status"><h4><font color="green">${status}</font></h4></div>
<h2>Approve/Remove Guest Notes</h2> 
  <div class="row">
    <div class="col-12">
      <table class="table table-striped table-responsive-md btn-table table-hover">      
        <thead>
          <tr>
            <th scope="col">Sl. No</th>
            <th scope="col">Date</th>
            <th scope="col">Guest Name</th>
            <th scope="col">Notes</th>
            <th scope="col">Image</th>
            <th scope="col">Actions</th>
          </tr>
        </thead>
        <tbody> 
        <c:forEach var="guestNotesDetails" items="${guestNotesDetails}" varStatus="loop">
        <tr>
            <td scope="row">${loop.count}</td>
            <td>${guestNotesDetails.toDateFormat()}</td>
            <td>${guestNotesDetails.getUsername()}</td>
            <td>${guestNotesDetails.getNotes()}</td>            
            <td>
            	<c:if test="${guestNotesDetails.getImage_file_name() != null}">      
	            	<a href="/viewImage?id=${guestNotesDetails.getNotes_details_id()}">${guestNotesDetails.getImage_file_name()}</a>
	         	</c:if>
	        </td>           
            <td> 
            	<c:if test="${guestNotesDetails.getApproveStatus()=='N'}">
            		<button type="button" class="btn btn-success" id="appr${guestNotesDetails.getNotes_details_id()}" onclick="approveReject('approve','${guestNotesDetails.getNotes_details_id()}')">Approve</button>
            		<button type="button" class="btn btn-danger" id="rej${guestNotesDetails.getNotes_details_id()}" onclick="approveReject('remove','${guestNotesDetails.getNotes_details_id()}')">Remove</button>
            	</c:if> 
            	<c:if test="${guestNotesDetails.getApproveStatus()=='A'}">
            		Approved
            	</c:if> 
            </td>
         </tr>  
         </c:forEach>               
    	</tbody>
      </table>
    </div>
  </div>
</div>
</body>
</html>
