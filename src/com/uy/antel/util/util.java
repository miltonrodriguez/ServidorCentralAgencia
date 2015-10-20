package com.uy.antel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class util {

	private static String formatoEsperadoFecha = System.getProperty("com.uy.antel.formatoEsperadoFecha");
	
	public static int getPuertoTerminal() {
		return Integer.parseInt(System.getProperty("com.uy.antel.puertoTerminal"));
	}

	public static String getIdAgencia() {
		return System.getProperty("com.uy.antel.idAgencia");
	}
	
	public static int getCantIntentosLogin() {
		return Integer.parseInt(System.getProperty("com.uy.antel.cantIntentosLogin"));
	}

	public static Date stringToDate(String fechaStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatoEsperadoFecha);
		Date resultDate = new Date();		
		format.setLenient(false);
	    try {
	    	resultDate = format.parse(fechaStr.trim());
	    } catch (ParseException pe) {
	    	 pe.printStackTrace();
	    }
	    return resultDate;
	}

	public static String dateToString(Date fecha) {
		SimpleDateFormat format = new SimpleDateFormat(formatoEsperadoFecha);
		return format.format(fecha);
	}
	
	public static boolean esValidaFecha(String fecha) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat(formatoEsperadoFecha);
	    dateFormat.setLenient(false);
	    try {
	      dateFormat.parse(fecha.trim());
	    } catch (ParseException pe) {
	      return false;
	    }
	    return true;
   }
	/**
	 * 
	 * @param mes
	 * @param anio
	 * @return
	 */
	public static int getDiasMaxMes(int mes, int anio)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,anio);
		calendar.set(Calendar.MONTH,mes-1);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println("Fecha maximo dias: " +calendar);
		return days;
	}
}
