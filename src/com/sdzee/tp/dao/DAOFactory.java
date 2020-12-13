package com.sdzee.tp.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
	
	private static final String FICHIER_PROPREITES="/com/sdzee/tp/dao/dao.properties";
	private static final String PROPERY_URL = "url";
	private static final String PROPERY_DRIVER = "driver";
	private static final String PROPERY_USER = "nomutilisateur";
	private static final String PROPERTY_MOT_DE_PASSE = "motdepasse";

	
	private String url;
	private String user;
	private String motDePasse;
	
	public DAOFactory(String url, String user, String motDePasse) {
		this.url=url;
		this.user=user;
		this.motDePasse=motDePasse;
	}



	public static DAOFactory getInstance() throws DAOConfigurationException {
		Properties properties = new Properties();
		
		String url;
		String driver;
		String user;
		String motDePasse;
		
		ClassLoader classLoader= Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(FICHIER_PROPREITES);
		
		if (fichierProperties==null) {
			throw new DAOConfigurationException("le fichier"+fichierProperties+"est introuvable;");
		}
		
		try { 
			properties.load(fichierProperties);
			
			url = properties.getProperty(PROPERY_URL);
			driver = properties.getProperty(PROPERY_DRIVER);
			user= properties.getProperty(PROPERY_USER);
			motDePasse= properties.getProperty(PROPERTY_MOT_DE_PASSE);
			
		} catch (IOException e) {
			
			throw new DAOConfigurationException("impossible de charger le fichier properties"+fichierProperties, e);
			
		}
		
		// chargement du driver
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DAOConfigurationException("driver est introuvable dans la classpath");
		}
		
		DAOFactory daofactory = new DAOFactory(url, user, motDePasse);
		
		return daofactory;
	}
	
	
	// cree une connexion a bdd
	
	Connection getConnxion() throws SQLException {
		return DriverManager.getConnection(url, user, motDePasse);
	}
	
	// instancier l'objet qui va communiquer avec la bdd 
	
	public ClientDao getClientDAO(){
		return new ClientDaoImpl(this); 
		
	}
	
	public CommandeDao getCommandeDAU() {
		return new CommandeDaoImpl(this); 
	}
	
	
}
