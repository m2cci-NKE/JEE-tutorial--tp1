package com.sdzee.tp.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.sdzee.tp.beans.Client;

import eu.medsea.mimeutil.MimeUtil;

public final class CreationClientForm {
    private static final String CHAMP_NOM       = "nomClient";
    private static final String CHAMP_PRENOM    = "prenomClient";
    private static final String CHAMP_ADRESSE   = "adresseClient";
    private static final String CHAMP_TELEPHONE = "telephoneClient";
    private static final String CHAMP_EMAIL     = "emailClient";
    
    
    private static final String CHAMP_IMAGE     = "imageClient";

    private static final int    TAILLE_TAMPON   = 10240;                        // 10ko
    
    private String              resultat;
    private Map<String, String> erreurs         = new HashMap<String, String>();

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Client creerClient( HttpServletRequest request, String chemin)  {
    	
    
    	 Client client = new Client();
    	 
    	 String image = null;
		
			try {
				image = getValidationIamge(request, chemin);
			}  catch ( FormValidationException e ) {
	            setErreur( CHAMP_IMAGE, e.getMessage() );
	        }
		
     	
     	client.setChemin(image);
    		
        String nom = getValeurChamp( request, CHAMP_NOM );
        String prenom = getValeurChamp( request, CHAMP_PRENOM );
        String adresse = getValeurChamp( request, CHAMP_ADRESSE );
        String telephone = getValeurChamp( request, CHAMP_TELEPHONE );
        String email = getValeurChamp( request, CHAMP_EMAIL );

       

        try {
            validationNom( nom );
        } catch ( Exception e ) {
            setErreur( CHAMP_NOM, e.getMessage() );
        }
        client.setNom( nom );

        try {
            validationPrenom( prenom );
        } catch ( Exception e ) {
            setErreur( CHAMP_PRENOM, e.getMessage() );
        }
        client.setPrenom( prenom );

        try {
            validationAdresse( adresse );
        } catch ( Exception e ) {
            setErreur( CHAMP_ADRESSE, e.getMessage() );
        }
        client.setAdresse( adresse );

//        try {
//            validationTelephone( telephone );
//        } catch ( Exception e ) {
//            setErreur( CHAMP_TELEPHONE, e.getMessage() );
//        }
        client.setTelephone( telephone );

        try {
            validationEmail( email );
        } catch ( Exception e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        client.setEmail( email );

        if ( erreurs.isEmpty() ) {
            resultat = "Succès de la création du client.";
        } else {
            resultat = "Échec de la création du client.";
        }

        return client;
    }

    private String getValidationIamge(HttpServletRequest request, String chemin) throws FormValidationException {
    	
    	 String nomFichier = null;
        
    	try {
		Part part = request.getPart(CHAMP_IMAGE);
	
		nomFichier= getNomFichier(part);
		
		if (nomFichier!=null && !nomFichier.isEmpty()) {
			/* Extraction du type MIME du fichier depuis l'InputStream nommé "contenu" */
	    	MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
	    	Collection<?> mimeTypes = MimeUtil.getMimeTypes( part.getInputStream() );
	   
	    	/*
	    	 * Si le fichier est bien une image, alors son en-tête MIME
	    	 * commence par la chaîne "image"
	    	 */
	    	
	    	if ( mimeTypes.toString().startsWith( "image" ) ) {
	    
	    	
	    	    	ecrireFichier( part, nomFichier, chemin );
	    	   
	    	} else {
	    	
	    		throw new FormValidationException("le fichier doit être une image. ");
	    	}
		
		}
    	}catch ( IllegalStateException e ) {
            /*
             * Exception retournée si la taille des données dépasse les limites
             * définies dans la section <multipart-config> de la déclaration de
             * notre servlet d'upload dans le fichier web.xml
             */
            e.printStackTrace();
            throw new FormValidationException( "Le fichier envoyé ne doit pas dépasser 1Mo." );
        } catch ( IOException e ) {
            /*
             * Exception retournée si une erreur au niveau des répertoires de
             * stockage survient (répertoire inexistant, droits d'accès
             * insuffisants, etc.)
             */
            e.printStackTrace();
            throw new FormValidationException( "Erreur de configuration du serveur." );
        } catch ( ServletException e ) {
            /*
             * Exception retournée si la requête n'est pas de type
             * multipart/form-data.
             */
            e.printStackTrace();
            throw new FormValidationException(
                    "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier." );
        }
		
	return nomFichier;
}

private void ecrireFichier(Part part, String nomFichier, String chemin) {
	
	BufferedInputStream entree = null;
	BufferedOutputStream sortie= null;
	
	try {
		entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
		
		sortie= new BufferedOutputStream(new FileOutputStream(new File(chemin+nomFichier)));
		
		
		 byte[] tampon = new byte[TAILLE_TAMPON];
	        int longueur;
	        while ( ( longueur = entree.read( tampon ) ) > 0 ) {
	            sortie.write( tampon, 0, longueur );
	        }
	        
	        
	        
	} catch (IOException e) {
		
		e.printStackTrace();
	} finally {
		try {
			sortie.close();
		}
		catch (IOException ignore) {
		
		} 
		try {
			entree.close();
		}
		catch (IOException ignore) {
		
		} 
	}
	
}


    private String getNomFichier(Part part) { 
    	
		for (String  contentDisposition : part.getHeader("content-disposition").split(";")) {
			if (contentDisposition.trim().startsWith("filename")) {
				return contentDisposition.substring(contentDisposition.indexOf("=")+1).replace("\"", "");
			}
		} 
		return null;
	}
    
    
    
    private void validationNom( String nom ) throws Exception {
        if ( nom != null ) {
            if ( nom.length() < 2 ) {
                throw new Exception( "Le nom d'utilisateur doit contenir au moins 2 caractères." );
            }
        } else {
            throw new Exception( "Merci d'entrer un nom d'utilisateur." );
        }
    }

    private void validationPrenom( String prenom ) throws Exception {
        if ( prenom != null && prenom.length() < 2 ) {
            throw new Exception( "Le prénom d'utilisateur doit contenir au moins 2 caractères." );
        }
    }

    private void validationAdresse( String adresse ) throws Exception {
        if ( adresse != null ) {
            if ( adresse.length() < 10 ) {
                throw new Exception( "L'adresse de livraison doit contenir au moins 10 caractères." );
            }
        } else {
            throw new Exception( "Merci d'entrer une adresse de livraison." );
        }
    }

	/*
	 * private void validationTelephone( String telephone ) throws Exception { if (
	 * telephone != null ) { if ( !telephone.matches( "^\\d+$" ) ) { throw new
	 * Exception( "Le numéro de téléphone doit uniquement contenir des chiffres." );
	 * } else if ( telephone.length() < 4 ) { throw new Exception(
	 * "Le numéro de téléphone doit contenir au moins 4 chiffres." ); } } else {
	 * throw new Exception( "Merci d'entrer un numéro de téléphone." ); } }
	 */
    
    private void validationEmail( String email ) throws Exception {
        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}