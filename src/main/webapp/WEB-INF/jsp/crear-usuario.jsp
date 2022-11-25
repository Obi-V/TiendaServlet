<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.Optional"%>

<%@include file="/WEB-INF/jsp/Head.jspf" %>

<body>

	<%@include file="/WEB-INF/jsp/Header.jspf" %>
	<%@include file="/WEB-INF/jsp/Nav.jspf" %>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >

	
	<form action="/tienda_informatica/usuario/crear/" method="post">
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Crear Usuario</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">								
					<input type="submit" value="Crear"/>					
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
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				Rol
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<form method="GET" action="/tienda_informatica/usuario/">
				<select name="rol"/>
					<option value="administrador"> Administrador</option>
					<option value="cliente"> Cliente</option>
					<option value="vendedor"> Vendedor</option>
				</select>
			</div> 
		</div>
	</form>
</div>
<%@include file="/WEB-INF/jsp/Footer.jspf" %>
</body>
</html>