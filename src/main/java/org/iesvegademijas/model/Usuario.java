package org.iesvegademijas.model;

public class Usuario {

	private int id;
	private String nombre;
	private String contraseña;
	private String rol;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getContraseña() {
		return contraseña;
	}
	
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	@Override
	public String toString() {
		return "Usuario [Nombre=" + nombre + ", contraseña=" + contraseña +", rol="+ rol+ "]";
	}
	
	
}
