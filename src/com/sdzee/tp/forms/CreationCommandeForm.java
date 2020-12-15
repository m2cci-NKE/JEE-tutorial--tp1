package com.sdzee.tp.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.beans.Commande;
import com.sdzee.tp.dao.ClientDao;
import com.sdzee.tp.dao.CommandeDao;

import eu.medsea.mimeutil.MimeUtil;

public class CreationCommandeForm {

    public static final String CHAMP_NOM              = "nomClient";
    public static final String CHAMP_PRENOM           = "prenomClient";
    public static final String CHAMP_ADRESSE          = "adresseClient";
    public static final String CHAMP_TELEPHONE        = "telephoneClient";
    public static final String CHAMP_EMAIL            = "emailClient";

    public static final String CHAMP_DATE             = "dateCommande";
    public static final String CHAMP_MONTANT          = "montantCommande";
    public static final String CHAMP_MODE_PAIEMENT    = "modePaiementCommande";
    public static final String CHAMP_STATUT_PAIEMENT  = "statutPaiementCommande";
    public static final String CHAMP_MODE_LIVRAISON   = "modeLivraisonCommande";
    public static final String CHAMP_STATUT_LIVRAISON = "statutLivraisonCommande";
    public static final String FORMAT_DATE            = "dd/MM/yyyy HH:mm:ss";
    public static final String SESSION_CLIENTS  = "clients";
    
    private static final String CHAMP_CHOIX_CLIENT     = "choixNouveauClient";
    private static final String CHAMP_LISTE_CLIENTS    = "listeClients";
    private static final String ANCIEN_CLIENT          = "ancienClient";
    
      
    private CommandeDao commandeDao;
    private ClientDao clientDao;
    public Map<String, String> erreurs = new HashMap<>();
    String                     resultat;

    public CreationCommandeForm(CommandeDao commandeDao, ClientDao clientDao) {
		this.commandeDao=commandeDao;
		this.clientDao= clientDao;
	}
    

	

	public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Commande creerCommande( HttpServletRequest request, String chemin ) throws Exception {

    	
    	Client client =new Client();
    	
   
        String choixNouveauClient = getValeurChamp( request, CHAMP_CHOIX_CLIENT );
        
        if ( ANCIEN_CLIENT.equals( choixNouveauClient ) ) {
        	
            /* Récupération du nom du client choisi */
            String nomAncienClient = getValeurChamp( request, CHAMP_LISTE_CLIENTS );
          
            HttpSession session = request.getSession();
            client = ( (Map<String, Client>) session.getAttribute( SESSION_CLIENTS ) ).get( nomAncienClient );
        } else {
           
            CreationClientForm clientForm = new CreationClientForm(clientDao);
            client = clientForm.creerClient( request , chemin);
            erreurs = clientForm.getErreurs();
        }
    	

    

      
        /* ****validation Commande***** */
      
        Commande commande = new Commande();
        
        String modePaiement = request.getParameter( CHAMP_MODE_PAIEMENT );
        String statutPaiement = request.getParameter( CHAMP_STATUT_PAIEMENT );
        String modeLivraison = request.getParameter( CHAMP_MODE_LIVRAISON );
        String statutLivraison = request.getParameter( CHAMP_STATUT_LIVRAISON );

		traiterMontant( commande,request);
        traiterModePaiement(modePaiement, commande);
        traiterstatutPaiement(statutPaiement, commande);
        traitermodeLivraison(modeLivraison, commande);
        traiterStatutLivraison(statutLivraison, commande);
        
        DateTime date = new DateTime();
        
  
        commande.setDate( date );
        commande.setClient( client );
        
        
        commande.setModePaiement( modePaiement );
        commande.setStatutPaiement( statutPaiement );
        commande.setModeLivraison( modeLivraison );
        commande.setStatutLivraison( statutLivraison );
        
        if ( erreurs.isEmpty() ) {
        	commandeDao.creer(commande);
            resultat = "Commande créée avec succès !";
        	
        } else {
        	   resultat = "Echec de la création de la commande.";
        }

        /*
         * Création des beans Client et Commande et initialisation avec les
         * données récupérées
         */

       
       

        return commande;

    }

    
	private void traiterStatutLivraison(String statutLivraison, Commande commande) {
		 try {
			  validationStatutLivraison( statutLivraison ); } catch ( Exception e ) {
			  setErreurs( CHAMP_STATUT_LIVRAISON, e.getMessage() ); }
		
	}

	private void traitermodeLivraison(String modeLivraison, Commande commande) {
		try {
			  validationModeLivraison( modeLivraison ); } catch ( Exception e ) {
			  setErreurs( CHAMP_MODE_LIVRAISON, e.getMessage() ); }
		
	}

	private void traiterstatutPaiement(String statutPaiement, Commande commande) {
		try {
			  validationstatutPaiement( statutPaiement ); } catch ( Exception e ) {
			  setErreurs( CHAMP_STATUT_PAIEMENT, e.getMessage() ); }
		
	}

	private void traiterModePaiement(String modePaiement, Commande commande) {
		  try {
			  validationModePaiement( modePaiement ); 
			  } catch 
		  ( Exception e ) 
		  {
		  setErreurs( CHAMP_MODE_PAIEMENT, e.getMessage() ); 
		  }
		
	}

	private void traiterMontant(Commande commande, HttpServletRequest request) {
		double	montant = 0;
		try { 
		   montant = Double.parseDouble( request.getParameter( CHAMP_MONTANT ) );
		    
			validationMontant( montant ); 
		  
		} catch ( Exception e ) 
		
		{ setErreurs(
		  CHAMP_MONTANT, e.getMessage() ); 
		}
		commande.setMontant( montant );
	}

	private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }

    
	/* *************methods ******************** */

    private void setErreurs( String champ, String message ) {
        erreurs.put( champ, message );
    }

    

    private void validationStatutLivraison( String statutLivraison ) throws Exception {
        if ( statutLivraison == null || statutLivraison.trim().length() < 2 ) {
            throw new Exception( "le statut Livraison doit contenir au moins deux caractéres" );
        }

    }

    private void validationModeLivraison( String modeLivraison ) throws Exception {
        if ( modeLivraison == null || modeLivraison.trim().length() < 2 ) {
            throw new Exception( "le mode de Livraison doit contenir au moins deux caractéres" );
        }

    }

    private void validationstatutPaiement( String statutPaiement ) throws Exception {
        if ( statutPaiement.trim().length() < 2 ) {
            throw new Exception( "le statutPaiement doit contenir au moins deux caractéres" );
        }

    }

    private void validationModePaiement( String modePaiement ) throws Exception {

        if ( modePaiement == null || modePaiement.trim().length() < 2 ) {
            throw new Exception( "le mode de Paiement doit contenir au moins deux caractéres" );
        }

    }

    private void validationMontant( double montant ) throws Exception {

        if ( montant == 0.0 || montant <= 0 ) {

            throw new Exception( "le mantant doivent etre positif" );

        }
    }

  
}
