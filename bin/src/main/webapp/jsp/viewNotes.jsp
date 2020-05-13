<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="com.app.guestbook.model.GuestNotesDetails" %> 
<!DOCTYPE html>
<html>
<head>
	<title>Guest Notes Details</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>  

<link rel="stylesheet" href="/css/viewGuest.css">
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
	function logoutApp()
	{
			form = document.getElementById("logoutForm");
			form.submit()
	}
</script>
</head>
<body class="security-app">
<form id="notesForm" method="get" action="/approveReject">
<input type=hidden name="id" id="id"/>
<input type=hidden name="status" id="status"/>
</form>
<form id="logoutForm" method="get" action="/login">
</form>
<div id="status"><h4><font color="green"><%=request.getAttribute("status")!=null?request.getAttribute("status"):"" %></font></h4>
	
</div>
<div class="container">
<a href="#" class="f90-logout-button" onclick="logoutApp()" style="text-align:right; "><h4>LogOut</h4></a>
<h2>Approve/Remove Guest Notes</h2> 
  <div class="row">
    <div class="col-12">
      <table class="table table-bordered">
        <thead>
          <tr>
            <th scope="col">Sl. No</th>
            <th scope="col">Date</th>
            <th scope="col">Guest Name</th>
            <th scope="col">Notes</th>
            <th scope="col">Actions</th>
          </tr>
        </thead>
        <tbody>        
  		<%
  			GuestNotesDetails[] 	guestNotesDetails =  (GuestNotesDetails[]) request.getAttribute("guestNotesDetails");
			for(int i=0;i<guestNotesDetails.length;i++)
			{
		%>
	  	<tr>
            <td scope="row"><%=i+1%></td>
            <td><%=guestNotesDetails[i].getDateTime() %></td>
            <td><%=guestNotesDetails[i].getUsername() %></td>
            <td><%=guestNotesDetails[i].getNotes() %></td>
            <td>              
				<% if("N".equalsIgnoreCase(guestNotesDetails[i].getApproveStatus())){ %>
              		<button type="button" class="btn btn-success" id="appr<%=guestNotesDetails[i].getNotes_details_id()%>" onclick="approveReject('approve','<%=guestNotesDetails[i].getNotes_details_id()%>')"><i class="fas fa-edit">Approve</i></button>
            		<button type="button" class="btn btn-danger" id="rej<%=guestNotesDetails[i].getNotes_details_id()%>" onclick="approveReject('remove','<%=guestNotesDetails[i].getNotes_details_id()%>')"><i class="far fa-trash-alt">Remove</i></button>
         		<% } %>
            </td>
          </tr>        
			<% } %>        
        
    	</tbody>
      </table>
    </div>
  </div>
</div>
</body>
</html>