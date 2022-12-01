<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Producto"%>
<%@page import="org.iesvegademijas.dto.FabricanteDTO"%>
<%@page import="java.util.Optional"%>
<%@page import="java.util.List"%>


<%@include file="/WEB-INF/jsp/Head.jspf" %>
<body>
		<%@include file="/WEB-INF/jsp/Header.jspf" %>
		<%@include file="/WEB-INF/jsp/Nav.jspf" %>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		
	<form action="/tienda_informatica/productos/editar/" method="post" >
		<input type="hidden" name="__method__" value="put" />
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Editar Producto</h1>
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
		
		<% 	Optional<Producto> optProd = (Optional<Producto>)request.getAttribute("producto");
			if (optProd.isPresent()) {
		%>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>Código</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="codigo" value="<%= optProd.get().getCodigo() %>" readonly="readonly"/>
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>Nombre</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="nombre" value="<%= optProd.get().getNombre() %>"/>
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>Precio</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="precio" value="<%= optProd.get().getPrecio() %>"/>
			</div> 
		</div>
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>Codigo Fabricante</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<select name="codigo_fabricante" />
				<%	if (request.getAttribute("listaFabricantes") != null) {
		            	List<FabricanteDTO> listaFabricante = (List<FabricanteDTO>)request.getAttribute("listaFabricantes");
		            	for (FabricanteDTO fabricante : listaFabricante) {%>
		            		<option value="<%= fabricante.getCodigo()%>"><%= fabricante.getNombre()%></option>
		            <% } }%>
			</div> 
		</div>
		
		<% 	} else { %>
			
				request.sendRedirect("productos/");
		
		<% 	} %>
	</form>
</div>
<%@include file="/WEB-INF/jsp/Footer.jspf" %>
</body>
</html>