package com.sdzee.tp.beans;

public class Client {
    /* Propriétés du bean */
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;

    public Client( String nom, String prenom, String adresse, String telephone, String email ) {

        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
    }

    public Client( String nom, String prenom, String adresse ) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
    }

    public Client() {

    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom( String prenom ) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setAdresse( String adresse ) {
        this.adresse = adresse;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone( String telephone ) {
        this.telephone = telephone;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return " [nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse + ", telephone=" + telephone
                + ", email=" + email + "]";
    }

}
