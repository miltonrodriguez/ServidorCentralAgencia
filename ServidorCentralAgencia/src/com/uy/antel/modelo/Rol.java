package com.uy.antel.modelo;

import java.util.ArrayList;

public class Rol {
	private String rol;
	private String descripcion;
	private ArrayList<Terminal> terminales;
	public ArrayList<Terminal> getTerminales() {
		return terminales;
	}
	public void setTerminales(ArrayList<Terminal> terminales) {
		this.terminales = terminales;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
		
}
