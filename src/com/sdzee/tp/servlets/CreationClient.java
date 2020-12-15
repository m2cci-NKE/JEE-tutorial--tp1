package com.sdzee.tp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.dao.ClientDao;
import com.sdzee.tp.dao.DAOFactory;
import com.sdzee.tp.forms.CreationClientForm;

@WebServlet( "/CreationClient" )
public class CreationClient extends HttpServlet {
    private static final long  serialVersionUID = 1L;

    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_CLIENT       = "client";
    public static final String ATT_FORM         = "form";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String VUE_SUCCES       = "/WEB-INF/listeClients.jsp";

    public static final String VUE_FORM         = "/WEB-INF/creeClient.jsp";
    public static final String SESSION_CLIENTS  = "clients";
    public static final String CHEMIN        = "chemin";

    private ClientDao clientDao;
    @Override
    public void init() throws ServletException { 
    	
    	this.clientDao= ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClientDAO();
    	
    }
    
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );

    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
    	
    	

    	String chemin = this.getServletConfig().getInitParameter( CHEMIN );
    	
        CreationClientForm form = new CreationClientForm(clientDao);
        Client client = form.creerClient( request,chemin );
        
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_CLIENT, client );

        if ( form.getErreurs().isEmpty() ) {
            HttpSession session = request.getSession();

            Map<Long, Client> mapClient = (HashMap<Long, Client>) session.getAttribute( SESSION_CLIENTS );
            if ( mapClient == null ) {
                mapClient = new HashMap<Long, Client>();
            }
            
            
            
            mapClient.put( client.getId(), client );
            session.setAttribute( SESSION_CLIENTS, mapClient );

            
            //response.sendRedirect( request.getContextPath() + VUE_SUCCES );
           
          
           this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } 

        else {
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }

    }

}
