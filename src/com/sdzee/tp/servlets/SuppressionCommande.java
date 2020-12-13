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
import com.sdzee.tp.dao.ClientDao;
import com.sdzee.tp.dao.CommandeDao;
import com.sdzee.tp.dao.DAOException;
import com.sdzee.tp.dao.DAOFactory;

@WebServlet( "/SuppressionCommande" )
public class SuppressionCommande extends HttpServlet {
    private static final long  serialVersionUID = 1L;

    public static final String SESSION_COMMANDE = "commande";
    private static final String PARAM_ID_COMMANDE = "idCommande";
    public static final String VUE_SUCCES       = "/WEB-INF/listeCommandes.jsp";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private CommandeDao commandeDao;
    @Override
    public void init() throws ServletException {
    	this.commandeDao= ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getCommandeDAU();
    }
    
    
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        HttpSession session = request.getSession();
     
        
        long idCommande= getParameterCommande(request,PARAM_ID_COMMANDE);
        
        Map<Long, Commande> mapDelete = (HashMap<Long, Commande>) session.getAttribute( SESSION_COMMANDE );
       
        if (idCommande!=0 && mapDelete!=null) {
        	try {
        		mapDelete.remove( idCommande );
            	commandeDao.supprimer(idCommande);
			} catch (DAOException e) {
				e.printStackTrace();
			}
        	
		}
        

        this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );

    }

    private long getParameterCommande(HttpServletRequest request, String paramIdCommande) {
		long id= Long.parseLong(request.getParameter(paramIdCommande));
		
		if (id==0) {
			return 0;
		} else {
			return id;
		}
		
	}


	protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        doGet( request, response );
    }

}
