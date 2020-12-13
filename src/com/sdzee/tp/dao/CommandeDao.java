package com.sdzee.tp.dao;

import java.util.List;

import org.joda.time.DateTime;

import com.sdzee.tp.beans.Commande;

public interface CommandeDao {
	
	void creer(Commande commande);
	Commande trouver (long id);
	List<Commande> lister();
	void supprimer(long id); 

}
