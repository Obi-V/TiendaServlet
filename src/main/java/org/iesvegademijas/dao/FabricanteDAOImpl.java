package org.iesvegademijas.dao;

import static java.util.stream.Collectors.toList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.iesvegademijas.dto.FabricanteDTO;
import org.iesvegademijas.model.Fabricante;

public class FabricanteDAOImpl extends AbstractDAOImpl implements FabricanteDAO{

	/**
	 * Inserta en base de datos el nuevo fabricante, actualizando el id en el bean fabricante.
	 */
	
	@Override	
	public synchronized void create(Fabricante fabricante) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
        	conn = connectDB();


        	//1 alternativas comentadas:       
        	//ps = conn.prepareStatement("INSERT INTO fabricante (nombre) VALUES (?)", new String[] {"codigo"});        	
        	//Ver también, AbstractDAOImpl.executeInsert ...
        	//Columna fabricante.codigo es clave primaria auto_increment, por ese motivo se omite de la sentencia SQL INSERT siguiente. 
        	ps = conn.prepareStatement("INSERT INTO fabricante (nombre) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            
            int idx = 1;
            ps.setString(idx++, fabricante.getNombre());
                   
            int rows = ps.executeUpdate();
            if (rows == 0) {
            	System.out.println("INSERT de fabricante con 0 filas insertadas.");
            }
            
            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next()) {
            	fabricante.setCodigo(rsGenKeys.getInt(1));
            }     
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        
	}

	/**
	 * Devuelve lista con todos los fabricantes.
	 */
	@Override
	public List<Fabricante> getAll() {
		
		Connection conn = null;
		Statement s = null;
        ResultSet rs = null;
        
        List<Fabricante> listFab = new ArrayList<>(); 
        
        try {
        	conn = connectDB();

        	// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
        	s = conn.createStatement();
            		
        	rs = s.executeQuery("SELECT * FROM fabricante");          
            while (rs.next()) {
            	Fabricante fab = new Fabricante();
            	
            	fab.setCodigo(rs.getInt("codigo"));
            	fab.setNombre(rs.getString("nombre"));
            	listFab.add(fab);
            }
          
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, s, rs);
        }
        return listFab;
        
	}

	/**
	 * Devuelve Optional de fabricante con el ID dado.
	 */
	@Override
	public Optional<Fabricante> find(int id) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("SELECT * FROM fabricante WHERE codigo = ?");
        	
        	ps.setInt(1, id);
        	
        	rs = ps.executeQuery();
        	
        	if (rs.next()) {
        		Fabricante fab = new Fabricante();
        		
        		fab.setCodigo(rs.getInt("codigo"));
        		fab.setNombre(rs.getString("nombre"));
        		
        		return Optional.of(fab);
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
	 * Actualiza fabricante con campos del bean fabricante según ID del mismo.
	 */
	@Override
	public void update(Fabricante fabricante) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("UPDATE fabricante SET nombre = ?  WHERE codigo = ?");
        	int idx = 1;
        	ps.setString(idx++, fabricante.getNombre());
        	ps.setInt(idx, fabricante.getCodigo());
        	
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Update de fabricante con 0 registros actualizados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
    
	}

	/**
	 * Borra fabricante con ID proporcionado.
	 */
	@Override
	public void delete(int id) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("DELETE FROM fabricante WHERE codigo = ?");
        	ps.setInt(1, id);
        	
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Delete de fabricante con 0 registros eliminados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
		
	}

	@Override
	public Optional<Integer> getCountProductos(int id) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("SELECT COUNT(P.codigo) as numProd from fabricante F left outer join producto P on F.codigo = P.codigo_fabricante where F.codigo = ? group by F.codigo ");
        	int idx = 1;
        	ps.setInt(idx, id);
        	
        	rs = ps.executeQuery();
        	
        	if (rs.next()) {
        		int numProd = rs.getInt(idx);
        		return Optional.of(numProd);
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
	
	@Override
	public List<FabricanteDTO> getAllDTOPlusCountProductos() {
		
		List<FabricanteDTO> listFab = new ArrayList<>(); 
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("SELECT F.*, COUNT(P.codigo) as numProd from fabricante F left outer join producto P on F.codigo = P.codigo_fabricante group by F.codigo ");
        	int idx = 1;        	

        	rs = ps.executeQuery();
        	
        	while (rs.next()) {
        		FabricanteDTO fab = new FabricanteDTO();
        		fab.setNombre(rs.getString("nombre"));
        		fab.setCodigo(rs.getInt("codigo"));
        		fab.setNumeroProductos(Optional.of(rs.getInt("numProd")));
        		listFab.add(fab);
        	}
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
		
	return listFab;
	}
	
	public List<FabricanteDTO>  getAllDTOPlusCountProductos(String ordenarPor, String orden) {
		
		List<FabricanteDTO> listOrd = new ArrayList<>();
		
		Connection conn = null;
		Statement s = null;
        ResultSet rs = null;
        
        List <String> lista_ordenarPor = new ArrayList<>();
    	lista_ordenarPor.add("nombre");
    	lista_ordenarPor.add("codigo");
    	List <String> lista_orden = new ArrayList<>();
    	lista_orden.add("asc");
    	lista_orden.add("desc");
        
        try {
        	conn = connectDB();
        	
        	s = conn.createStatement();
        	
        	String consulta = "SELECT F.*, COUNT(P.codigo) as numProd from fabricante F left outer join producto P on F.codigo = P.codigo_fabricante group by F.codigo";
        	
        	if(lista_ordenarPor.contains(ordenarPor) && lista_orden.contains(orden)){
        		
        		rs = s.executeQuery(consulta + " order by "+ ordenarPor + " " + orden+ ";" );
        	
        	} else {
        		rs = s.executeQuery(consulta + ";");
        	}
        	
        	while (rs.next()){
        		FabricanteDTO fab = new FabricanteDTO();
        		fab.setNombre(rs.getString("nombre"));
        		fab.setCodigo(rs.getInt("codigo"));
        		fab.setNumeroProductos(Optional.of(rs.getInt("numProd")));
        		listOrd.add(fab);
        	}
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, s, rs);
        }
		
	return listOrd;
   					
	}
}

