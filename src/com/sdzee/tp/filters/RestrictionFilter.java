package com.sdzee.tp.filters;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.beans.Commande;
import com.sdzee.tp.dao.ClientDao;
import com.sdzee.tp.dao.CommandeDao;
import com.sdzee.tp.dao.DAOFactory;
import com.sdzee.tp.dao.DAOFactory;


public class RestrictionFilter implements Filter {
	
	
	 public static final String SESSION_CLIENTS  = "clients";
	 public static final String SESSION_COMMANDE = "commande";
	 public static final String CONF_DAO_FACTORY = "daofactory";
	    
	 private ClientDao clientDao;
	 private CommandeDao commandeDao;
	 Commande commande;
	 
    public void init( FilterConfig config ) throws ServletException {
    	this.clientDao=((DAOFactory)config.getServletContext().getAttribute(CONF_DAO_FACTORY)).getClientDAO();
    	this.commandeDao= ((DAOFactory)config.getServletContext().getAttribute(CONF_DAO_FACTORY)).getCommandeDAU();
    }

    public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain ) throws IOException,
            ServletException {
    	
    	/* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        
        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        
        
        // client Map
        Map<Long, Client> clients = (HashMap<Long, Client>) session.getAttribute( SESSION_CLIENTS );
        if ( clients == null ) {
        	clients = new HashMap<Long, Client>();
        } 

        
        
        // Commande map
        Map<Long, Commande> commandes = (HashMap<Long, Commande>) session.getAttribute( SESSION_COMMANDE );
        if ( commandes == null ) {
        	commandes = new HashMap<Long, Commande>();
        }
        
        List<Client> clientList = new ArrayList<>();
        List<Commande> commandeList = new ArrayList<>();
        /**
         * Si l'objet utilisateur n'existe pas dans la session en cours, alors
         * l'utilisateur n'est pas connecté.
         */
        if ( session.getAttribute( SESSION_CLIENTS ) == null ) {
        	
        	clientList=clientDao.lister();
        	commandeList=commandeDao.lister();
        	for (Client client : clientList) {
        		clients.put(client.getId(), client);
			}
        	
            session.setAttribute( SESSION_CLIENTS, clients );
            
            for (Commande commande : commandeList) {
            	commandes.put(commande.getId(), commande);
			}
            
            session.setAttribute( SESSION_COMMANDE, commandes );
            
       
        }else {
        	 /* Affichage de la page restreinte */
            chain.doFilter( request, response );
        }
        
        
    }

    public void destroy() {
    }
}

/* convertissons les objets transmis en arguments à notre méthode doFilter(). 
 * La raison en est simple : comme je vous l'ai déjà dit, il n'existe pas de classe fille implémentant 
 * l'interface Filter, alors que côté servlet nous avons bien HttpServlet qui implémente Servlet. 
 * Ce qui signifie que notre filtre n'est pas spécialisé, il implémente uniquement Filter et peut traiter
 *  n'importe quel type de requête et pas seulement les requêtes HTTP. C'est donc pour cela que nous 
 *  devons manuellement spécialiser nos objets, en effectuant un cast vers les objets dédiés 
 *  aux requêtes et réponses HTTP : c'est seulement en procédant à cette conversion que nous 
 *  aurons accès ensuite à la session, qui est propre à l'objet HttpServletRequest, 
 *  et n'existe pas dans l'objet ServletRequest.
 * */
 