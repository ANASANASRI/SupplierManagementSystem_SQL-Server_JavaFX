package com.afm.suppliermanagementsystem.model;

public class Compte {
    private String nom;
    private String prenom;
    private String telephone;
    private String pseudo_nom;
    private String cin;
    private String mot_pass;



    private String adresse;
    private int etat = 1;


    public Compte(String pseudo_nom, String mot_pass) {
        this.pseudo_nom = pseudo_nom;
        this.mot_pass = mot_pass;
    }

    public Compte() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPseudo_nom() {
        return pseudo_nom;
    }

    public void setPseudo_nom(String pseudo_nom) {
        this.pseudo_nom = pseudo_nom;
    }

    public String getMot_pass() {
        return mot_pass;
    }

    public void setMot_pass(String mot_pass) {
        this.mot_pass = mot_pass;
    }

    public void setCode_adh(String code_adh) {
        cin = code_adh;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setIdentifiant(String identifiant) {
        this.cin = identifiant;
    }

    public String getIdentifiant() {
        return cin;
    }
}
