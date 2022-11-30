package org.iesvegademijas.dto;

import java.util.Optional;

import org.iesvegademijas.model.Fabricante;

public class FabricanteDTO {

	private int codigo;
	private String nombre;
	private Optional<Integer> numeroProductos;

	public FabricanteDTO(Fabricante f) {
	this.codigo = f.getCodigo();
	this.nombre = f.getNombre();
	}

	public FabricanteDTO() {
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return "Fabricante [codigo=" + codigo + ", nombre=" + nombre + "]";
	}

	public Optional<Integer> getNumeroProductos() {
		return numeroProductos;
	}

	public void setNumeroProductos(Optional<Integer> optional) {
		this.numeroProductos = optional;
	}
}
