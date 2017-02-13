<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
 <%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8">
<title>Livros de tecnologia!</title>

<!--  Bootstrap -->
<c:url value="/resources/css" var="cssPath" />
<link rel="stylesheet" href="${cssPath}/bootstrap.min.css" >
<link rel="stylesheet" href="${cssPath}/bootstrap-theme.min.css" >

<style type="text/css">

	body {
		padding: 60px 0px;
		}

</style>

</head>
<body>


<div class="container">

<h1> Login da casa do codigo</h1>


	<form:form servletRelativeAction="/login" method="post" >
	        
	        <div class="form-group"> 
				<label>E-mail</label>				
				<input name="username" type="text" cssClass="form-control"/>
			</div>
			<div class="form-group"> 	
				<label>Password</label>				
				<input type="password" name="password" cssClass="form-control"/>		
				
			</div>			
			<button type="submit"class="btn btn-primary">Logar</button>
	
	</form:form>
	
</div>

</body>
</html>
