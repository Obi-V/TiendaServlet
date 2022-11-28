<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.List"%>

<%@include file="/WEB-INF/jsp/Head.jspf" %>


<body>
		<%@include file="/WEB-INF/jsp/Header.jspf" %>
		<%@include file="/WEB-INF/jsp/Nav.jspf" %>

	<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Usuario</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
					
						<form action="/tienda_informatica/usuario/crear">
							<input type="submit" value="Crear">
						</form>
					</div>
				
			</div>
		</div>
		<div class="clearfix">
			<div style="float: left;width: 20%">Id</div>
			<div style="float: left;width: 20%">Nombre</div>
			<div style="float: left;width: 20%">Rol</div>
			<div style="float: left;width: 20%">Contraseña</div>
			<div style="float: none;width: auto;overflow: hidden;">Acción</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
	<% 
        if (request.getAttribute("listaUsuario") != null) {
            List<Usuario> listaUsuario = (List<Usuario>)request.getAttribute("listaUsuario");
            for (Usuario usuario : listaUsuario){
    %>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 20%"><%= usuario.getId()%></div>
			<div style="float: left;width: 20%"><%= usuario.getNombre()%></div>
			<div style="float: left;width: 20%"><%= usuario.getRol()%></div>
			<div style="float: left;width: 20%"><%= usuario.getContraseña()%></div>
			<div style="float: none;width: auto;overflow: hidden;">
				<form action="/tienda_informatica/usuario/<%= usuario.getId()%>" style="display: inline;">
    				<input type="submit" value="Ver Detalle" />
				</form>
				<form action="/tienda_informatica/usuario/editar/<%= usuario.getId()%>" style="display: inline;">
    				<input type="submit" value="Editar" />
				</form>
				<form action="/tienda_informatica/usuario/borrar/" method="post" style="display: inline;">
					<input type="hidden" name="__method__" value="delete"/>
					<input type="hidden" name="id" value="<%= usuario.getId()%>"/>
    				<input type="submit" value="Eliminar" />
				</form>
			</div>
		</div>

	<% 
            }
        } else { 
    %>
		No hay registros de usuario
	<% } %>
	</div>
	<%@include file="/WEB-INF/jsp/Footer.jspf" %>
</body>
</html>