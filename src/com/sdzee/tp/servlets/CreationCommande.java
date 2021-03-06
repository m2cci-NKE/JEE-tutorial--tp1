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
import com.sdzee.tp.beans.Commande;
import com.sdzee.tp.dao.ClientDao;
import com.sdzee.tp.dao.CommandeDao;
import com.sdzee.tp.dao.DAOFactory;
import com.sdzee.tp.forms.CreationCommandeForm;
import com.sdzee.tp.dao.DAOFactory;
@WebServlet( "/CreationCommande" )
public class CreationCommande extends HttpServlet {

    public static final String ATT_COMMANDE     = "commande";
    public static final String ATT_FORM         = "form";
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String VUE_SUCCES       = "/listeCommandes";
    public static final String VUE_FORM         = "/WEB-INF/creeCommande.jsp";
    public static final String SESSION_COMMANDE = "commande";
    public static final String ATT_CKECK_NAME   = "name";
    public static final String SESSION_CLIENTS  = "clients";
    public static final String CHEMIN        = "chemin";

    private ClientDao clientDao;
    private CommandeDao commandeDao;
    @Override
    public void init() throws ServletException {
    	this.commandeDao= ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getCommandeDAU();
    	this.clientDao= ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClientDAO();
    }
    
   
   
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
    	
    	String chemin = this.getServletConfig().getInitParameter( CHEMIN );
        CreationCommandeForm form = new CreationCommandeForm(commandeDao, clientDao);
        Commande commande = null;
		try {
			commande = form.creerCommande( request,chemin );
		} catch (Exception e) {
			e.printStackTrace();
		} 

        
        
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_COMMANDE, commande );


        if ( form.getErreurs().isEmpty() ) {

            HttpSession session = request.getSession();

            Map<Long, Client> clients = (HashMap<Long, Client>) session.getAttribute( SESSION_CLIENTS );
            if ( clients == null ) {
            	clients = new HashMap<Long, Client>();
            } 

            clients.put(commande.getClient().getId(), commande.getClient());
            
            session.setAttribute( SESSION_CLIENTS, clients );
            
         
 
            /* **Add session Commande** */
            
            Map<Long, Commande> commandes = (HashMap<Long, Commande>) session.getAttribute( SESSION_COMMANDE );
            if ( commandes == null ) {
            	commandes = new HashMap<Long, Commande>();
            }
            
            commandes.put(commande.getId(), commande);
            
            session.setAttribute( SESSION_COMMANDE, commandes );
         
            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        }

        else {

            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );

        }

    }

}