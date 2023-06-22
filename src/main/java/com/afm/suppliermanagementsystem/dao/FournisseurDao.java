package com.afm.suppliermanagementsystem.dao;

import com.afm.suppliermanagementsystem.model.Fournisseur;

import java.util.List;

public interface FournisseurDao {
    void insert(Fournisseur fournisseur);

    void update(Fournisseur fournisseur);

    void deleteByCin(String cin);

    Fournisseur findByCin(String cin);

    List<Fournisseur> findAll();
}
