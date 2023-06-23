package com.afm.suppliermanagementsystem.dao;

import com.afm.suppliermanagementsystem.model.Fournisseur;

import java.util.List;

public interface FournisseurDao {
    void insert(Fournisseur fournisseur);

    void update(Fournisseur fournisseur);

    Fournisseur findBynumSIREN(int numSIREN);

    void deleteBynumSIREN(int numSIREN);

    List<Fournisseur> findAll();
}
