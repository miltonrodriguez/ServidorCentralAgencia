package com.uy.antel.controlador;

import java.util.Date;

import org.apache.commons.lang3.tuple.Pair;


public interface ICtrValidacion {
	public Pair<Integer,String> validarEntrada(String matricula,String fechaIniE, int cantMinutos,Date fechaVenta);
}
