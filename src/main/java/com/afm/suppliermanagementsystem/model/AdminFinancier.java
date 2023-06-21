package com.afm.suppliermanagementsystem.model;

public class AdminFinancier {
    private String identifiant;
    private String nom;
    private String prenom;

    public AdminFinancier() {
    }

    public AdminFinancier(String identifiantA, String nom, String prenom) {
        this.identifiant = identifiantA;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getIdentifiantA() {
        return identifiant;
    }

    public void setIdentifiantA(String identifiantA) {
        this.identifiant = identifiantA;
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
}
