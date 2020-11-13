package com.sdzee.tp.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sdzee.tp.beans.Client;

public class CreationClientForm {

    public static final String CHAMP_NOM        = "nomClient";
    public static final String CHAMP_PRENOM     = "prenomClient";
    public static final String CHAMP_ADRESSE    = "adresseClient";
    public static final String CHAMP_TELEPHONE  = "telephoneClient";
    public static final String CHAMP_EMAIL      = "emailClient";
    public static final String SESSION_CLIENTS  = "mapClient";

    


    Map<String, String> erreurs  = new HashMap<>();
    String              resultat = "";

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    /* **************** */

    public Client creerClient( HttpServletRequest request ) {

        Client client = new Client();

        String nom = request.getParameter( CHAMP_NOM );
        String prenom = request.getParameter( CHAMP_PRENOM );
        String adresse = request.getParameter( CHAMP_ADRESSE );
        String telephone = request.getParameter( CHAMP_TELEPHONE );
        String email = request.getParameter( CHAMP_EMAIL );

        try {
            validationNom( nom );
        } catch ( Exception e ) {
            erreurs.put(
                    CHAMP_NOM, e.getMessage() );
        }

        try {
            validationPrenom( prenom );
        } catch ( Exception e ) {
            erreurs.put( CHAMP_PRENOM, e.getMessage() );
        }

        try {
            validationAdresse( adresse );
        } catch ( Exception e ) {
            erreurs.put( CHAMP_ADRESSE, e.getMessage() );
        }

        try {
            validationNumero( telephone );
        } catch ( Exception e ) {
            erreurs.put( CHAMP_TELEPHONE, e.getMessage() );
        }

        client.setNom( nom );
        client.setPrenom( prenom );
        client.setAdresse( adresse );
        client.setTelephone( telephone );
        client.setEmail( email );

        if ( erreurs.isEmpty() ) {

            resultat = "Client créé avec succès !";

        } else {

            resultat = "Echec de la création de la client . ";
        }

        return client;
    }

    /* ************************************* */

    private void validationNom( String nom ) throws Exception {

        if ( nom != null && nom.trim().length() < 2 ) {

            throw new Exception( "Le nom doit contenir plus de 2 caracteres" );
        }

    }

    private void validationPrenom( String prenom ) throws Exception {

        if ( prenom != null && prenom.trim().length() < 2 ) {

            throw new Exception( "Le prenom doit contenir plus de 2 caracteres" );

        }

    }

    private void validationNumero( String telephone ) throws Exception {
        if ( telephone != null && telephone.trim().length() < 4 ) {

            throw new Exception( "Le telephone doit contenir plus de 4 caracteres" );
        }

    }

    private void validationAdresse( String adresse ) throws Exception {

        if ( adresse != null && adresse.trim().length() < 10 ) {

            throw new Exception( "L'adresse doit contenir plus de 10 caracteres" );
        }

    }

}
