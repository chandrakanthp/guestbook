<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <!DOCTYPE html>  
<html lang="en">  
  <head>  
     <title>Guest Notes Entry</title>  
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
     <script>
     /*
     function logoutApp()
 	 {
 			form = document.getElementById("logoutForm");
 			form.submit()
 	 }*/
     </script>  
  </head>  
  <body> 
  <br/>
  	<div class="container">
  		<form:form id="logoutForm" method="POST" action="/logout">
  			<div class="row">
  				<div class="col-sm-12">
      				<div class="pull-right">
            			<input type="submit" class="btn btn-danger" value="Logout"/>	
      				</div>
  				</div>
  			</div>
  			<!--  
  			<h4><a href="#" class="f90-logout-button" onClick="logoutApp()" style="text-align:right; ">LogOut</a></h4>
  			-->	
  		</form:form>
	</div>
	<!--  
  	<form id="logoutForm" method="get" action="/login">
	</form>
	-->
	<%-- 
  	<div id="status" style="color: green;"><%=request.getAttribute("resStatus")!=null?request.getAttribute("resStatus"):"" %>  	
  	</div>
  	--%> 
	<div class="container">
	<!--  
	<a href="#" class="f90-logout-button" onClick="logoutApp()" style="text-align:right; "><h4>LogOut</h4></a> 
	--> 
	<div id="status"><h4><font color="green"><%=request.getAttribute("resStatus")!=null?request.getAttribute("resStatus"):"" %></font></h4>	
	</div>
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
 