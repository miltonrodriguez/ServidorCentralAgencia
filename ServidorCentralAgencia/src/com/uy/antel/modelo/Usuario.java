package com.uy.antel.modelo;

import java.util.ArrayList;

public class Usuario {
	private String nombre;
	private String usuario;
	private String password;
	private String correo;
	private ArrayList<Rol> roles;
	public ArrayList<Terminal> getLogueado() {
		return logueado;
	}
	public void setLogueado(ArrayList<Terminal> logueado) {
		this.logueado = logueado;
	}
	public ArrayList<Rol> getRoles() {
		return roles;
	}
	public void setRoles(ArrayList<Rol> roles) {
		this.roles = roles;
	}
	private ArrayList<Terminal> logueado;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	}
