package com.uy.antel.util;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportConfigUtil {
	/**
     * PRIVATE METHODS
     */
    private static void setCompileTempDir(ServletContext context, String uri) {
        System.setProperty("jasper.reports.compile.temp", context.getRealPath(uri));
    }
 
    /**
     * PUBLIC METHODS
     */
    public static boolean compileReport(ServletContext context, String compileDir, String filename) throws JRException {
        String jasperFileName = context.getRealPath(compileDir + filename + ".jasper");
        File jasperFile = new File(jasperFileName); 
        try {
            setCompileTempDir(context, compileDir); 
            String xmlFileName = jasperFileName.substring(0, jasperFileName.indexOf(".jasper")) + ".jrxml";
            JasperCompileManager.compileReportToFile(xmlFileName);            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
 
    public static JasperPrint fillReport(File reportFile, Map<String, Object> parameters, JRBeanCollectionDataSource beanColDataSource) throws JRException {
        System.out.println(reportFile.getParentFile());
    	parameters.put("BaseDir", reportFile.getParentFile()); 
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), parameters, beanColDataSource); 
        return jasperPrint;
    }
 
    public static String getJasperFilePath(ServletContext context, String compileDir, String jasperFile) {
        return context.getRealPath(compileDir + jasperFile);
    }

    public static void exportReportAsHtml(JasperPrint jasperPrint, PrintWriter out) throws JRException {
    	
    }
     
}
