package com.afm.suppliermanagementsystem.model;

import java.util.Date;

public class Paiement {
    private int identifiant; // L'identifiant unique du paiement
    private double montant; // Le montant du paiement
    private String devise; // La devise dans laquelle le paiement est effectué
    private Date date; // La date du paiement
    private boolean effectue; // Indique si le paiement a été effectué ou non
    private MoyenPaiement moyenPaiement; // Updated to use the MoyenPaiement enum
    private int numIF; // Foreign key referencing the numIF of the associated Fournisseur

    public enum MoyenPaiement {
        CHEQUE,
        VIREMENT
    }

    public Paiement() {}

    public Paiement(int identifiant, double montant, String devise, Date date, boolean effectue, MoyenPaiement moyenPaiement) {
        this.identifiant = identifiant;
        this.montant = montant;
        this.devise = devise;
        this.date = date;
        this.effectue = effectue;
        this.moyenPaiement = moyenPaiement;
    }

    public Paiement(int identifiant, double montant, String devise, Date date, boolean effectue, MoyenPaiement moyenPaiement, int numIF) {
        this.identifiant = identifiant;
        this.montant = montant;
        this.devise = devise;
        this.date = date;
        this.effectue = effectue;
        this.moyenPaiement = moyenPaiement;
        this.numIF = numIF;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
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

    public int getNumIF() {
        return numIF;
    }

    public void setNumIF(int numIF) {
        this.numIF = numIF;
    }
}