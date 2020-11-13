package com.sdzee.tp.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.beans.Commande;

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

    public Map<String, String> erreurs = new HashMap<>();
    String                     resultat;

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Commande creerCommande( HttpServletRequest request ) {

    	Client client;
        /*
         * Si l'utilisateur choisit un client déjà existant, pas de validation à
         * effectuer
         */
        String choixNouveauClient = getValeurChamp( request, CHAMP_CHOIX_CLIENT );
        
        if ( ANCIEN_CLIENT.equals( choixNouveauClient ) ) {
        	
            /* Récupération du nom du client choisi */
            String nomAncienClient = getValeurChamp( request, CHAMP_LISTE_CLIENTS );
            
            /* Récupération de l'objet client correspondant dans la session */
            
            HttpSession session = request.getSession();
            client = ( (Map<String, Client>) session.getAttribute( SESSION_CLIENTS ) ).get( nomAncienClient );
        } else {
            /*
             * Sinon on garde l'ancien mode, pour la validation des champs.
             * 
             * L'objet métier pour valider la création d'un client existe déjà,
             * il est donc déconseillé de dupliquer ici son contenu ! À la
             * place, il suffit de passer la requête courante à l'objet métier
             * existant et de récupérer l'objet Client créé.
             */
            CreationClientForm clientForm = new CreationClientForm();
            client = clientForm.creerClient( request );

            /*
             * Et très important, il ne faut pas oublier de récupérer le contenu
             * de la map d'erreur créée par l'objet métier CreationClientForm
             * dans la map d'erreurs courante, actuellement vide.
             */
            erreurs = clientForm.getErreurs();
        }
    	

        String date = getDate();

      
        /* ****validation Commande***** */
        double montant = 0;

        try {
            montant = Double.parseDouble( request.getParameter( CHAMP_MONTANT ) );
            validationMontant( montant );
        } catch ( Exception e ) {
            setErreurs( CHAMP_MONTANT, e.getMessage() );
        }

        String modePaiement = request.getParameter( CHAMP_MODE_PAIEMENT );
        String statutPaiement = request.getParameter( CHAMP_STATUT_PAIEMENT );
        String modeLivraison = request.getParameter( CHAMP_MODE_LIVRAISON );
        String statutLivraison = request.getParameter( CHAMP_STATUT_LIVRAISON );

        try {
            validationModePaiement( modePaiement );
        } catch ( Exception e ) {
            setErreurs( CHAMP_MODE_PAIEMENT, e.getMessage() );
        }
        try {
            validationstatutPaiement( statutPaiement );
        } catch ( Exception e ) {
            setErreurs( CHAMP_STATUT_PAIEMENT, e.getMessage() );
        }
        try {
            validationModeLivraison( modeLivraison );
        } catch ( Exception e ) {
            setErreurs( CHAMP_MODE_LIVRAISON, e.getMessage() );
        }
        try {
            validationStatutLivraison( statutLivraison );
        } catch ( Exception e ) {
            setErreurs( CHAMP_STATUT_LIVRAISON, e.getMessage() );
        }

        if ( !erreurs.isEmpty() ) {
            resultat = "Echec de la création de la commande.";

        } else {

            resultat = "Commande créée avec succès !";

        }

        /*
         * Création des beans Client et Commande et initialisation avec les
         * données récupérées
         */

        Commande commande = new Commande();
        commande.setClient( client );
        commande.setDate( date );
        commande.setMontant( montant );
        commande.setModePaiement( modePaiement );
        commande.setStatutPaiement( statutPaiement );
        commande.setModeLivraison( modeLivraison );
        commande.setStatutLivraison( statutLivraison );

        return commande;

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

    private String getDate() {
        /* Récupération de la date courante */
        DateTime dt = new DateTime();
        /* Conversion de la date en String selon le format défini */
        DateTimeFormatter formatter = DateTimeFormat.forPattern( FORMAT_DATE );

        return dt.toString( formatter );
    }
}
