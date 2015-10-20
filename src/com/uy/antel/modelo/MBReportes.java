package com.uy.antel.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;












import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

import com.uy.antel.controlador.ctrReportes;
import com.uy.antel.util.ReportConfigUtil;

public  class MBReportes extends AbstractReportBean{
	
	private String COMPILE_FILE_NAME = "ReporteVentaMensualDiario";
	
	public enum AccionReporte {		 
        DIARIO,FRANJAS
    } 
	
	public AccionReporte accionReporte;
	
	   
    public AccionReporte getAccionReporte() {
		return accionReporte;
	}

	public void setAccionReporte(AccionReporte accionReporte) {
		this.accionReporte = accionReporte;
	}

	@Override
    protected String getCompileFileName() {
        return COMPILE_FILE_NAME;
    }
 
    @Override
    protected Map<String, Object> getReportParameters() {
        Map<String, Object> reportParameters = new HashMap<String, Object>();
 
        reportParameters.put("rtitle", "Hello JasperReports");
 
        return reportParameters;
    }
		
	private int anio = 2015;//TODO: sacar este hardcode
	private List<BReporteVentaMensualDiaria> reporteVentaMensualDiario;
	private List<BReporteVentaMensualFranja> reporteVentaMensualFranja;

	

	public MBReportes(){
		
	}
	
	
//	public String exportar(String tipo, int mes){		 	 
//		 try {			 
//			 AccionReporte tipo_enum = AccionReporte.valueOf(tipo);
//			 JRBeanCollectionDataSource beanColDataSource = null;
//			 if (tipo_enum == AccionReporte.DIARIO){
//				 COMPILE_FILE_NAME = "ReporteVentaMensualDiario";				 
//				 List<BReporteVentaMensualDiaria> dataList = ctrReportes.getInstance().getReporteVentaMensualDiaria(anio, mes);                   
//				 beanColDataSource = new JRBeanCollectionDataSource(dataList);			      
//			  }else if (tipo_enum == AccionReporte.FRANJAS){
//				  COMPILE_FILE_NAME = "ReportePorFecha";
//				  List<BReporteVentaMensualFranja> dataList = ctrReportes.getInstance().getReporteVentaMensualFranja(anio, mes);                 
//				  beanColDataSource = new JRBeanCollectionDataSource(dataList);			      
//			 }			 
//			 super.prepareReport(beanColDataSource);
//	        } catch (Exception e) {
//	        	e.printStackTrace();
//	        }
//		 return null;
//	 }
//	 
//	 public String filtrar(){		 	 
//		 try {
//			 	getReportePorFecha();
//	        } catch (Exception e) {
//	        	e.printStackTrace();
//	        }
//		 return null;
//	 }
	
	
}
