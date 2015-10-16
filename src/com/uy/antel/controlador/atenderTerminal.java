package com.uy.antel.controlador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.uy.antel.util.util;
import com.uy.antel.xml.login.XmlLogin;
import com.uy.antel.xml.loginResp.XmlLoginResp;
import com.uy.antel.xml.respTicket.OperacionT;
import com.uy.antel.xml.respTicket.XmlRes;
import com.uy.antel.xml.ticket.XmlTicket;

public class atenderTerminal implements Runnable {

	private Socket client;

	// Constructor
	atenderTerminal(Socket client) {
		this.client = client;
	}

	public void run() {
		DataInputStream is = null;
		DataOutputStream os = null;
		int nroTerminal = 0;
		try {
			is = new DataInputStream(client.getInputStream());
			os = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			System.out.println("in or out failed");
			System.exit(-1);
		}
		try {
			boolean login = false;
			int cont = 0;
			while (!login && cont < util.getCantIntentosLogin()) {
				System.out.println("login");
				JAXBContext jaxbContext;
				jaxbContext = JAXBContext.newInstance("com.uy.antel.xml.login");
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				// Hago la conversion de XML -> objeto AltaTicket.
				System.out.println("antes unmarshal login");
				XmlLogin inLogin = (XmlLogin) jaxbUnmarshaller.unmarshal(new StringReader(is.readUTF()));
				System.out.println("unmarshal login");
				ctrlCentral central = ctrlCentral.getInstance();
				XmlLoginResp outLogin = central.login(inLogin);
				if (outLogin.getError() == 0) {
					login = true;
					nroTerminal = inLogin.getNroTerminal();
					System.out.println("marshal login OK");
				}
				JAXBContext context = JAXBContext.newInstance("com.uy.antel.xml.loginResp");
				Marshaller marshaller = context.createMarshaller();
				StringWriter writer = new StringWriter();
				marshaller.marshal(outLogin, writer);
				System.out.println("marshal login");
				os.writeUTF(writer.toString());
				cont++;
			}
			// Si se logro hacer el login, continua la ejecucion de este socket
			if (login) {

				while (true) {

					JAXBContext jaxbContext;
					jaxbContext = JAXBContext.newInstance("com.uy.antel.xml.ticket");
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					// Hago la conversion de XML -> objeto xmlTickt.
					System.out.println("unmarshall 1");
					XmlTicket inTicket = (XmlTicket) jaxbUnmarshaller.unmarshal(new StringReader(is.readUTF()));
					ctrlCentral central = ctrlCentral.getInstance();
					XmlRes outRespTicket;
					if (inTicket.getOperacion().toString() == OperacionT.ALTA.toString()) {
						// ALTA
						outRespTicket = central.altaTicket(inTicket.getXmlAltaTicket(),nroTerminal);
					}else if (inTicket.getOperacion().toString() == OperacionT.CANCELACION.toString()) {
						// CANCELACION
						outRespTicket = central.cancelacionTicket(inTicket.getXmlCancelacionTicket(),nroTerminal);
						
					}
					
					JAXBContext context = JAXBContext.newInstance("com.uy.antel.xml.respTicket");
					Marshaller marshaller = context.createMarshaller();
					StringWriter writer = new StringWriter();
					marshaller.marshal(outRespTicket, writer);
					os.writeUTF(writer.toString());
				}
			} else {
				finalize();
				return;

			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			System.out.println("JAXException - " + this.getClass());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception - " + this.getClass());
			e.printStackTrace();
		}

	}

	// The finalize() method is called by the Java virtual machine (JVM)* before
	// the program exits to give the program a chance to clean up and release
	// resources. Multi-threaded programs should close all Files and Sockets
	// they use before exiting so they do not face resource starvation. The call
	// to server.close() in the finalize() method closes the Socket connection
	// used by each thread in this program.

	protected void finalize() {
		// Objects created in run method are finalized when
		// program terminates and thread exits
		try {
			client.close();
		} catch (IOException e) {
			System.out.println("Could not close socket");
			System.exit(-1);
		}
	}

}
