package com.uy.antel.controlador;

import java.rmi.RemoteException;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import com.uy.antel.util.util;

import antel.com.uy.webservices.DataAnulacion;
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

	public DataTicket altaTicket(String matricula, Date fechaIniE, int cantMinutos, Date fechaVenta, String agencia) {
		WsTicketServiceLocator wsIMM = new WsTicketServiceLocator();
		try {
			DataTicket respuesta = wsIMM.getwsTicketPort().altaTicket(matricula,util.dateToString(fechaIniE),
					cantMinutos, util.dateToString(fechaVenta), agencia);

			return respuesta;

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DataTicket dataError = new DataTicket();
		dataError.setError(201);
		dataError.setMensaje("Error del sistema.");
		return dataError;
	}
	
	public DataAnulacion cancelacionTicket(int nroTicket, String idAgencia) {
		WsTicketServiceLocator wsIMM = new WsTicketServiceLocator();
		try {
			DataAnulacion respuesta = wsIMM.getwsTicketPort().anulacionTicket(nroTicket,idAgencia);

			return respuesta;

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DataAnulacion dataAnulacionErr = new DataAnulacion();
		dataAnulacionErr.setError(201);
		dataAnulacionErr.setMensaje("Error del sistema.");
		return dataAnulacionErr;
	}
}
