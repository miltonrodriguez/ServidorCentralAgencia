package com.uy.antel.modelo;

import java.util.Date;

public class Ticket {
	private int nroTicket;
	private Date fechaVenta;
	private Date fechaIniE;
	private int cantMinutos;
	
	private Auto auto;
	
	private Terminal agencia;
	
	public Terminal getAgencia() {
		return agencia;
	}
	public void setAgencia(Terminal agencia) {
		this.agencia = agencia;
	}
	public Auto getAuto() {
		return auto;
	}
	public void setAuto(Auto auto) {
		this.auto = auto;
	}
	public int getNroTicket() {
		return nroTicket;
	}
	public void setNroTicket(int nroTicket) {
		this.nroTicket = nroTicket;
	}
	public Date getFechaVenta() {
		return fechaVenta;
	}
	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	public Date getFechaIniE() {
		return fechaIniE;
	}
	public void setFechaIniE(Date fechaIniE) {
		this.fechaIniE = fechaIniE;
	}
	public int getCantMinutos() {
		return cantMinutos;
	}
	public void setCantMinutos(int cantMinutos) {
		this.cantMinutos = cantMinutos;
	}

}
