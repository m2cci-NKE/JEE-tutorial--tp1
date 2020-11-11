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

import com.sdzee.tp.beans.Commande;

@WebServlet( "/SuppressionCommande" )
public class SuppressionCommande extends HttpServlet {
    private static final long  serialVersionUID = 1L;

    public static final String SESSION_COMMANDE = "mapCommande";
    public static final String VUE_SUCCES       = "/WEB-INF/listeCommandes.jsp";

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Map<String, Commande> mapDelete = (HashMap<String, Commande>) session.getAttribute( SESSION_COMMANDE );
        String keyV = request.getParameter( "date" );
        mapDelete.remove( keyV );

        this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );

    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        doGet( request, response );
    }

}
