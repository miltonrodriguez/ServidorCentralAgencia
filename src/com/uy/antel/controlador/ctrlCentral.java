package com.uy.antel.controlador;

import java.math.BigInteger;
import java.util.Date;

import com.uy.antel.util.util;
import com.uy.antel.xml.AltaTicket.XmlAltaTicket;
import com.uy.antel.xml.DataTicket.ObjectFactory;
import com.uy.antel.xml.DataTicket.XmlDataTicket;

import antel.com.uy.webservices.DataTicket;
import org.apache.commons.lang3.tuple.Pair;

public class ctrlCentral {

	private static ctrlCentral instance = null;

	public static ctrlCentral getInstance() {
		if (instance == null) {
			instance = new ctrlCentral();
		}
		return instance;
	}

	public XmlDataTicket altaTicket(XmlAltaTicket xmlAltaT) {
		ObjectFactory factory = new ObjectFactory();
		XmlDataTicket dataTicketResp = factory.createXmlDataTicket();
		try {
			ctrlWS ws = ctrlWS.getInstance();
			Date fechaVenta = new Date();

			ICtrValidacion validacion = ctrValidacion.getInstance();
			Pair<Integer, String> resp = validacion.validarEntrada(xmlAltaT.getMatricula(),
					xmlAltaT.getFechaHoraInicioEst(), xmlAltaT.getCantidadMinutos(), fechaVenta);
			if (resp.getKey() == 0) {
				// No hubo error al validar los datos de entrada
				System.out.println("ctrCentral - altaticket - paso las validaciones de entrada");
				Date fechaIniE = util.stringToDate(xmlAltaT.getFechaHoraInicioEst());
				int cantMinutos = xmlAltaT.getCantidadMinutos();
				String matricula = xmlAltaT.getMatricula();
				DataTicket respuesta = ws.altaTicket(matricula, fechaIniE, cantMinutos, fechaVenta,
						util.getIdAgencia());
				System.out.println("ctrCentral - altaticket - paso la invocacion al alta ticket en el ws");
				dataTicketResp.setError(BigInteger.valueOf(respuesta.getError()));
				dataTicketResp.setMensaje(respuesta.getMensaje());
				dataTicketResp.setImporteTotal(respuesta.getImporteTotal());
				dataTicketResp.setNroTicket(respuesta.getNroTicket());
				if (respuesta.getError() == 0) {
					// Doy de alta el ticket en la BD
					System.out.println("ctrCentral - altaticket - se va a persistir en la BD de agencia el alta ticket");
					ctrlDAO dao = ctrlDAO.getInstance();
					dao.altaAuto(matricula);
					dao.altaTicket(respuesta.getNroTicket(), fechaVenta, fechaIniE, cantMinutos,
							respuesta.getImporteTotal(), matricula, xmlAltaT.getNroTerminal());

				}
			} else {
				// Error de entrada
				dataTicketResp.setError(BigInteger.valueOf(resp.getKey()));
				dataTicketResp.setMensaje(resp.getValue());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dataTicketResp.setError(BigInteger.valueOf(201));
			dataTicketResp.setMensaje("Error del sistema.");
		}
		System.out.println("ctrCentral - altaticket - se re torna la respuesta con codigo: "+ dataTicketResp.getError());
		return dataTicketResp;
	}
}
