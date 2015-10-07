package com.uy.antel.controlador;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;

import com.uy.antel.xml.DataTicket.ObjectFactory;
import com.uy.antel.util.util;
import com.uy.antel.xml.AltaTicket.XmlAltaTicket;
import com.uy.antel.xml.DataTicket.XmlDataTicket;

import antel.com.uy.webservices.DataTicket;

public class ctrlCentral {

	private static ctrlCentral instance = null;
	

	public static ctrlCentral getInstance() {
		if (instance == null) {
			instance = new ctrlCentral();
			//TODO:seguir aca
		}
		return instance;
	}

	public XmlDataTicket altaTicket(XmlAltaTicket xmlAltaT) {
		// TODO: sacr el id de la aencia de una property
		ObjectFactory factory = new ObjectFactory();
		XmlDataTicket dataTicketResp = factory.createXmlDataTicket();
		try {
			ctrlWS ws = ctrlWS.getInstance();
			Date fechaVenta = new Date();

			
			Date fechaIniE = util.stringToDate(xmlAltaT.getFechaHoraInicioEst());
			int cantMinutos = xmlAltaT.getCantidadMinutos().intValue();
			String matricula = xmlAltaT.getMatricula();
			DataTicket respuesta = ws.altaTicket(matricula, fechaIniE, cantMinutos, fechaVenta, util.getIdAgencia());
			dataTicketResp.setError(BigInteger.valueOf(respuesta.getError()));
			dataTicketResp.setMensaje(respuesta.getMensaje());
			dataTicketResp.setImporteTotal(BigInteger.valueOf(respuesta.getImporteTotal()));
			dataTicketResp.setNroTicket(BigInteger.valueOf(respuesta.getNroTicket()));
			if (respuesta.getError() == 0) {
				// Doy de alta el ticket en la BD
				ctrlDAO dao = ctrlDAO.getInstance();

				dao.altaAuto(matricula);
				dao.altaTicket(respuesta.getNroTicket(), fechaVenta, fechaIniE, cantMinutos,
						respuesta.getImporteTotal(), matricula, xmlAltaT.getNroTerminal().intValue());

			}
			//todo: poner el error

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Parse exception "+this.getClass());

			dataTicketResp.setError(BigInteger.valueOf(100));
			dataTicketResp.setMensaje("La fecha de inicio de estacionamiento no es valida");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception "+this.getClass());
			dataTicketResp.setError(BigInteger.valueOf(101));
			dataTicketResp.setMensaje("Error al dar de alta el ticket");
		}
		return dataTicketResp;
	}
}
