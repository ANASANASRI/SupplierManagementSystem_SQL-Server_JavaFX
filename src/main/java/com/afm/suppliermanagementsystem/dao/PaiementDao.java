package com.afm.suppliermanagementsystem.dao;

import com.afm.suppliermanagementsystem.model.Paiement;

import java.util.List;

public interface PaiementDao {
    void insert(Paiement paiement);

    void update(Paiement paiement);

    Paiement deleteById(int id);

    Paiement findById(String id);

    List<Paiement> findAll();

    Paiement findByIdentifiant(String identifiant);

    List<Paiement> findAllIF(int numIF);
}
