<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.Optional"%>

<%@include file="/WEB-INF/jsp/Head.jspf" %>

<body>

	<%@include file="/WEB-INF/jsp/Header.jspf" %>
	<%@include file="/WEB-INF/jsp/Nav.jspf" %>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >

	
	<form action="/tienda_informatica/usuario/login/" method="post">
	<input type="hidden" name="__method__" value="login">
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Loguear Usuario</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				<div style="width; 29.5%;display: inline-block;"></div>
		<div style="margin-top: 20px"; display="inline-block"; class="clearfix">
			<input type="submit" value="iniciar sesion">
			
		</div>
			</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				Nombre
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="nombre" />
			</div> 
		</div>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				Contraseña
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="contrasenia" />
			</div> 
		</div>
	</form>
</div>
<%@include file="/WEB-INF/jsp/Footer.jspf" %>
</body>
</html>