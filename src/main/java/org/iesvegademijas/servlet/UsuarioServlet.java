package org.iesvegademijas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.iesvegademijas.dao.UsuarioDAO;
import org.iesvegademijas.dao.UsuarioDAOImpl;
import org.iesvegademijas.model.Usuario;

import static java.util.stream.Collectors.*;

/**
 * Servlet implementation class UsuarioServlet
 */

@WebServlet("/usuario/*")
public class UsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * HTTP Method: GET
	 * Paths:
	 * 		/usuario/
	 * 		/usuario/{id}
	 * 		/usuario/edit/{id}
	 * 		/usuariocreate
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		
		String pathInfo = request.getPathInfo(); //

		if (pathInfo == null || "/".equals(pathInfo)) {
			UsuarioDAO usuDAO = new UsuarioDAOImpl();
			
			//GET 
			//	/usuario/
			//	/usuario
		
			var lista = usuDAO.getAll();
		
			
			
			request.setAttribute("listaUsuario", lista);		
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuario.jsp");
			
			}else {
				// GET
				// 		/usuario/{id}
				// 		/usuario/{id}/
				// 		/usuario/edit/{id}
				// 		/usuario/edit/{id}/
				// 		/usuario/create
				// 		/usuario/create/
				//		/usuario/login/
				
				pathInfo = pathInfo.replaceAll("/$", "");
				String[] pathParts = pathInfo.split("/");
				
				if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
					UsuarioDAOImpl usuDao = new UsuarioDAOImpl();
					// GET
					// /usuario/create
					request.setAttribute("listaUsuario", usuDao.getAll());
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-usuario.jsp");
	        												
				} else if (pathParts.length == 2 && "login".equals(pathParts[1])){
					// GET
					// /usuario/login
					try {
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
						        								
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuario.jsp");
					}
					
				} else if (pathParts.length == 2){
					UsuarioDAO usuDAO = new UsuarioDAOImpl();
					// GET
					// /usuario/{id}
					try {
						request.setAttribute("usuario",usuDAO.find(Integer.parseInt(pathParts[1])));
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-usuario.jsp");
						        								
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuario.jsp");
					}
					
				} else if (pathParts.length == 3 && "editar".equals(pathParts[1]) ) {
					UsuarioDAO usuDAO = new UsuarioDAOImpl();
					
					// GET
					// /usuario/edit/{id}
					try {
						request.setAttribute("usuario",usuDAO.find(Integer.parseInt(pathParts[2])));
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-usuario.jsp");
						        								
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuario.jsp");
					}
				} else {
					System.out.println("Opción POST no soportada.");
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuario.jsp");
				}
			}
			dispatcher.forward(request, response);		 
}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		String __method__ = request.getParameter("__method__");
		
		if (__method__ == null) {
			// Crear uno nuevo
			UsuarioDAO usuDAO = new UsuarioDAOImpl();
			
			String nombre = request.getParameter("nombre");
			String contrasenia = request.getParameter("contrasenia");
			String rol = request.getParameter("rol");
			Usuario nuevoUsu = new Usuario();
			nuevoUsu.setNombre(nombre);
			nuevoUsu.setContraseña(contrasenia);
			nuevoUsu.setRol(rol);
			usuDAO.create(nuevoUsu);	
			
		} else if (__method__ != null && "put".equalsIgnoreCase(__method__)){			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización PUT.
			doPut(request, response);
			
		
		} else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización DELETE.
			doDelete(request, response);
			
			
		} else if (__method__ != null && "login".equalsIgnoreCase(__method__)) {
			doLogin(request,response);
		}
		
		else if (__method__ != null && "logout".equalsIgnoreCase(__method__)) {
			doLogout(request, response);
		} else {
			
			System.out.println("Opción POST no soportada.");
			
		}
		
		if((__method__ != null && "login".equalsIgnoreCase(__method__)) || (__method__ != null && "logout".equalsIgnoreCase(__method__))){
			response.sendRedirect("/tienda_informatica/");
		}else {
			response.sendRedirect("/tienda_informatica/usuario");
		//response.sendRedirect("/tienda_informatica/usuario");
		}
		
	}
	
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		UsuarioDAO usuDAO = new UsuarioDAOImpl();
		String id = request.getParameter("id");
		String nombre = request.getParameter("nombre");
		String rol = request.getParameter("rol");
		String contrasenia = request.getParameter("contrasenia");
		
		Usuario usu = new Usuario();
		
		try {
			
			int idUsu = Integer.parseInt(id);
			usu.setId(idUsu);
			usu.setNombre(nombre);
			usu.setRol(rol);
			usu.setContraseña(contrasenia);
			usuDAO.update(usu);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		UsuarioDAO usuDAO = new UsuarioDAOImpl();
		String id = request.getParameter("id");
		
		try {
			
			int idUsu = Integer.parseInt(id);
		
		usuDAO.delete(idUsu);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
	protected void doLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		UsuarioDAO usuDAO = new UsuarioDAOImpl();
		
		String nombre = request.getParameter("nombre");
		String contrasenia = request.getParameter("contrasenia");
		
		Usuario usuario = usuDAO.login(nombre);
		
		if(usuario.getId() !=0) {
			try {
				if(usuario.getContraseña().equals(hashPassword(contrasenia))) {
					 HttpSession session = request.getSession(true);
					 session.setAttribute("usuario-logado", usuario);
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void doLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		HttpSession session=request.getSession();
		session.invalidate();
		
	}
	
	public static String hashPassword( String password ) throws NoSuchAlgorithmException {
		MessageDigest digest;
		
		digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(
				password.getBytes(StandardCharsets.UTF_8));
		
		return bytesToHex(encodedhash);					
		
	}
	
	private static String bytesToHex(byte[] byteHash) {
		
	    StringBuilder hexString = new StringBuilder(2 * byteHash.length);	  	
	    for (int i = 0; i < byteHash.length; i++) {
	        String hex = Integer.toHexString(0xff & byteHash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    
	    return hexString.toString();
	    
	}
}
