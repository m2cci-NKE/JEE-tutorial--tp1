package com.sdzee.tp.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
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
    
    public static final String CHAMP_FICHIER     = "image";
    

    
    public static final int TAILLE_TAMPON = 10240; // 10 ko

    public Map<String, String> erreurs = new HashMap<>();
    String                     resultat;

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Commande creerCommande( HttpServletRequest request, String chemin ) throws Exception {

    	
    	Client client =new Client();
    	
    	
    	
    	try { 
			Part part = request.getPart(CHAMP_FICHIER);
		
			String nomFichier= getNomFichier(part);
			
			if (nomFichier!=null && !nomFichier.isEmpty()) {
				 System.out.println("nomFichier :"+nomFichier);
				 
				String nomChamp= part.getName();
				
				request.setAttribute(nomChamp, nomFichier);
			}
			
			/* Extraction du type MIME du fichier depuis l'InputStream nommé "contenu" */
	    	MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
	    	Collection<?> mimeTypes = MimeUtil.getMimeTypes( part.getInputStream() );
	   
	    	/*
	    	 * Si le fichier est bien une image, alors son en-tête MIME
	    	 * commence par la chaîne "image"
	    	 */
	    	if ( mimeTypes.toString().startsWith( "image" ) ) {
	    	    System.out.println("File is an Image");
	    	    try {
	    	    	ecrireFichier( part, nomFichier, chemin );
	    	    	
	    	    	File file = new File(chemin);
	    	    	String path = file.getAbsolutePath();
	    	    	
	    			client.setChemin(file.getAbsolutePath()); 
	    			
	    			System.out.println("path: "+client.getChemin());
	    			 
				} catch (Exception e) {
					 setErreurs( CHAMP_FICHIER, "Erreur lors de l'écriture du fichier sur le disque." );
				}
	    	    

	    	} else {
	    		System.out.println("File isn't an Image");
	    		throw new Exception("le fichier doit être une image");
	    	}
			
			
	    	
	    	
		
		} catch (IOException | ServletException e1) {
			e1.printStackTrace();
		}
    	
    	
    	
    	
    	
    	
    	
    	
        /*
         * Si l'utilisateur choisit un client déjà existant, pas de validation à
         * effectuer
         */
        String choixNouveauClient = getValeurChamp( request, CHAMP_CHOIX_CLIENT );
        
        if ( ANCIEN_CLIENT.equals( choixNouveauClient ) ) {
        	
            /* Récupération du nom du client choisi */
            String nomAncienClient = getValeurChamp( request, CHAMP_LISTE_CLIENTS );
          
            HttpSession session = request.getSession();
            client = ( (Map<String, Client>) session.getAttribute( SESSION_CLIENTS ) ).get( nomAncienClient );
        } else {
           
            CreationClientForm clientForm = new CreationClientForm();
            client = clientForm.creerClient( request );
            erreurs = clientForm.getErreurs();
        }
    	

        String date = getDate();

      
        /* ****validation Commande***** */
        double montant = 0;

      

        String modePaiement = request.getParameter( CHAMP_MODE_PAIEMENT );
        String statutPaiement = request.getParameter( CHAMP_STATUT_PAIEMENT );
        String modeLivraison = request.getParameter( CHAMP_MODE_LIVRAISON );
        String statutLivraison = request.getParameter( CHAMP_STATUT_LIVRAISON );

		/*
		 * try { montant = Double.parseDouble( request.getParameter( CHAMP_MONTANT ) );
		 * validationMontant( montant ); } catch ( Exception e ) { setErreurs(
		 * CHAMP_MONTANT, e.getMessage() ); }
		 * 
		 * try { validationModePaiement( modePaiement ); } catch ( Exception e ) {
		 * setErreurs( CHAMP_MODE_PAIEMENT, e.getMessage() ); } try {
		 * validationstatutPaiement( statutPaiement ); } catch ( Exception e ) {
		 * setErreurs( CHAMP_STATUT_PAIEMENT, e.getMessage() ); } try {
		 * validationModeLivraison( modeLivraison ); } catch ( Exception e ) {
		 * setErreurs( CHAMP_MODE_LIVRAISON, e.getMessage() ); } try {
		 * validationStatutLivraison( statutLivraison ); } catch ( Exception e ) {
		 * setErreurs( CHAMP_STATUT_LIVRAISON, e.getMessage() ); }
		 */

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
