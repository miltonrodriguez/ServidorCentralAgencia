package com.uy.antel.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.uy.antel.controlador.levantarCtrlTerminal;

/**
 * Servlet implementation class lanzador
 */
@WebServlet("/lanzador")
public class lanzador extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public lanzador() {
		// TODO Auto-generated constructor stub

		levantarCtrlTerminal lev = new levantarCtrlTerminal();
		Thread t = new Thread(lev);
		t.start();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

}
