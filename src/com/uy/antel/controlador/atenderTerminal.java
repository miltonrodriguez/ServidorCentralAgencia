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

import com.uy.antel.xml.AltaTicket.XmlAltaTicket;
import com.uy.antel.xml.DataTicket.XmlDataTicket;

public class atenderTerminal implements Runnable {

	private Socket client;

	// Constructor
	atenderTerminal(Socket client) {
		this.client = client;
	}

	public void run() {
//		InputStream in = null;
//		OutputStream out = null;
		DataInputStream is = null;
        DataOutputStream os = null;
		try {
//			in = client.getInputStream();
//			out = client.getOutputStream();
			is = new DataInputStream(client.getInputStream());
            os = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			System.out.println("in or out failed");
			System.exit(-1);
		}
		try {
			while (true) {

				JAXBContext jaxbContext;
				jaxbContext = JAXBContext.newInstance("com.uy.antel.xml.AltaTicket");
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				// Hago la conversion de XML -> objeto AltaTicket.
				XmlAltaTicket inAltaTicket = (XmlAltaTicket) jaxbUnmarshaller.unmarshal(new StringReader(is.readUTF()));

				System.out.println("unmarshall");
				System.out.println(inAltaTicket.getMatricula());
				System.out.println(inAltaTicket.getCantidadMinutos());
				System.out.println(inAltaTicket.getFechaHoraInicioEst());

				ctrlCentral central = ctrlCentral.getInstance();
				XmlDataTicket outDataTicket = central.altaTicket(inAltaTicket);

				JAXBContext context = JAXBContext.newInstance("com.uy.antel.xml.DataTicket");

				Marshaller marshaller = context.createMarshaller();

				StringWriter writer = new StringWriter();
				marshaller.marshal(outDataTicket, writer);
				os.writeUTF(writer.toString());

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
