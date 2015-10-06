package com.uy.antel.controlador;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import com.uy.antel.util.util;

import antel.com.uy.webservices.DataTicket;
import antel.com.uy.webservices.WsTicketServiceLocator;

public class ctrlWS {

	private static ctrlWS instance = null;

	public static ctrlWS getInstance() {
		if (instance == null) {
			instance = new ctrlWS();
		}
		return instance;
	}
	
	public DataTicket altaTicket(String matricual, Date fechaIniE, int cantMinutos, Date fechaVenta, String agencia) {
		WsTicketServiceLocator wsIMM = new WsTicketServiceLocator();
		try {//TODO manejar las excepciones y agregar el id de la agenciad esde una property
			DataTicket respuesta = wsIMM.getwsTicketPort().altaTicket(matricual,util.dateToString(fechaIniE), cantMinutos,util.dateToString(fechaVenta),agencia);
						
			return respuesta;
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
