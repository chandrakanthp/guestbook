<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <!DOCTYPE html>  
<html lang="en">  
  <head>  
     <title>Guest Notes Entry</title>  
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
     <script>
     function logoutApp()
 	 {
 			form = document.getElementById("logoutForm");
 			form.submit()
 	 }
     </script>  
  </head>  
  <body> 
  	<form id="logoutForm" method="get" action="/login">
	</form>
  	<div id="status" style="color: green;"><%=request.getAttribute("resStatus")!=null?request.getAttribute("resStatus"):"" %>  	
  	</div> 
	<div class="container">
	<a href="#" class="f90-logout-button"onclick="logoutApp()" style="text-align:right; "><h4>LogOut</h4></a>  
  	<h2>Enter Notes or select a image</h2>  
  
	<form:form style="width:300px" action="/insertNotes" modelAttribute="guestNotesDetails" enctype="multipart/form-data">  
	  	<div class="form-group">  
	    	<label for="Notes">Notes</label>  
	    	<form:textarea type="text" class="form-control" path="notes" placeholder="enter notes" />  
	  	</div>     
  		
  			<div class="form-group">  
	    	<label for="Notes">Upload Image</label>  
	    	<form:input type="file" class="form-control" path="imageFile" placeholder="Select Image File" accept="image/*" />  
	  	</div>  
	  	
	  	
  		<button type="submit" class="btn btn-default">Submit</button>  
	</form:form>    
	</div> 
  </body>  
</html>  

 