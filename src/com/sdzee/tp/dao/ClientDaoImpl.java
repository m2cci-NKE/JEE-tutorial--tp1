package com.sdzee.tp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static com.sdzee.tp.dao.DAOUtilitaire.*;
import com.sdzee.tp.beans.Client;

public class ClientDaoImpl implements ClientDao{
	
	private DAOFactory daoFactory;
	private DAOUtilitaire DAOutilitaire;
	
	 ResultSet valeursAutoGenerees;
	public ClientDaoImpl(DAOFactory daoFactory) {
		this.daoFactory=daoFactory;
	}
	
	
	
	  Client client = null;
	  
	private static Client map(ResultSet resultSet) throws SQLException {
		
		Client client = new Client();
		
		client.setId(resultSet.getLong("id"));
		client.setNom(resultSet.getString("nom"));
		client.setPrenom(resultSet.getString("prenom"));
		client.setAdresse(resultSet.getString("adresse"));
		client.setTelephone(resultSet.getString("telephone"));
		client.setEmail(resultSet.getString("email"));
		client.setImage(resultSet.getString("image"));
		
		return client; 
	}
	
	private static final String SQL_SELECT        = "SELECT id, nom, prenom, adresse, telephone, email, image "
			+ "										 FROM Client "
			+ "										ORDER BY id";
	private static final String SQL_SELECT_PAR_ID = "SELECT id, nom, prenom, adresse, telephone, email, image "
			+ "										FROM Client WHERE "
			+ "										id = ?";
	private static final String SQL_INSERT        = "INSERT INTO Client (nom, prenom, adresse, telephone, email, image) "
			+ "										VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_PAR_ID = "DELETE FROM Client WHERE id = ?";
	
	@Override
	public Client trouver(Long id) throws SQLException {
		 Connection connexion = null;
		  PreparedStatement preparedStatement = null;
		  ResultSet resultSet = null;
		try {
			connexion=  daoFactory.getConnxion();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, false, id);
			resultSet= preparedStatement.executeQuery();
			if (resultSet.next()) {
				client= map(resultSet);
			}
			
			
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    }finally {
	    	fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
		
		return client;
	}
	
	@Override
	public void creer(Client client) throws SQLException, DAOException {
		 Connection connexion = null;
		  PreparedStatement preparedStatement = null;
		  ResultSet resultSet = null;
		try {
			connexion=  daoFactory.getConnxion();
			preparedStatement= initialisationRequetePreparee(connexion, SQL_INSERT, true, client.getNom(),
					client.getPrenom(), client.getAdresse(), client.getTelephone(), client.getEmail(), client.getImage());
			int status= preparedStatement.executeUpdate();
			if (status ==0) {
				throw new DAOException( "Échec de la création du Clinet, aucune ligne ajoutée dans la table." );
				
			}
			
			/* Récupération de l'id auto-généré par la requête d'insertion */
	         valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            client.setId( valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DAOException( "Échec de la création du client en base, aucun ID auto-généré retourné." );
	        }
		}catch (SQLException e) {
			throw new DAOException(e);
		}finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
		
        
		
	}


	@Override
	public List<Client> lister() {
		Connection connexion = null;
		  PreparedStatement preparedStatement = null;
		  ResultSet resultSet = null;
		  List<Client> clients = new ArrayList<>();
		  
		  try {
			connexion=  daoFactory.getConnxion();
			preparedStatement = connexion.prepareStatement(SQL_SELECT);
			resultSet= preparedStatement.executeQuery();
			while  (resultSet.next()) {
				clients.add(map(resultSet));
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
		return clients;
	}

	@Override
	public void supprimer(long id) {
		Connection connexion = null;
		  PreparedStatement preparedStatement = null;
		  ResultSet resultSet = null;
		  try {
			connexion=  daoFactory.getConnxion();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE_PAR_ID, false, id);
			int statut= preparedStatement.executeUpdate();
			if (statut==0) {
				throw new DAOException("Echec de suppression du client, aucune ligne supprimer de la table");
			} 
			
		} catch (SQLException e) {
			throw new DAOException(e);
			
		}finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
		  
		  
	}
	
	
	

}
