package com.uy.antel.controlador;

import java.util.Date;

import org.apache.commons.lang3.tuple.Pair;

import com.uy.antel.util.util;
import com.uy.antel.xml.login.XmlLogin;
import com.uy.antel.xml.loginResp.XmlLoginResp;
import com.uy.antel.xml.respTicket.ObjectFactory;
import com.uy.antel.xml.respTicket.OperacionT;
import com.uy.antel.xml.respTicket.XmlRes;
import com.uy.antel.xml.respTicket.XmlRes.XmlRespAltaTicket;
import com.uy.antel.xml.respTicket.XmlRes.XmlRespCancelacionTicket;
import com.uy.antel.xml.ticket.XmlTicket.XmlAltaTicket;
import com.uy.antel.xml.ticket.XmlTicket.XmlCancelacionTicket;

import antel.com.uy.webservices.DataTicket;

public class ctrlCentral {

	private static ctrlCentral instance = null;

	public static ctrlCentral getInstance() {
		if (instance == null) {
			instance = new ctrlCentral();
		}
		return instance;
	}

	public XmlRes altaTicket(XmlAltaTicket xmlAltaT, int nroTerminal) {
		ObjectFactory factory = new ObjectFactory();
		XmlRespAltaTicket respAltaTicket = factory.createXmlResXmlRespAltaTicket();
		XmlRes respTicket = factory.createXmlRes();
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
				respAltaTicket.setError(respuesta.getError());
				respAltaTicket.setMensaje(respuesta.getMensaje());
				respAltaTicket.setImporteTotal(respuesta.getImporteTotal());
				respAltaTicket.setNroTicket(respuesta.getNroTicket());
				if (respuesta.getError() == 0) {
					// Doy de alta el ticket en la BD
					System.out
							.println("ctrCentral - altaticket - se va a persistir en la BD de agencia el alta ticket");
					ctrlDAO dao = ctrlDAO.getInstance();
					dao.altaAuto(matricula);
					System.out.println(
							"ctrCentral - altaticket - se persistio el auto en la BD de agencia el alta ticket");
					dao.altaTicket(respuesta.getNroTicket(), fechaVenta, fechaIniE, cantMinutos,
							respuesta.getImporteTotal(), matricula, nroTerminal);
					System.out.println(
							"ctrCentral - altaticket - se persistio el ticketen la BD de agencia el alta ticket");

				}
			} else {
				// Error de entrada
				respAltaTicket.setError(resp.getKey());
				respAltaTicket.setMensaje(resp.getValue());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respAltaTicket.setError(201);
			respAltaTicket.setMensaje("Error del sistema.");
		}
		System.out
				.println("ctrCentral - altaticket - se re torna la respuesta con codigo: " + respAltaTicket.getError());
		respTicket.setOperacion(OperacionT.ALTA);
		respTicket.setXmlRespAltaTicket(respAltaTicket);
		return respTicket;
	}

	public XmlRes cancelacionTicket(XmlCancelacionTicket xmlCancTicket, int nroTerminal) {
		ObjectFactory factory = new ObjectFactory();
		XmlRespCancelacionTicket respCancelTicket = factory.createXmlResXmlRespCancelacionTicket();
		XmlRes respTicket = factory.createXmlRes();
		try {
			ctrlWS ws = ctrlWS.getInstance();
			Date fechaVenta = new Date();

			ICtrValidacion validacion = ctrValidacion.getInstance();
			Pair<Integer, String> resp = validacion.validarEntradaCancelacionTicket(xmlCancTicket.getNroTicket());
			if (resp.getKey() == 0) {
				// No hubo error al validar los datos de entrada
				System.out.println("ctrCentral - cancelacionticket - paso las validaciones de entrada");
				// TODO:seguir esto cuando carlos haga el WS
				// DataTicket respuesta = ws.altaTicket(matricula, fechaIniE,
				// cantMinutos, fechaVenta,util.getIdAgencia());
				System.out.println("ctrCentral - cancelacionticket - paso la invocacion al ws");

				// respCancelTicket.setError(respuesta.getError());
				// respCancelTicket.setMensaje(respuesta.getMensaje());
				if (respuesta.getError() == 0) {
					// Doy de alta la cancelacion en la BD
					System.out.println(
							"ctrCentral - cancelacionticket - se va a persistir en la BD de agencia la cancelacion del ticket");
					ctrlDAO dao = ctrlDAO.getInstance();
					dao.altaCancelacion(xmlCancTicket.getNroTicket(), nroTerminal);
					System.out.println(
							"ctrCentral - cancelacionticket - se persistio la cancelacion en la BD de agencia");
				}
			} else {
				// Error de entrada
				respCancelTicket.setError(resp.getKey());
				respCancelTicket.setMensaje(resp.getValue());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respCancelTicket.setError(201);
			respCancelTicket.setMensaje("Error del sistema.");
		}
		System.out.println(
				"ctrCentral - altaticket - se re torna la respuesta con codigo: " + respCancelTicket.getError());
		respTicket.setOperacion(OperacionT.CANCELACION);
		respTicket.setXmlRespCancelacionTicket(respCancelTicket);
		return respTicket;
	}

	public XmlLoginResp login(XmlLogin login) {
		com.uy.antel.xml.loginResp.ObjectFactory factory = new com.uy.antel.xml.loginResp.ObjectFactory();
		XmlLoginResp loginResp = factory.createXmlLoginResp();
		try {
			ICtrValidacion validacion = ctrValidacion.getInstance();
			Pair<Integer, String> resp = validacion.validarEntradaLogin(login.getUsuario(), login.getPassword(),
					login.getNroTerminal());
			if (resp.getKey() == 0) {
				// No hubo error al validar los datos de entrada
				System.out.println("ctrCentral - login - paso las validaciones de entrada");
				ctrlDAO dao = ctrlDAO.getInstance();
				if (dao.login(login.getUsuario(), login.getPassword(), login.getNroTerminal())) {
					loginResp.setError(0);
					loginResp.setMensaje("Login OK");
				} else {
					loginResp.setError(1);
					loginResp.setMensaje(
							"Usuario y password no coinciden o usuario no habilitado para loguearse en esta terminal.");
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
		System.out.println("ctrCentral - login - se re torna la respuesta con codigo: " + loginResp.getError());
		return loginResp;
	}

}
