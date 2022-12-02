package org.iesvegademijas.dao;

import java.util.List;
import java.util.Optional;

import org.iesvegademijas.dto.FabricanteDTO;
import org.iesvegademijas.model.Fabricante;

public interface FabricanteDAO {
		
	public void create(Fabricante fabricante);
	
	public List<Fabricante> getAll();
	
	public Optional<Fabricante>  find(int id);
	
	public void update(Fabricante fabricante);
	
	public void delete(int id);
	
    //Ampliación CRUD:
    public Optional<Integer> getCountProductos(int id);

	public List<FabricanteDTO> getAllDTOPlusCountProductos();
	
	public List<FabricanteDTO> getAllDTOPlusCountProductos(String string1, String string2);
   
}