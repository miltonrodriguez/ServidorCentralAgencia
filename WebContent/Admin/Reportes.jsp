<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.uy.antel.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Servidor Central Agencia Reportes</title>
</head>
<style type="text/css">
label {
	display: block;
	width: 150px;
	float: left;
}
</style>
<body>
	<h1>Servidor Central Agencia Reportes</h1>
	<form action="Controlador">
		<label for="optMes">Seleccione Mes</label>
		<select name="optMes">
			<c:forEach begin="0" end="12" var="iter">
				<option value="${iter}">"${iter}"</option>
			</c:forEach>
		</select>
		<br> 
		<label for="tipoReporte">Tipo de reporte</label> <input type="radio"
			name="tipoReporte" value="xls">Excel <input type="radio"
			name="tipoReporte" value="html">HTML <input type="radio"
			name="tipoReporte" value="pdf">PDF<br> <input
			type="submit" value="Generar Reporte"></input>
	</form>

</body>
</html>