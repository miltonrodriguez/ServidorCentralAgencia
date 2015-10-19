/**
 * WsTicket.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package antel.com.uy.webservices;

public interface WsTicket extends java.rmi.Remote {
    public antel.com.uy.webservices.DataTicket altaTicket(java.lang.String arg0, java.lang.String arg1, int arg2, java.lang.String arg3, java.lang.String arg4) throws java.rmi.RemoteException;
    public antel.com.uy.webservices.DataAnulacion anulacionTicket(int arg0, java.lang.String arg1) throws java.rmi.RemoteException;
}
