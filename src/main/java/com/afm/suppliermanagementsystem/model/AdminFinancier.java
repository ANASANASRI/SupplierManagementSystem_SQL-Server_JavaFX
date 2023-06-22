package com.afm.suppliermanagementsystem.model;

public class AdminFinancier {
    private String cin;
    private String nom;
    private String prenom;

    public AdminFinancier() {
    }

    public AdminFinancier(String cin, String nom, String prenom) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getIdentifiantA() {
        return cin;
    }

    public void setIdentifiantA(String identifiantA) {
        this.cin = identifiantA;
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
