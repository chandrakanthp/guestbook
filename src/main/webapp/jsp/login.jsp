<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
	<title>Welcome to Guest Application </title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
</head>
<body class="security-app">		
		<form:form action="/login" method="post" modelAttribute="userLoginInfo">
		<div class="container">
			<font color='red'>${SPRING_SECURITY_LAST_EXCEPTION.message}</font>
			<h4>Guest Application Login</h4> 
			<div class="form-group">
				<form:input type="text" class="form-control" path="username"
					placeholder="User Name" required="true" oninvalid="this.setCustomValidity('Enter User Name Here')" onKeyup="this.setCustomValidity('')"/>
			</div>
			<div class="form-group">
				<form:input type="password" class="form-control" path="password"
					placeholder="Password" required="true" oninvalid="this.setCustomValidity('Enter Password Here')" onKeyup="this.setCustomValidity('')"/>
			</div>
			<div>
				<input type="submit" value="Sign In" class="btn btn-default" />
			</div>
		</div>
	</form:form>

</body>
</html>