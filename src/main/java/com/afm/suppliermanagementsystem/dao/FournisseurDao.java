package com.afm.suppliermanagementsystem.dao;

import com.afm.suppliermanagementsystem.model.Fournisseur;

import java.util.List;

public interface FournisseurDao {
    void insert(Fournisseur fournisseur);

    void update(Fournisseur fournisseur);

    Fournisseur findBynumIF(int numIF);

    void deleteBynumIF(int numIF);

    List<Fournisseur> findAll();
}
