<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.uy.antel.*,java.util.ArrayList,java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
<%@ include file="css/estilos.css" %>
</style>
<title>Servidor Central Agencia Reportes</title>
</head>

<body>
	<%
		int mesA = (new Date().getMonth())+1;
	%>
	<c:set var="mesActual" value="<%=mesA%>" scope="page" />
	<a class="logout" href="Login?operacion=logout">Logout</a>
	<br>
	<h1>Servidor Central Agencia Reportes</h1>
	<br>
	<table>
	<form action="Controlador">
	
		<input type="hidden" name="operacion" value="getReporte"> 
	<tr><td>Seleccione Mes</td>
	 <td>
		<select name="optMes">
			<c:forEach begin="1" end="12" var="iter">
				<c:choose>
					<c:when test="${iter == mesActual}">
						<option value="${iter}" selected>${iter}</option>
					</c:when>
					<c:otherwise>
						<option value="${iter}">${iter}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
		</td>
		</tr>
		 <tr><td>Tipo de reporte</td> 
			<td><input type="radio" name="tipoReporte" value="Diario" checked>Reporte mensual diario
			<input type="radio" name="tipoReporte" value="Franja">Reporte mensual por franjas
			</td>
		</tr>
		 
		<tr><td>
			<input type="submit" value="Generar Reporte"></input>
			</td>
		</tr>
	</form>
	</table>
	<br>
	<br>
	<c:if test="${not empty ventaMensualDiariaList}">
		<h1>Reporte de ventas mensuales Diarias del mes <c:out value="${mes}"/> del corriente año <c:out value="${anio}"/> </h1>
		<table class="table">
			<tr>
				<th class="th"><c:out value="Dia" /></th>
				<th class="th"><c:out value="Importe total Hora" /></th>
				<th class="th"><c:out value="Cantidad de tickets" /></th>
			</tr>
			<c:forEach var="ventaMensualDiaria" items="${ventaMensualDiariaList}">
				<tr>
					<td class="td"><c:out value="${ventaMensualDiaria.dia}" /></td>
					<td class="td"><c:out value="${ventaMensualDiaria.importeTotal}" /></td>
					<td class="td"><c:out value="${ventaMensualDiaria.cantTicket}" /></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<c:if test="${not empty ventaMensualFranjaList}">
		<h1>Reporte de ventas mensuales por franja horaria del mes <c:out value="${mes}"/> del corriente año <c:out value="${anio}"/> </h1>
		<table class="table">
			<tr>
				<th class="th"><c:out value="Hora" /></th>
				<th class="th"><c:out value="Importe total Hora" /></th>
				<th class="th"><c:out value="Cantidad de tickets" /></th>
			</tr>
			<c:forEach var="ventaMensualFranja" items="${ventaMensualFranjaList}">
				<tr>
					<td class="td"><c:out value="${ventaMensualFranja.hora}" /></td>
					<td class="td"><c:out value="${ventaMensualFranja.importeTotal}" /></td>
					<td class="td"><c:out value="${ventaMensualFranja.cantTicket}" /></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>