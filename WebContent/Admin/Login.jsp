<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.uy.antel.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<style type="text/css">
label {display: block;width: 150px;float: left;}
</style>
<body>
<% 
	String resp = (String)request.getAttribute("respuesta");
if (resp!= null && resp.length() != 0)
{%>
	<h1><%=resp%></h1>
	<br>	
<%
}
%>
	<h1>Login</h1>
	<form action="Controlador">
		<input type="hidden" name="operacion" value="login">
		<label for="usuario">Usuario</label> <input type="text" name="usuario"></input>
		<br> 
		<label for="password">Password</label> <input type="password" name="password"></input>
		<br>
		<input type="submit" value="Ingresar"></input>
	</form>

</body>
</html>