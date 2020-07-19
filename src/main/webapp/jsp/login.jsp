<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
	<title>Welcome to Guest Application </title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	<style>
		.login-form {
		    width: 500px;
		    margin: 50px auto;
		  	font-size: 15px;
		}
		.login-form form {
		    margin-bottom: 15px;
		    background: #f7f7f7;
		    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
		    padding: 30px;
		}
		.login-form h2 {
		    margin: 0 0 15px;
		}
		.form-control, .btn {
		    min-height: 38px;
		    border-radius: 2px;
		}
		.btn {        
		    font-size: 15px;
		    font-weight: bold;
		}
	</style>
</head>
<body class="security-app">	
	<div class="login-form">
		<form:form action="/login" method="post" modelAttribute="userLoginInfo">
		<div class="container">
			<font color='red'>${SPRING_SECURITY_LAST_EXCEPTION.message}</font>
			<h2 class="text-center">Guest Application Login</h2> 
			<div class="form-group">
				<form:input type="text" class="form-control" path="username"
					placeholder="User Name" required="true" oninvalid="this.setCustomValidity('Enter User Name Here')" onKeyup="this.setCustomValidity('')"/>
			</div>
			<div class="form-group">
				<form:input type="password" class="form-control" path="password"
					placeholder="Password" required="true" oninvalid="this.setCustomValidity('Enter Password Here')" onKeyup="this.setCustomValidity('')"/>
			</div>
			<div class="form-group">
				<input type="submit" value="Sign In" class="btn btn-primary btn-block" />
			</div>
		</div>
		</form:form>
	</div>
</body>
</html>
