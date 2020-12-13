package com.sdzee.tp.beans;

import org.joda.time.DateTime;

public class Commande {
    /* Propriétés du bean */
	private Long id;
    private Client client;
    private String date;
    private Double montant;
    private String modePaiement;
    private String statutPaiement;
    private String modeLivraison;
    private String statutLivraison;

    public Client getClient() {
        return client;
    }

    public void setClient( Client client ) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date2 ) {
        this.date = date2;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant( Double montant ) {
        this.montant = montant;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement( String modePaiement ) {
        this.modePaiement = modePaiement;
    }

    public String getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement( String statutPaiement ) {
        this.statutPaiement = statutPaiement;
    }

    public String getModeLivraison() {
        return modeLivraison;
    }

    public void setModeLivraison( String modeLivraison ) {
        this.modeLivraison = modeLivraison;
    }

    public String getStatutLivraison() {
        return statutLivraison;
    }

    public void setStatutLivraison( String statutLivraison ) {
        this.statutLivraison = statutLivraison;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}