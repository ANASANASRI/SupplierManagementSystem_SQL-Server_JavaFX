package com.afm.suppliermanagementsystem.model;

public class Fournisseur {

    private String cin;
    private String nom;
    private String adresse;
    private String numeroTelephone;
    private String email;
    private String numeroCompteBancaire;

    public Fournisseur(String cin, String nom, String adresse, String numeroTelephone, String email, String numeroCompteBancaire) {
        this.cin = cin;
        this.nom = nom;
        this.adresse = adresse;
        this.numeroTelephone = numeroTelephone;
        this.email = email;
        this.numeroCompteBancaire = numeroCompteBancaire;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    //get

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public String getEmail() {
        return email;
    }

    public String getNumeroCompteBancaire() {
        return numeroCompteBancaire;
    }

    //set

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumeroCompteBancaire(String numeroCompteBancaire) {
        this.numeroCompteBancaire = numeroCompteBancaire;
    }
}
