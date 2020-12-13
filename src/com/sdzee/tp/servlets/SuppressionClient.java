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
import com.sdzee.tp.dao.ClientDao;
import com.sdzee.tp.dao.ClientDaoImpl;
import com.sdzee.tp.dao.DAOException;
import com.sdzee.tp.dao.DAOFactory;

@WebServlet( "/SuppressionClient" )
public class SuppressionClient extends HttpServlet {
    private static final long  serialVersionUID = 1L;
    private static final String PARAM_ID_CLIENT = "idClient";
    public static final String SESSION_CLIENTS  = "clients";
    public static final String VUE_SUCCES       = "/listeClients";
    public static final String CONF_DAO_FACTORY = "daofactory";
    private ClientDao clientDao;
    @Override
    public void init() throws ServletException { 
    	
    	this.clientDao= ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClientDAO();
    	
    }
    
    

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

    
        HttpSession session = request.getSession();
        
        Long idClient = getValeurParametre(request, PARAM_ID_CLIENT);
        Map<Long, Client> clients = (HashMap<Long, Client>) session.getAttribute( SESSION_CLIENTS );
        
        if (idClient!= null && clients!= null) {
        	
        	try {
        		clients.remove( idClient );
        		clientDao.supprimer(idClient);
             	
			} catch (DAOException e) {
				e.printStackTrace();
			}
        	
			
		}
                
     	
       
        
        session.setAttribute(SESSION_CLIENTS, clients);
        
       // long id = Long.parseLong(keyV.trim());
     
        
        
        
     //   this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        response.sendRedirect(request.getContextPath()+ VUE_SUCCES);
    }

   

	protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

    }
	
	
	
	 private Long getValeurParametre(HttpServletRequest request, String paramIdClient) {
			long id = Long.parseLong(request.getParameter(paramIdClient));
			if (id ==0 ) {
				return (long) 0;
			}
			else {
				return id; 
			}
			
		}

}
