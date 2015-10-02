/**
 * WsTicketService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package antel.com.uy.webservices;

public interface WsTicketService extends javax.xml.rpc.Service {
    public java.lang.String getwsTicketPortAddress();

    public antel.com.uy.webservices.WsTicket getwsTicketPort() throws javax.xml.rpc.ServiceException;

    public antel.com.uy.webservices.WsTicket getwsTicketPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
