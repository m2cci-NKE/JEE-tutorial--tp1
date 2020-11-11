package com.sdzee.tp.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
    public static final String SESSION_COMMANDE       = "mapCommande";

    String                     find                   = null;

    public String getFind() {
        return find;
    }

    public Map<String, String> erreurs = new HashMap<>();
    String                     resultat;

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Commande commandeForm( HttpServletRequest request ) {

        String nom = request.getParameter( CHAMP_NOM );
        String prenom = request.getParameter( CHAMP_PRENOM );
        String adresse = request.getParameter( CHAMP_ADRESSE );
        String telephone = request.getParameter( CHAMP_TELEPHONE );
        String email = request.getParameter( CHAMP_EMAIL );

        String date = getDate();

        String checkName = request.getParameter( "newClient" );

        if ( !( checkName == null ) ) {
            find = "client find in Map !";
        } else {
            find = "client not find in map !";

            try {
                validationNomPrenom( nom );
            } catch ( Exception e ) {
                setErreurs( CHAMP_NOM, e.getMessage() );

            }

            try {
                validationNomPrenom( prenom );
            } catch ( Exception e ) {
                setErreurs( CHAMP_PRENOM, e.getMessage() );

            }
            try {
                validationAdresse( adresse );
            } catch ( Exception e ) {
                setErreurs( CHAMP_ADRESSE, e.getMessage() );
            }
            try {
                validationTelephone( telephone );
            } catch ( Exception e ) {
                setErreurs( CHAMP_TELEPHONE, e.getMessage() );
            }
            try {
                validationEmail( email );
            } catch ( Exception e ) {

                setErreurs( CHAMP_EMAIL, e.getMessage() );

            }
        }
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
        Client client = new Client();
        client.setNom( nom );
        client.setPrenom( prenom );
        client.setAdresse( adresse );
        client.setTelephone( telephone );
        client.setEmail( email );

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

    /* *************methods ******************** */

    private void setErreurs( String champ, String message ) {
        erreurs.put( champ, message );
    }

    private void validationNomPrenom( String champs ) throws Exception {
        if ( champs == null || champs.trim().length() < 2 ) {
            throw new Exception( "le champs doit contenir au moins deux caractéres" );
        }
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

    private void validationEmail( String email ) throws Exception {

        if ( email != null ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new Exception( "Merci de saisir une adresse mail valide." );
            }
        } else {
            throw new Exception( "Merci de saisir une adresse mail." );
        }

    }

    private void validationTelephone( String telephone ) throws Exception {

        try {
            int num = Integer.parseInt( telephone );
        } catch ( NumberFormatException e ) {
            throw new Exception( "Le numero de telephone doit contenir uniquement des chiffres" );
        }
    }

    private void validationAdresse( String adresse ) throws Exception {
        if ( adresse == null || adresse.trim().length() < 10 ) {

            throw new Exception( "l'adresse de laivraison doit contenir au moins 10 caractéres" );
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
