package com.uy.antel.controlador;

import java.util.Date;
import java.util.regex.*;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.uy.antel.util.util;


public class ctrValidacion implements ICtrValidacion {

	private static ctrValidacion instance;

	private ctrValidacion() {

	}

	public static ctrValidacion getInstance() {
		if (instance == null) {
			instance = new ctrValidacion();
		}
		return instance;
	}

	private boolean validarMatricula(String matricula) {
		boolean ok = (matricula.length() == 7);
		if (ok) {
			Pattern pIni3Letras = Pattern.compile("[a-zA-Z]{3}[0-9]{4}");
			Matcher m = pIni3Letras.matcher(matricula);
			ok = m.find();
		}
		return ok;
	}

	private boolean validarFechainiEFuturo(Date fechaIniE, Date fechaVenta) {
		return fechaIniE.after(fechaVenta);
	}

	@SuppressWarnings("deprecation")
	private boolean validarFechainiEHs(Date fechaIniE) {
		return (10 > fechaIniE.getHours() || fechaIniE.getHours() >= 18);
	}

	@SuppressWarnings("deprecation")
	private boolean validarFechainiEMasMinHs(Date fechaIniE, int cantMIn) {
		return DateUtils.addHours(fechaIniE, cantMIn).getHours() > 18;
	}

	private boolean validarCantidadMinutos(int cantMinutos) {
		return ((cantMinutos % 30 == 0) && (cantMinutos > 0));
	}

	private boolean validarFormatoFecha(String fecha) {
		// La fecha debe tener el formato "yyyy-MM-dd_HH:mm"
		return util.esValidaFecha(fecha);
	}

	@Override
	public Pair<Integer,String> validarEntrada(String matricula, String fechaIniE, int cantMinutos, Date fechaVenta) {
		Pair<Integer,String> error;
		if (!validarFormatoFecha(fechaIniE))
			error = new ImmutablePair<Integer,String>(105,"La fecha ingresada no es correcta.");
		else if (!validarFechainiEFuturo(util.stringToDate(fechaIniE), fechaVenta))
			error = new ImmutablePair<Integer, String> (101,"La fecha de la solicitud de estacionamiento debe ser en el futuro.");
		else if (!validarFechainiEHs(util.stringToDate(fechaIniE)))
			error = new ImmutablePair<Integer, String> (102,"El horario de estacionamiento tarifado de 10 � 18 hs.");
		else if (!validarFechainiEMasMinHs(util.stringToDate(fechaIniE), cantMinutos))
			error = new ImmutablePair<Integer, String> (103,"La cantidad de minutos seleccionada excede la hora de finalizaci�n del horario de estacionamiento tarifado (18hs).");
		else if (!validarMatricula(matricula))
			error = new ImmutablePair<Integer, String> (104,"La matricula no es valida.");
		else if (!validarCantidadMinutos(cantMinutos))
			error = new ImmutablePair<Integer, String> (106,"La cantidad de minutos seleccionada debe ser multiplo de 30.");
		else
			error = new ImmutablePair<Integer, String>(0,"");
		return error;
	}

}