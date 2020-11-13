package com.sdzee.tp.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.tp.beans.Client;

@WebServlet( "/SuppressionClient" )
public class SuppressionClient extends HttpServlet {
    private static final long  serialVersionUID = 1L;
    public static final String SESSION_CLIENTS  = "clients";
    public static final String VUE_SUCCES       = "/WEB-INF/listeClients.jsp";

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Map<String, Client> mapDelete = (HashMap<String, Client>) session.getAttribute( SESSION_CLIENTS );
        String keyV = request.getParameter( "nom" );
        mapDelete.remove( keyV );

        this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );

    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

    }

}
