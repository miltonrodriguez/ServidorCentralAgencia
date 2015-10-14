package com.uy.antel.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,	FilterChain chain) throws IOException, ServletException {
		HttpSession session;
	    if (req instanceof HttpServletRequest){
	        session = ((HttpServletRequest)req).getSession();
	        
	        if ((session.getAttribute("UsuarioAgencia") != null) || (((HttpServletRequest) req).getRequestURI().contains("/Admin/Login.jsp")) ){
	            chain.doFilter(req, res);
	        } else {
	        	 String contextPath = ((HttpServletRequest)req).getContextPath();
	             ((HttpServletResponse)res).sendRedirect(contextPath + "/Admin/Login.jsp");
	        }
	    }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
