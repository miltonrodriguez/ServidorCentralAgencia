package com.uy.antel.xml;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.uy.antel.xml.AltaTicket.*;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class xml {

	public static void main(String[] args) throws JAXBException, DatatypeConfigurationException, SAXException{
		ObjectFactory factory = new ObjectFactory();

		AltaTicket altaTicket = factory.createAltaTicket();
		altaTicket.setCantidadMinutos(BigInteger.valueOf(30));
		
		GregorianCalendar c = new GregorianCalendar();
		//c.setTime(new Date());
		c.setTimeZone(TimeZone.getTimeZone("GMT-3"));
		c.set(2015, 9, 25, 23, 50,0);
		XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
				
		altaTicket.setFechaHoraInicioEst(xgc);
		
		altaTicket.setMatricula("SBT6668");
		
		

		
		JAXBContext context = JAXBContext.newInstance("com.uy.antel.util.altaTicket");

		//JAXBElement<AltaTicket> elemento = factory.createAltaTicket(altaTicket);
		
		Marshaller marshaller = context.createMarshaller();

		marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);

		marshaller.marshal(altaTicket, System.out);
		
		File fileo = new File("prueba.xml");
		marshaller.marshal(altaTicket, fileo);
		
		
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
        //Schema schema = sf.newSchema(new File("src/com/uy/antel/util/altaTicket.xsd"));
		Schema schema = sf.newSchema(new File("src/com/uy/antel/util/altaTicket.xsd"));
		
		File filei = new File("prueba.xml");
//        File filei = new File("altaTicketpru.xml");
        //File filei = new File("dataTicketpru.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(AltaTicket.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		jaxbUnmarshaller.setSchema(schema);
//		AltaTicketT altaTicket = factory.createAltaTicketT();
//		altaTicket = (AltaTicketT)jaxbUnmarshaller.unmarshal(filei);
//		System.out.println(altaTicket);
		
		AltaTicket data = (AltaTicket) jaxbUnmarshaller.unmarshal(filei);
		//jaxbUnmarshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
		System.out.println("unmarshall");
		System.out.println(data.getMatricula());
		System.out.println(data.getCantidadMinutos());
		System.out.println(data.getFechaHoraInicioEst());
		
				

	}
}
