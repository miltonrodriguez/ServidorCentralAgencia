package com.uy.antel.controlador;

import java.util.Calendar;
import java.util.Date;

import com.uy.antel.xml.AltaTicket.XmlAltaTicket;
import com.uy.antel.xml.DataTicket.XmlDataTicket;

import antel.com.uy.webservices.WsTicket;
import antel.com.uy.webservices.WsTicketService;
import antel.com.uy.webservices.WsTicketServiceLocator;

public class ctrlCentral {

	private static ctrlCentral instance = null;

	public static ctrlCentral getInstance() {
		if (instance == null) {
			instance = new ctrlCentral();
		}
		return instance;
	}
	
	public XmlDataTicket altaTicket(XmlAltaTicket altaT) {
		//WsTicketServiceLocator wsIMM = new WsTicketServiceLocator();
		WsTicket service = new WsTicketService().getwsTicketPort();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		service.altaTicket(altaT.getMatricula(), altaT.getFechaHoraInicioEst().toCalendar(), altaT.getCantidadMinutos().intValue(), calendar, 1);
		return null;
	}
}
