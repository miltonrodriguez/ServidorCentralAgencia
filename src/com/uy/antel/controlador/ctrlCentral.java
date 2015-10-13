package com.uy.antel.controlador;

import java.util.Date;

import org.apache.commons.lang3.tuple.Pair;

import com.uy.antel.util.util;
import com.uy.antel.xml.AltaTicket.XmlAltaTicket;
import com.uy.antel.xml.DataTicket.ObjectFactory;
import com.uy.antel.xml.DataTicket.XmlDataTicket;
import com.uy.antel.xml.login.XmlLogin;
import com.uy.antel.xml.loginResp.XmlLoginResp;

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
		ObjectFactory factory = new ObjectFactory();
		XmlDataTicket dataTicketResp = factory.createXmlDataTicket();
		try {
			ctrlWS ws = ctrlWS.getInstance();
			Date fechaVenta = new Date();

			ICtrValidacion validacion = ctrValidacion.getInstance();
			Pair<Integer, String> resp = validacion.validarEntradaAltaTicket(xmlAltaT.getMatricula(),
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
				dataTicketResp.setError(respuesta.getError());
				dataTicketResp.setMensaje(respuesta.getMensaje());
				dataTicketResp.setImporteTotal(respuesta.getImporteTotal());
				dataTicketResp.setNroTicket(respuesta.getNroTicket());
				if (respuesta.getError() == 0) {
					// Doy de alta el ticket en la BD
					System.out.println("ctrCentral - altaticket - se va a persistir en la BD de agencia el alta ticket");
					ctrlDAO dao = ctrlDAO.getInstance();
					dao.altaAuto(matricula);
					System.out.println("ctrCentral - altaticket - se persistio el auto en la BD de agencia el alta ticket");
					dao.altaTicket(respuesta.getNroTicket(), fechaVenta, fechaIniE, cantMinutos,
							respuesta.getImporteTotal(), matricula, xmlAltaT.getNroTerminal());
					System.out.println("ctrCentral - altaticket - se persistio el ticketen la BD de agencia el alta ticket");

				}
			} else {
				// Error de entrada
				dataTicketResp.setError(resp.getKey());
				dataTicketResp.setMensaje(resp.getValue());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dataTicketResp.setError(201);
			dataTicketResp.setMensaje("Error del sistema.");
		}
		System.out.println("ctrCentral - altaticket - se re torna la respuesta con codigo: "+ dataTicketResp.getError());
		return dataTicketResp;
	}
	
	public XmlLoginResp login(XmlLogin login) {
		com.uy.antel.xml.loginResp.ObjectFactory factory = new com.uy.antel.xml.loginResp.ObjectFactory();
		XmlLoginResp loginResp = factory.createXmlLoginResp();
		try {
			ICtrValidacion validacion = ctrValidacion.getInstance();
			Pair<Integer, String> resp = validacion.validarEntradaLogin(login.getUsuario(),login.getPassword(), login.getNroTerminal());
			if (resp.getKey() == 0) {
				// No hubo error al validar los datos de entrada
				System.out.println("ctrCentral - login - paso las validaciones de entrada");
				ctrlDAO dao = ctrlDAO.getInstance();
				if (dao.login(login.getUsuario(), login.getPassword(), login.getNroTerminal())){
					loginResp.setError(0);
					loginResp.setMensaje("Login OK");
				}
				else{
					loginResp.setError(1);
					loginResp.setMensaje("Usuario y password no coinciden o usuario no habilitado para loguearse en esta terminal.");
				}
				
			} else {
				// Error de entrada
				loginResp.setError(resp.getKey());
				loginResp.setMensaje(resp.getValue());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			loginResp.setError(201);
			loginResp.setMensaje("Error del sistema.");
		}
		System.out.println("ctrCentral - login - se re torna la respuesta con codigo: "+ loginResp.getError());
		return loginResp;
	}

}
