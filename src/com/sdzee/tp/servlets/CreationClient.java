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
import com.sdzee.tp.forms.CreationClientForm;

@WebServlet( "/CreationClient" )
public class CreationClient extends HttpServlet {
    private static final long  serialVersionUID = 1L;

    public static final String ATT_CLIENT       = "client";
    public static final String ATT_FORM         = "form";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String VUE_SUCCES       = "/tp1/listeClients";

    public static final String VUE_FORM         = "/WEB-INF/creeClient.jsp";
    public static final String SESSION_CLIENTS  = "mapClient";

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );

    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        CreationClientForm form = new CreationClientForm();

        Client client = form.clientForm( request );

        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_CLIENT, client );

        if ( form.getErreurs().isEmpty() ) {
            HttpSession session = request.getSession();

            Map<String, Client> mapClient = (HashMap<String, Client>) session.getAttribute( SESSION_CLIENTS );
            if ( mapClient == null ) {
                mapClient = new HashMap<String, Client>();
            }

            mapClient.put( client.getNom(), client );
            session.setAttribute( SESSION_CLIENTS, mapClient );

            response.sendRedirect( VUE_SUCCES );

        } else if ( form.getFind() == true ) {
            response.sendRedirect( VUE_SUCCES );

        }

        else {
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }

    }

}
