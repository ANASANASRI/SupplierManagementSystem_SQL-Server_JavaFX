package com.afm.suppliermanagementsystem.model;

import java.util.Date;

public class DocumentPDF {
    private String nomFichier; // Le nom du fichier PDF
    private int nombrePages; // Le nombre de pages du fichier PDF
    private Date dateCreation; // La date de création du fichier PDF
    private String cheminStockage;
    private TypeDocument typeDocument; // Updated to use the TypeDocument enum

    public enum TypeDocument {
        ORDRE_VIREMENT,
        REGLEMENT_ASSURANCE
    }

    public DocumentPDF(String nomFichier, int nombrePages, Date dateCreation, String cheminStockage, TypeDocument typeDocument) {
        this.nomFichier = nomFichier;
        this.nombrePages = nombrePages;
        this.dateCreation = dateCreation;
        this.cheminStockage = cheminStockage;
        this.typeDocument = typeDocument;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public int getNombrePages() {
        return nombrePages;
    }

    public void setNombrePages(int nombrePages) {
        this.nombrePages = nombrePages;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getCheminStockage() {
        return cheminStockage;
    }

    public void setCheminStockage(String cheminStockage) {
        this.cheminStockage = cheminStockage;
    }

    public TypeDocument getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(TypeDocument typeDocument) {
        this.typeDocument = typeDocument;
    }
}
