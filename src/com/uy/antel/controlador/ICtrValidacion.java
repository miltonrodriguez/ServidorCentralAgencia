package com.uy.antel.controlador;

import java.util.Date;

import org.apache.commons.lang3.tuple.Pair;

public interface ICtrValidacion {
	public Pair<Integer, String> validarEntradaAltaTicket(String matricula, String fechaIniE, int cantMinutos,
			Date fechaVenta);

	public Pair<Integer, String> validarEntradaLogin(String usuario, String clave, int nroTerminal);

	public Pair<Integer, String> validarEntradaCancelacionTicket(int nroTicket);
}
