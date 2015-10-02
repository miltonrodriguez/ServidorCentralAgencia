package antel.com.uy.webservices;

public class WsTicketProxy implements antel.com.uy.webservices.WsTicket {
  private String _endpoint = null;
  private antel.com.uy.webservices.WsTicket wsTicket = null;
  
  public WsTicketProxy() {
    _initWsTicketProxy();
  }
  
  public WsTicketProxy(String endpoint) {
    _endpoint = endpoint;
    _initWsTicketProxy();
  }
  
  private void _initWsTicketProxy() {
    try {
      wsTicket = (new antel.com.uy.webservices.WsTicketServiceLocator()).getwsTicketPort();
      if (wsTicket != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wsTicket)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wsTicket)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wsTicket != null)
      ((javax.xml.rpc.Stub)wsTicket)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public antel.com.uy.webservices.WsTicket getWsTicket() {
    if (wsTicket == null)
      _initWsTicketProxy();
    return wsTicket;
  }
  
  public antel.com.uy.webservices.DataTicket altaTicket(java.lang.String arg0, java.util.Calendar arg1, int arg2, java.util.Calendar arg3, java.lang.String arg4) throws java.rmi.RemoteException{
    if (wsTicket == null)
      _initWsTicketProxy();
    return wsTicket.altaTicket(arg0, arg1, arg2, arg3, arg4);
  }
  
  
}