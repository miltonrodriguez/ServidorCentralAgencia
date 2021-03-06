package com.uy.antel.controlador;

import java.util.ArrayList;
import java.util.List;

import com.uy.antel.modelo.BReporteVentaMensualDiaria;
import com.uy.antel.modelo.BReporteVentaMensualFranja;
import com.uy.antel.util.util;

public class ctrReportes {
	private static ctrReportes instance;
	private ctrReportes() {

	}

	public static ctrReportes getInstance() {
		if (instance == null) {
			instance = new ctrReportes();
		}
		return instance;
	}

	/**
	 * 
	 * @param anio
	 * @param mes
	 * @return
	 */
	public List<BReporteVentaMensualDiaria> getReporteVentaMensualDiaria(int anio, int mes) {
		ArrayList<BReporteVentaMensualDiaria> reporteMensual = new ArrayList<BReporteVentaMensualDiaria>();
		try {
			ctrlDAO dao = ctrlDAO.getInstance();
			reporteMensual = dao.getReporteVentaMensualDiaria(mes, anio);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int maxDias = util.getDiasMaxMes(mes, anio);
		for (int i = 0; i < maxDias; i++) {
			if (((reporteMensual.size()>i) && (reporteMensual.get(i).getDia() != i+1))||(reporteMensual.size()<=i)) {
				// Si no esta agrego uno con 0
				BReporteVentaMensualDiaria nuevo = new BReporteVentaMensualDiaria(i+1, 0, 0);
				reporteMensual.add(i, nuevo);
			}

		}
		return reporteMensual;
	}

	/**
	 * 
	 * @param anio
	 * @param mes
	 * @return
	 */
	public List<BReporteVentaMensualFranja> getReporteVentaMensualFranja(int anio, int mes) {
		ArrayList<BReporteVentaMensualFranja> reporteMensual = new ArrayList<BReporteVentaMensualFranja>();
		try {
			ctrlDAO dao = ctrlDAO.getInstance();
			reporteMensual = dao.getReporteVentaMensualFranja(mes, anio);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 24; i++) {
			if (((reporteMensual.size()>i) && (reporteMensual.get(i).getHora() != i))||(reporteMensual.size()<=i)) {
				// Si no esta agrego uno con 0
				BReporteVentaMensualFranja nuevo = new BReporteVentaMensualFranja(i, 0, 0);
				reporteMensual.add(i, nuevo);
			}

		}
		return reporteMensual;
	}

}
