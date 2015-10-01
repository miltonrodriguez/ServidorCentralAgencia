package com.uy.antel.controlador;

import java.net.*;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.uy.antel.xml.AltaTicket.*;
import com.uy.antel.xml.DataTicket.*;

import java.io.*;

public class atenderTerminal implements Runnable {

	private Socket client;

	// Constructor
	atenderTerminal(Socket client) {
		this.client = client;
	}

	public void run() {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = client.getInputStream();
			out = client.getOutputStream();
		} catch (IOException e) {
			System.out.println("in or out failed");
			System.exit(-1);
		}

		while (true) {
			try {
				SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				Schema schema;

				schema = sf.newSchema(new File("src/com/uy/antel/xml/altaTicket.xsd"));
				JAXBContext jaxbContext;
				jaxbContext = JAXBContext.newInstance(XmlAltaTicket.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				jaxbUnmarshaller.setSchema(schema);
				//Hago la conversion de XML -> objeto AltaTicket.
				XmlAltaTicket inAltaTicket = (XmlAltaTicket) jaxbUnmarshaller.unmarshal(in);
				
				System.out.println("unmarshall");
				System.out.println(inAltaTicket.getMatricula());
				System.out.println(inAltaTicket.getCantidadMinutos());
				System.out.println(inAltaTicket.getFechaHoraInicioEst());
				
				ctrlCentral central = ctrlCentral.getInstance();
				XmlDataTicket outDataTicket = central.altaTicket(inAltaTicket);
				
				JAXBContext context = JAXBContext.newInstance("com.uy.antel.util.dataTicket");

				Marshaller marshaller = context.createMarshaller();

				marshaller.marshal(outDataTicket,out);
				
				
				
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// JTextArea.appendJTextArea.appendtextArea.append(line)synchronizedruntextArea.append(line)appendText(line)
	//
	// public synchronized void appendText(line){
	// textArea.append(line);
	// }

	// synchronizedtextAreatextArea
	//
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
