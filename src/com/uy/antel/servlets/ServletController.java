package com.uy.antel.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.uy.antel.controlador.ctrReportes;
import com.uy.antel.controlador.ctrlDAO;
import com.uy.antel.modelo.BReporteVentaMensualDiaria;
import com.uy.antel.modelo.BReporteVentaMensualFranja;

/**
 * Servlet implementation class ServletController
 */
@WebServlet("/Admin/Controlador")
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		ServletConfig sconfig = getServletConfig();
		// System.out.println(sconfig);
		ServletContext sc = sconfig.getServletContext();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Date d = new Date();
		int anio = d.getYear() + 1900;

		if ("pagReportes".equals(request.getParameter("operacion"))) {
			List<BReporteVentaMensualDiaria> ventaMensualDiariaList;
			ctrReportes rep = ctrReportes.getInstance();
			int mes = d.getMonth() + 1;
			ventaMensualDiariaList = rep.getReporteVentaMensualDiaria(anio, mes);
			request.setAttribute("mes", mes);
			request.setAttribute("anio", anio);
			request.setAttribute("ventaMensualDiariaList", ventaMensualDiariaList);
			// request.setAttribute("ventaMensualFranjaList",
			// ventaMensualFranjaList);
			RequestDispatcher rd = request.getRequestDispatcher("Reportes.jsp");
			rd.include(request, response);
		} else if ("getReporte".equals(request.getParameter("operacion"))) {
			List<BReporteVentaMensualDiaria> ventaMensualDiariaList;
			RequestDispatcher rd = request.getRequestDispatcher("Reportes.jsp");
			ctrReportes rep = ctrReportes.getInstance();

			// obtengo los parametros del reporte
			int mes = Integer.parseInt(request.getParameter("optMes"));
			String tipoReporte = request.getParameter("tipoReporte");
			if ("Diario".equals(tipoReporte)) {

				ventaMensualDiariaList = rep.getReporteVentaMensualDiaria(anio, mes);
				request.setAttribute("mes", mes);
				request.setAttribute("anio", anio);
				request.setAttribute("ventaMensualDiariaList", ventaMensualDiariaList);
				// request.setAttribute("ventaMensualFranjaList",
				// ventaMensualFranjaList);
				rd.include(request, response);

			} else if ("Franja".equals(tipoReporte)) {
				List<BReporteVentaMensualFranja> ventaMensualFranjaList;
				ventaMensualFranjaList = rep.getReporteVentaMensualFranja(anio, mes);
				request.setAttribute("mes", mes);
				request.setAttribute("anio", anio);
				// request.setAttribute("ventaMensualDiariaList",
				// ventaMensualDiariaList);
				request.setAttribute("ventaMensualFranjaList", ventaMensualFranjaList);
				rd.include(request, response);

			}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
