package com.uy.antel.modelo;

public class BReporteVentaMensualFranja {
	private int importeTotal;
	private int hora;
	private int cantTicket;
	
	
	public BReporteVentaMensualFranja(){
		
	}
	
	public BReporteVentaMensualFranja(int hora, int importeTotal, int cantTicket){
		this.importeTotal = importeTotal;
		this.hora = hora;
		this.cantTicket = cantTicket;
	}
	
	public int getImporteTotal() {
		return importeTotal;
	}


	public void setImporteTotal(int importeTotal) {
		this.importeTotal = importeTotal;
	}





	public int getHora() {
		return hora;
	}

	public void setHora(int hora) {
		this.hora = hora;
	}

	public int getCantTicket() {
		return cantTicket;
	}


	public void setCantTicket(int cantTicket) {
		this.cantTicket = cantTicket;
	}


}
