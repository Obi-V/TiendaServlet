package org.iesvegademijas.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iesvegademijas.dao.FabricanteDAOImpl;
import org.iesvegademijas.dao.ProductoDAO;
import org.iesvegademijas.dao.ProductoDAOImpl;
import org.iesvegademijas.model.Producto;
import static java.util.stream.Collectors.*;
/**
 * Servlet implementation class ProductosServlet
 */

@WebServlet("/productos/*")
public class ProductosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     * 
     * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * 		 HTTP Method: GET
	 *		 Paths: 
	 * 		/productos/(index) - muestra listado principal con operaciones CRUD
	 * 		/productos/{id} - ver detalle de producto con {id}
	 * 		/productos/edit/{id} - accede al formulario para editar producto con {id}
	 * 		/productos/create - accede al formulario para crear un producto nuevo
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		
		String pathInfo = request.getPathInfo(); //

		if (pathInfo == null || "/".equals(pathInfo)) {
			ProductoDAO prodDAO = new ProductoDAOImpl();
			
			//GET 
			//	/productos/
			//	/productos
			
			var lista = prodDAO.getAll();
			
			String filtrar = request.getParameter("filtrar-por-nombre");
			
			//Filtrar FullText Index
			if (filtrar != null) {
				if(filtrar.hashCode() != 0){
					lista = prodDAO.filtrarFullText(filtrar);
				}	
			}
			
			/* Filtrar por nombre SQL
			 * if (filtrar != null) {
				lista = prodDAO.filtrarProductos(filtrar);
			}*/
			
			/* Stream
			 * if (filtrar != null) {
				lista = lista.stream()
						.filter(p-> p.getNombre().toLowerCase().contains(filtrar.toLowerCase()))
						.collect(toList());
			}*/
			
			request.setAttribute("listaProductos", lista);		
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
			
			}else {
				// GET
				// 		/productos/{id}
				// 		/productos/{id}/
				// 		/productos/edit/{id}
				// 		/productos/edit/{id}/
				// 		/productos/create
				// 		/productos/create/
				
				pathInfo = pathInfo.replaceAll("/$", "");
				String[] pathParts = pathInfo.split("/");
				
				if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
					FabricanteDAOImpl fabDao = new FabricanteDAOImpl();
					// GET
					// /productos/create
					request.setAttribute("listaProductos", fabDao.getAllDTOPlusCountProductos());
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-producto.jsp");
	        												
				
				} else if (pathParts.length == 2){
					ProductoDAO prodDAO = new ProductoDAOImpl();
					// GET
					// /productos/{id}
					try {
						request.setAttribute("producto",prodDAO.find(Integer.parseInt(pathParts[1])));
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-producto.jsp");
						        								
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
					}
					
				} else if (pathParts.length == 3 && "editar".equals(pathParts[1]) ) {
					ProductoDAO prodDAO = new ProductoDAOImpl();
					
					// GET
					// /productos/edit/{id}
					try {
						request.setAttribute("producto",prodDAO.find(Integer.parseInt(pathParts[2])));
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-producto.jsp");
						        								
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
					}					
				} else {
					System.out.println("Opción POST no soportada.");
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
				}	
			}
			dispatcher.forward(request, response);		 
}
		
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		String __method__ = request.getParameter("__method__");
		
		if (__method__ == null) {
			// Crear uno nuevo
			ProductoDAO prodDAO = new ProductoDAOImpl();
			
			String nombre = request.getParameter("nombre");
			String precio = request.getParameter("precio");
			String codigoFab = request.getParameter("codigo_fabricante");
			Producto nuevoProd = new Producto();
			
			Double p = Double.parseDouble(precio);
			nuevoProd.setNombre(nombre);
			nuevoProd.setPrecio(p);
			int codF = Integer.parseInt(codigoFab);
			nuevoProd.setCodigoFabricante(codF);
			prodDAO.create(nuevoProd);
			
		} else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización PUT.
			doPut(request, response);
			
		
		} else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización DELETE.
			doDelete(request, response);
			
			
			
		} else {
			
			System.out.println("Opción POST no soportada.");
			
		}
		
		response.sendRedirect("/tienda_informatica/productos");
		//response.sendRedirect("/tienda_informatica/productos");
		}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ProductoDAO prodDAO = new ProductoDAOImpl();
		String codigo = request.getParameter("codigo");
		String nombre = request.getParameter("nombre");
		String precio = request.getParameter("precio");
		String codigoFab = request.getParameter("codigo_fabricante");
		Producto prod = new Producto();
		
		try {
			
			int id = Integer.parseInt(codigo);
			prod.setCodigo(id);
			prod.setNombre(nombre);
			Double p = Double.parseDouble(precio);
			prod.setPrecio(p);
			int c = Integer.parseInt(codigoFab);
			prod.setCodigoFabricante(c);
			prodDAO.update(prod);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		ProductoDAO prodDAO = new ProductoDAOImpl();
		String codigo = request.getParameter("codigo");
		try {
			
			int id = Integer.parseInt(codigo);
		
		prodDAO.delete(id);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
}