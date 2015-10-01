package com.uy.antel.controlador;

import java.math.BigInteger;
import java.util.Date;

import com.uy.antel.xml.DataTicket.ObjectFactory;
import com.uy.antel.xml.AltaTicket.XmlAltaTicket;
import com.uy.antel.xml.DataTicket.XmlDataTicket;

import antel.com.uy.webservices.DataTicket;

public class ctrlCentral {

	private static ctrlCentral instance = null;

	public static ctrlCentral getInstance() {
		if (instance == null) {
			instance = new ctrlCentral();
		}
		return instance;
	}

	public XmlDataTicket altaTicket(XmlAltaTicket xmlAltaT) {
		// TODO: sacr el id de la aencia de una property
		ctrlWS ws = ctrlWS.getInstance();
		Date fechaVenta = new Date();
		Date fechaIniE = xmlAltaT.getFechaHoraInicioEst().toGregorianCalendar().getTime();
		int cantMinutos = xmlAltaT.getCantidadMinutos().intValue();
		String matricula = xmlAltaT.getMatricula();
		DataTicket respuesta = ws.altaTicket(matricula, fechaIniE, cantMinutos, fechaVenta, "abcdagencia");
		ObjectFactory factory = new ObjectFactory();
		XmlDataTicket dataTicketResp = factory.createXmlDataTicket();
		dataTicketResp.setError(BigInteger.valueOf(respuesta.getError()));
		dataTicketResp.setMensaje(respuesta.getMensaje());
		dataTicketResp.setImporteTotal(BigInteger.valueOf(respuesta.getImporteTotal()));
		if (respuesta.getError() == 0) {
			// Doy de alta el ticket en la BD
			ctrlDAO dao = ctrlDAO.getInstance();
			try {
				dao.altaAuto(matricula);
				dao.altaTicket(respuesta.getNroTicket(), fechaVenta, fechaIniE, cantMinutos, respuesta.getImporteTotal(), matricula,xmlAltaT.getNroTerminal().intValue());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return dataTicketResp;
	}
}
