package com.afm.suppliermanagementsystem.model;

import java.util.Date;

public class Paiement {
    private int identifiant;
    private double montant;
    private String devise;
    private Date date;
    private boolean effectue;
    private MoyenPaiement moyenPaiement;
    private int numIF;

    public enum MoyenPaiement {
        CHEQUE,
        VIREMENT
    }

    private String agence;
    private String libelle;
    private String numCheque;



    public Paiement() {}

    public Paiement(int identifiant, double montant, String devise, Date date, boolean effectue, MoyenPaiement moyenPaiement, int numIF, String agence, String libelle, String numCheque) {
        this.identifiant = identifiant;
        this.montant = montant;
        this.devise = devise;
        this.date = date;
        this.effectue = effectue;
        this.moyenPaiement = moyenPaiement;
        this.numIF = numIF;
        this.agence = agence;
        this.libelle = libelle;
        this.numCheque = numCheque;
    }

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

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
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