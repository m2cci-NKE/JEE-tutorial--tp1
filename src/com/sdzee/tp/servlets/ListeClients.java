package com.sdzee.tp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.dao.ClientDao;
import com.sdzee.tp.dao.DAOFactory;

/**
 * Servlet implementation class ListeClients
 */
@WebServlet( "/ListeClients" )
public class ListeClients extends HttpServlet {
    private static final long  serialVersionUID = 1L;

    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String VUE_SUCCES       = "/WEB-INF/listeClients.jsp";
    private ClientDao clientDao;

    @Override
    public void init() throws ServletException { 
    	
    	this.clientDao= ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClientDAO();
    	
    }
    
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

    	List<Client> clients = new ArrayList<>();
        clients= clientDao.lister();
        if (clients== null) {
			System.out.println("list is null");
		} else {
			
			request.setAttribute("list", clients);
		}
        
        this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

      

    }

}
