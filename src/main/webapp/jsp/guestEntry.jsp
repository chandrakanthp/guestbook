<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>  
<html lang="en">  
 <head>  
	<title>Guest Notes Entry</title>  
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="/css/viewGuest.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
 </head>  
 <body> 
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
	<div id="status"><h4><font color="green"><%=request.getAttribute("resStatus")!=null?request.getAttribute("resStatus"):"" %></font></h4>	
	</div>  	  
	<form:form action="/insertNotes" modelAttribute="guestNotesDetails" enctype="multipart/form-data">
	<h2 class="text-center">Enter Notes or select a image</h2>  
	  	<div class="form-group purple-border">  
	    	<label for="Notes">Notes<font color="red">*</font></label>  
	    	<form:textarea type="text" class="form-control" path="notes" placeholder="Enter Notes" rows="3" required="true"/>  
	  	</div> 
  		<div class="form-group">  
	    	<label for="Notes">Upload Image</label>
	    	<form:input type="file" class="form-control-file" path="imageFile" placeholder="Select Image File" accept="image/*" />  	    	
	  	</div>  
	  	<div class="form-group">	  	
  			<button type="submit" class="btn btn-primary btn-block">Submit</button>
  		</div>  
	</form:form>    
	</div> 
</body>  
</html>  
 