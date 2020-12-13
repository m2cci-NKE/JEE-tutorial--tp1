package com.sdzee.tp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static com.sdzee.tp.dao.DAOUtilitaire.*;

import org.joda.time.DateTime;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.beans.Commande;



public class CommandeDaoImpl implements CommandeDao{

	ResultSet valeursAutoGenerees;
	
	private static final String SQL_SELECT        = "SELECT id, id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison FROM Commande ORDER BY id";
	private static final String SQL_SELECT_PAR_ID = "SELECT id, id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison FROM Commande WHERE id = ?";
	private static final String SQL_INSERT        = "INSERT INTO Commande (id_client, date, montant, mode_paiement, statut_paiement, "
			+ "mode_livraison, statut_livraison) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_PAR_ID = "DELETE FROM Commande WHERE id = ?";
	
	private Commande commande;
	DAOFactory daoFactory;
	
public CommandeDaoImpl(DAOFactory daoFactory) {
		this.daoFactory= daoFactory;
	}

private static Commande map(ResultSet resultSet) throws SQLException {
		
		Commande commande = new Commande();
		Client client;
		commande.setId(resultSet.getLong("id"));
		commande.getClient().setId(resultSet.getLong("id_client")); 
		commande.setDate( resultSet.getString("date"));
		commande.setMontant(resultSet.getDouble("montant")); 
		commande.setModePaiement(resultSet.getString("modePaiement"));
		commande.setStatutPaiement(resultSet.getString("statutPaiement"));
		commande.setModeLivraison(resultSet.getString("modeLivraison"));
		commande.setStatutLivraison(resultSet.getString("statutLivraison"));
		
		return commande;
	}
	
	@Override
	public void creer(Commande commande) {
		 Connection connexion = null;
		  PreparedStatement preparedStatement = null;
		  
		  try {
			  
			connexion= daoFactory.getConnxion();
			System.out.println("I'm insert commande in DB");
			preparedStatement= initialisationRequetePreparee(connexion, SQL_INSERT, true, commande.getClient(), commande.getDate(),
					  commande.getMontant(), commande.getModePaiement(), commande.getStatutPaiement(),
					  commande.getModeLivraison(), commande.getStatutLivraison());
			int status= preparedStatement.executeUpdate();
			if (status ==0) {
				throw new DAOException( "Échec de la création du Clinet, aucune ligne ajoutée dans la table." );
				
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
	         valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            commande.setId( valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DAOException( "Échec de la création de la commande en base, aucun ID auto-généré retourné." );
	        }
		}catch (SQLException e) {
			throw new DAOException(e);
		}finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}

	@Override
	public Commande trouver(long id) {
		Connection connexion = null;
		  PreparedStatement preparedStatement = null;
		  ResultSet resultSet = null;
		  
		  try {
			  connexion= daoFactory.getConnxion();
			preparedStatement=initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, false, id);
			resultSet= preparedStatement.executeQuery();
			if (resultSet.next()) {
				commande= map(resultSet);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
		return commande;
	}

	@Override
	public List<Commande> lister() {
		Connection connexion = null;
		  PreparedStatement preparedStatement = null;
		  ResultSet resultSet = null;
		  List<Commande> commande = new ArrayList<>();
		  try {
			  connexion= daoFactory.getConnxion();
			preparedStatement=connexion.prepareStatement(SQL_SELECT);
			preparedStatement.executeQuery();
			
			while  (resultSet.next()) {
				commande.add(map(resultSet));
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
		  
		  
		return commande;
	}

	@Override
	public void supprimer(long id) {
		
		Connection connexion = null;
		  PreparedStatement preparedStatement = null;
		  ResultSet resultSet = null;
		  try {
			
			connexion= daoFactory.getConnxion();
			preparedStatement=initialisationRequetePreparee(connexion, SQL_DELETE_PAR_ID, false, id);
			int statut=preparedStatement.executeUpdate();
			if (statut==0) {
				throw new DAOException("Echec de suppression de la commande, aucune ligne supprimer de la table");
			} 
			
		} catch (SQLException e) {
			throw new DAOException(e);
			
		}finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
		  
		
	}

}
