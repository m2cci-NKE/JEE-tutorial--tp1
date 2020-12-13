package com.sdzee.tp.dao;

import java.sql.SQLException;
import java.util.List;

import com.sdzee.tp.beans.Client;


public interface ClientDao {
	
	void creer(Client client) throws SQLException, DAOException;
	
	List<Client> lister();
	
	Client trouver(Long id) throws SQLException;

	void supprimer(long id);
}
