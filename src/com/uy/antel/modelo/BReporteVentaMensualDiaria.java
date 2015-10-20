package com.uy.antel.modelo;

public class BReporteVentaMensualDiaria {
	private int importeTotal;
	private int dia;
	private int cantTicket;
	
	
	public BReporteVentaMensualDiaria(){
		
	}
	
	public BReporteVentaMensualDiaria(int dia, int importeTotal, int cantTicket){
		this.importeTotal = importeTotal;
		this.dia = dia;
		this.cantTicket = cantTicket;
	}
	
	public int getImporteTotal() {
		return importeTotal;
	}


	public void setImporteTotal(int importeTotal) {
		this.importeTotal = importeTotal;
	}





	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getCantTicket() {
		return cantTicket;
	}


	public void setCantTicket(int cantTicket) {
		this.cantTicket = cantTicket;
	}


}
