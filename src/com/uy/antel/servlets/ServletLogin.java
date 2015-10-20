package com.uy.antel.servlets;

import java.io.IOException;
import java.util.ArrayList;

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

import com.uy.antel.controlador.ctrlDAO;

/**
 * Servlet implementation class ServletController
 */
@WebServlet("/Admin/Login")
public class ServletLogin extends HttpServlet {
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
	public ServletLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if ("logout".equals(request.getParameter("operacion"))) {
			HttpSession session = request.getSession();
			session.removeAttribute("UsuarioAgencia");
			RequestDispatcher rd = request.getRequestDispatcher("/Admin/Login.jsp");
			request.setAttribute("respuesta", "Logout correcto");
			rd.include(request, response);
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("/Admin/Login.jsp");
			request.setAttribute("respuesta", "Operacion Invalida");
			rd.include(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if ("login".equals(request.getParameter("operacion"))) {
			String usuario = request.getParameter("usuario");
			String password = request.getParameter("password");
			ctrlDAO dao = ctrlDAO.getInstance();
			boolean login = false;
			try {
				login = dao.login(usuario, password, -1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (login) {
				RequestDispatcher rd = request.getRequestDispatcher("Reportes.jsp");
				HttpSession session = request.getSession();
				session.setAttribute("UsuarioAgencia", usuario);
				// setting session to expiry in 30 mins
				session.setMaxInactiveInterval(30 * 60);
				// Cookie userCookie = new Cookie("user", usuario);
				// userCookie.setMaxAge(30*60);
				// response.addCookie(userCookie);
				rd.include(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/Admin/Login.jsp");
				request.setAttribute("respuesta", "Usuario o contraseña incorrecta");
				rd.include(request, response);
			}
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("/Admin/Login.jsp");
			request.setAttribute("respuesta", "Operacion Invalida");
			rd.include(request, response);
		}
	}

}
