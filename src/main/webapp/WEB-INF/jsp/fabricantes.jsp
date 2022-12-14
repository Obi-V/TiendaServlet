<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.dto.FabricanteDTO"%>
<%@page import="java.util.List"%>

<%@include file="/WEB-INF/jsp/Head.jspf" %>

<body>
		<%@include file="/WEB-INF/jsp/Header.jspf" %>
		<%@include file="/WEB-INF/jsp/Nav.jspf" %>
	<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Fabricantes</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
					
						<form action="/tienda_informatica/fabricantes/crear">
							<input type="submit" value="Crear">
						</form>
					</div>
				
			</div>
		</div>
		<div>
			<form method="GET" action="/tienda_informatica/fabricantes/">
				<select name="ordenar_por"/>
					<option value="nombre"<% if(request.getParameter("ordenar_por")!= null && request.getParameter("ordenar_por").equals("nombre")){%>selected<%} %> > Nombre</option>
					<option value="codigo"<% if(request.getParameter("ordenar_por")!= null && request.getParameter("ordenar_por").equals("codigo")){%>selected<%} %> >Código</option>
				</select>
				<select name="orden" />
					<option value="asc" <% if(request.getParameter("orden")!= null && request.getParameter("orden").equals("asc")){%>selected<%} %>> Ascendente</option>
					<option value="desc"<% if(request.getParameter("orden")!= null && request.getParameter("orden").equals("desc")){%>selected<%} %>>Descendente</option>
				</select>
				<button value="ordenar">Ordenar</button>
				</form>
			</div>
		<div class="clearfix">
			<hr/>
		</div>
		<div class="clearfix">
			<div style="float: left;width: 20%">Código</div>
			<div style="float: left;width: 20%">Nombre</div>
			<div style="float: left;width: 20%">Nº Productos</div>
			<div style="float: none;width: auto;overflow: hidden;">Acción</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
	<% 
        if (request.getAttribute("listaFabricantes") != null) {
            List<FabricanteDTO> listaFabricante = (List<FabricanteDTO>)request.getAttribute("listaFabricantes");
            for (FabricanteDTO fabricante : listaFabricante){
    %>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 20%"><%= fabricante.getCodigo()%></div>
			<div style="float: left;width: 20%"><%= fabricante.getNombre()%></div>
			<div style="float: left;width: 20%"><%= fabricante.getNumeroProductos().get() %></div>
			<div style="float: none;width: auto;overflow: hidden;">
				<form action="/tienda_informatica/fabricantes/<%= fabricante.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Ver Detalle" />
				</form>
				<form action="/tienda_informatica/fabricantes/editar/<%= fabricante.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Editar" />
				</form>
				<form action="/tienda_informatica/fabricantes/borrar/" method="post" style="display: inline;">
					<input type="hidden" name="__method__" value="delete"/>
					<input type="hidden" name="codigo" value="<%= fabricante.getCodigo()%>"/>
    				<input type="submit" value="Eliminar" />
				</form>
			</div>
		</div>

	<% 
            }
        } else { 
    %>
		No hay registros de fabricante
	<% } %>
	</div>
	<%@include file="/WEB-INF/jsp/Footer.jspf" %>
</body>
</html>