<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.uy.antel.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Servidor Central Agencia</title>
</head>
<style type="text/css">
label {
	display: block;
	width: 150px;
	float: left;
}
</style>
<body>
	<h1>Login Servidor Central Agencia</h1>
	<form action="Login" method="post">
		<input type="hidden" name="operacion" value="login">
		<label for="usuario">Usuario</label> <input type="text" name="usuario"></input>
		<br> <label for="password">Password</label> <input
			type="password" name="password"></input> <br> <input
			type="submit" value="Ingresar"></input>
	</form>
	<br>
	<br>
	<c:if test="${not empty respuesta}">
		<h3><c:out value="${respuesta}" /></h3>
	</c:if>
</body>
</html>