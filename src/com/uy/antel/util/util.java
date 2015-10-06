package com.uy.antel.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class util {

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

	public static Date stringToDate(String fechaStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
		return format.parse(fechaStr);
	}

	public static String dateToString(Date fecha) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
		return format.format(fecha);
	}
}
