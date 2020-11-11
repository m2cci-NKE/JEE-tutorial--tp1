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
import com.sdzee.tp.forms.CreationCommandeForm;

@WebServlet( "/CreationCommande" )
public class CreationCommande extends HttpServlet {

    public static final String ATT_COMMANDE     = "commande";
    public static final String ATT_FORM         = "form";

    public static final String VUE_SUCCES       = "/listeCommandes";
    public static final String VUE_FORM         = "/WEB-INF/creeCommande.jsp";
    public static final String SESSION_COMMANDE = "mapCommande";
    public static final String ATT_CKECK_NAME   = "name";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        /* Transmission à la page JSP en charge de l'affichage des données */
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        CreationCommandeForm form = new CreationCommandeForm();
        Commande commande = form.commandeForm( request );

        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_COMMANDE, commande );

        String checkName = request.getParameter( "newClient" );

        if ( form.erreurs.isEmpty() ) {

            HttpSession session = request.getSession();

            Map<String, Commande> mapCommande = (HashMap<String, Commande>) session.getAttribute( SESSION_COMMANDE );
            if ( mapCommande == null ) {
                mapCommande = new HashMap<String, Commande>();
            }

            mapCommande.put( commande.getDate(), commande );
            session.setAttribute( SESSION_COMMANDE, mapCommande );

            request.setAttribute( ATT_CKECK_NAME, checkName );

            // response.sendRedirect( VUE_SUCCES );
            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        }
        // if ( checkName != null ) {
        // request.setAttribute( ATT_CKECK_NAME, checkName );
        // this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward(
        // request, response );
        // }

        else {

            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );

        }

    }

}