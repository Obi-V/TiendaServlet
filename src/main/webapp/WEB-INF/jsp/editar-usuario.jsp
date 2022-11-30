<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.Optional"%>
<%@include file="/WEB-INF/jsp/Head.jspf" %>

<body>
	<%@include file="/WEB-INF/jsp/Header.jspf" %>
	<%@include file="/WEB-INF/jsp/Nav.jspf" %>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >

	
	<form action="/tienda_informatica/usuario/editar/" method="post" >
		<input type="hidden" name="__method__" value="put" />
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Editar Usuario</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
							<input type="submit" value="Guardar" />						
				</div>
				
			</div>
		</div>
		
		<div class="clearfix">
			<hr/>
		</div>
		
		<% 	Optional<Usuario> optUsu = (Optional<Usuario>)request.getAttribute("usuario");
			if (optUsu.isPresent()) {
		%>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>Id</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="id" value="<%= optUsu.get().getId() %>" readonly="readonly"/>
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>Nombre</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="nombre" value="<%= optUsu.get().getNombre() %>"/>
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>Contraseña</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="contrasenia" value="<%= optUsu.get().getContraseña() %>"/>
			</div>
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>Rol</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<select name="rol">
					<option value="administrador"> Administrador</option>
					<option value="cliente"> Cliente</option>
					<option value="vendedor"> Vendedor</option>
				</select>
				
			</div>
		</div>
		
		<% 	} else { %>
			
				request.sendRedirect("usuario/");
		
		<% 	} %>
	</form>
</div>
<%@include file="/WEB-INF/jsp/Footer.jspf" %>

</body>
</html>