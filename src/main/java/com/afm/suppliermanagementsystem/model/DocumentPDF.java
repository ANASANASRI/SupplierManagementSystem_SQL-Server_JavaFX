package com.afm.suppliermanagementsystem.model;

import java.util.Date;

public class DocumentPDF {
    private int id;
    private String nomFichier; // Le nom du fichier PDF
    private Date dateCreation; // La date de création du fichier PDF
    private String cheminStockage;
    private TypeDocument typeDocument; // Updated to use the TypeDocument enum

    public enum TypeDocument {
        ORDRE_VIREMENT,
        REGLEMENT_ASSURANCE
    }

    public DocumentPDF(int id, String nomFichier, Date dateCreation, String cheminStockage, TypeDocument typeDocument) {
        this.id = id;
        this.nomFichier = nomFichier;
        this.dateCreation = dateCreation;
        this.cheminStockage = cheminStockage;
        this.typeDocument = typeDocument;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DocumentPDF(String nomFichier, Date dateCreation, String cheminStockage, TypeDocument typeDocument) {
        this.nomFichier = nomFichier;
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
