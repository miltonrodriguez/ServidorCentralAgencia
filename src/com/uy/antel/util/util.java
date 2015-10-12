package com.uy.antel.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class util {

	private static String formatoEsperadoFecha = "yyyy-MM-dd_HH:mm";
	
	public static int getPuertoTerminal() {
		Properties prop = new Properties();
		try {
			InputStream inputStream = util.class.getClassLoader().getResourceAsStream("/propiedades.properties");
			prop.load(inputStream);
		} catch (IOException e) {
			System.out.println("No se pudo abrir el archivo.properties 1");

		}
		return Integer.parseInt(prop.get("puertoTerminales").toString());
	}

	public static String getIdAgencia() {
		Properties prop = new Properties();
		try {
			InputStream inputStream = util.class.getClassLoader().getResourceAsStream("/propiedades.properties");
			prop.load(inputStream);
		} catch (IOException e) {
			System.out.println("No se pudo abrir el archivo.properties 2");
		}
		return prop.get("idAgencia").toString();
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
}
