package com.afm.suppliermanagementsystem.model;

import java.util.Date;

public class Paiement {
    private String identifiant; // L'identifiant unique du paiement
    private double montant; // Le montant du paiement
    private String devise; // La devise dans laquelle le paiement est effectué
    private Date date; // La date du paiement
    private boolean effectue; // Indique si le paiement a été effectué ou non
    private MoyenPaiement moyenPaiement; // Updated to use the MoyenPaiement enum

    public enum MoyenPaiement {
        CHEQUE,
        VIREMENT
    }

    public Paiement(String identifiant, double montant, String devise, Date date, boolean effectue, MoyenPaiement moyenPaiement) {
        this.identifiant = identifiant;
        this.montant = montant;
        this.devise = devise;
        this.date = date;
        this.effectue = effectue;
        this.moyenPaiement = moyenPaiement;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isEffectue() {
        return effectue;
    }

    public void setEffectue(boolean effectue) {
        this.effectue = effectue;
    }

    public MoyenPaiement getMoyenPaiement() {
        return moyenPaiement;
    }

    public void setMoyenPaiement(MoyenPaiement moyenPaiement) {
        this.moyenPaiement = moyenPaiement;
    }
}
