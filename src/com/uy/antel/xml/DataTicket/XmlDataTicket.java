//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2015.10.13 a las 12:00:09 AM GMT-03:00 
//


package com.uy.antel.xml.DataTicket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="error" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mensaje" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="importeTotal" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nroTicket" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "error",
    "mensaje",
    "importeTotal",
    "nroTicket"
})
@XmlRootElement(name = "xmlDataTicket")
public class XmlDataTicket {

    protected int error;
    @XmlElement(required = true)
    protected String mensaje;
    protected int importeTotal;
    protected int nroTicket;

    /**
     * Obtiene el valor de la propiedad error.
     * 
     */
    public int getError() {
        return error;
    }

    /**
     * Define el valor de la propiedad error.
     * 
     */
    public void setError(int value) {
        this.error = value;
    }

    /**
     * Obtiene el valor de la propiedad mensaje.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Define el valor de la propiedad mensaje.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensaje(String value) {
        this.mensaje = value;
    }

    /**
     * Obtiene el valor de la propiedad importeTotal.
     * 
     */
    public int getImporteTotal() {
        return importeTotal;
    }

    /**
     * Define el valor de la propiedad importeTotal.
     * 
     */
    public void setImporteTotal(int value) {
        this.importeTotal = value;
    }

    /**
     * Obtiene el valor de la propiedad nroTicket.
     * 
     */
    public int getNroTicket() {
        return nroTicket;
    }

    /**
     * Define el valor de la propiedad nroTicket.
     * 
     */
    public void setNroTicket(int value) {
        this.nroTicket = value;
    }

}
