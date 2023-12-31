package com.afm.suppliermanagementsystem.model;

public class Fournisseur {

    private int numIF;
    private String nom;
    private String adresse;
    private String numeroTelephone;
    private String email;
    private String numeroCompteBancaire;

    public Fournisseur(int numIF, String nom, String adresse, String numeroTelephone, String email, String numeroCompteBancaire) {
        this.numIF = numIF;
        this.nom = nom;
        this.adresse = adresse;
        this.numeroTelephone = numeroTelephone;
        this.email = email;
        this.numeroCompteBancaire = numeroCompteBancaire;
    }

    public Fournisseur() {

    }

    public int getNumIF() {
        return numIF;
    }

    public void setNumIF(int numIF) {
        this.numIF = numIF;
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

    @Override
    public String toString() {
        return "Fournisseur{" +
                "numIF='" + numIF + '\'' +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", numeroTelephone='" + numeroTelephone + '\'' +
                ", email='" + email + '\'' +
                ", numeroCompteBancaire='" + numeroCompteBancaire + '\'' +
                '}';
    }
}
