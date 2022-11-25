<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.Optional"%>
 
<%@include file="/WEB-INF/jsp/Head.jspf" %>
 
<body>

<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		<%@include file="/WEB-INF/jsp/Header.jspf" %>
		<%@include file="/WEB-INF/jsp/Nav.jspf" %>
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Detalle Usuario</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
					
						<form action="/tienda_informatica/usuario" >
							<input type="submit" value="Volver" />
						</form>
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
				<input value="<%= optUsu.get().getId() %>" readonly="readonly"/>
			</div>
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>Nombre</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input value="<%= optUsu.get().getNombre() %>" readonly="readonly"/>
			</div> 
		</div>
		
		<% 	} else { %>
			
				request.sendRedirect("usuario/");
		
		<% 	} %>
		
</div>
<%@include file="/WEB-INF/jsp/Footer.jspf" %>
</body>
</html>