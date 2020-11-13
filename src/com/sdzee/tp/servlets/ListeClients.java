package com.sdzee.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListeClients
 */
@WebServlet( "/ListeClients" )
public class ListeClients extends HttpServlet {
    private static final long  serialVersionUID = 1L;

    public static final String VUE_SUCCES       = "/WEB-INF/listeClients.jsp";


    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

      

    }

}
