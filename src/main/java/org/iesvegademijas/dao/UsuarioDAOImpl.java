package org.iesvegademijas.dao;

import static java.util.stream.Collectors.toList;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.iesvegademijas.model.Producto;
import org.iesvegademijas.model.Usuario;

public class UsuarioDAOImpl extends AbstractDAOImpl implements UsuarioDAO{

	/**
	 * Inserta en base de datos el nuevo usuario, actualizando el id en el bean usuario.
	 */
	
	@Override	
	public synchronized void create(Usuario usuario) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
        	conn = connectDB();


        	//1 alternativas comentadas:       
        	//ps = conn.prepareStatement("INSERT INTO usuario (nombre) VALUES (?)", new String[] {"id"});        	
        	//Ver también, AbstractDAOImpl.executeInsert ...
        	//Columna usuario.id es clave primaria auto_increment, por ese motivo se omite de la sentencia SQL INSERT siguiente. 
        	ps = conn.prepareStatement("INSERT INTO usuario(nombre, contrasenia, rol) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            int idx = 1;
            ps.setString(idx++, usuario.getNombre());
            try {
				ps.setString(idx++, hashPassword(usuario.getContraseña()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ps.setString(idx, usuario.getRol());
            int rows = ps.executeUpdate();
            if (rows == 0) 
            	System.out.println("INSERT de usuario con 0 filas insertadas.");
            
            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next()) 
            	usuario.setId(rsGenKeys.getInt(1));
                      
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
	}

	/**
	 * Devuelve lista con todos los usuarios.
	 */
	@Override
	public List<Usuario> getAll() {
		
		Connection conn = null;
		Statement s = null;
        ResultSet rs = null;
        
        List<Usuario> listUsu = new ArrayList<>(); 
        
        try {
        	conn = connectDB();

        	// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
        	s = conn.createStatement();
            		
        	rs = s.executeQuery("SELECT * FROM usuario");          
            while (rs.next()) {
            	Usuario usu = new Usuario();
            	int idx = 1;
            	usu.setId(rs.getInt("id"));
            	usu.setNombre(rs.getString("nombre"));
            	usu.setContraseña(rs.getString("contrasenia"));
            	usu.setRol(rs.getString("rol"));
            	listUsu.add(usu);
            }
          
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, s, rs);
        }
        return listUsu;
        
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

	
	/**
	 * Devuelve Optional de usuario con el ID dado.
	 */
	@Override
	public Optional<Usuario> find(int id) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("SELECT * FROM usuario WHERE id = ?");
        	
        	int idx =  1;
        	ps.setInt(idx, id);
        	
        	rs = ps.executeQuery();
        	
        	if (rs.next()) {
        		Usuario usu = new Usuario();
        		idx = 1;
        		usu.setId(rs.getInt("id"));
        		usu.setNombre(rs.getString("nombre"));
        		usu.setRol(rs.getString("rol"));
        		usu.setContraseña(rs.getString("contrasenia"));
        		
        		return Optional.of(usu);
        	}
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        
        return Optional.empty();
        
	}
	/**
	 * Actualiza usuario con campos del bean usuario según ID del mismo.
	 */
	@Override
	public void update(Usuario usuario) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("UPDATE usuario SET nombre = ?, contrasenia = ?, rol = ? WHERE id = ?");
        	int idx = 1;
        	ps.setString(idx++, usuario.getNombre());       
			ps.setString(idx++, usuario.getContraseña());
        	ps.setString(idx++, usuario.getRol());
        	ps.setInt(idx, usuario.getId());
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Update de usuario con 0 registros actualizados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
    
	}

	/**
	 * Borra usuario con ID proporcionado.
	 */
	@Override
	public void delete(int id) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("DELETE FROM usuario WHERE id = ?");	
        	ps.setInt(1, id);
        	
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Delete de usuario con 0 registros eliminados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
	
	}
	
	public Usuario login(String usuario) {
		
		Usuario usu = new Usuario();
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("SELECT * FROM usuario WHERE nombre like ?;");
        	String filtro = "%"+usuario+"%";
        	ps.setString(1, filtro);
        	rs = ps.executeQuery();
        	
        	if (rs.next()) {
        		
        		usu.setId(rs.getInt("id"));
        		usu.setNombre(rs.getString("nombre"));
        		usu.setContraseña(rs.getString("contrasenia"));
        		usu.setRol(rs.getString("rol"));       		
        	}
        
        }catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        return usu;
	}
}
