package com.afm.suppliermanagementsystem.dao;

import com.afm.suppliermanagementsystem.model.Compte;

import java.util.List;

public interface CompteDao {
    void insert(Compte compte);

    void update(Compte compte);

    void deleteById(String id);

    Compte findById(String id);

    List<Compte> findAll();

    void deleteById(Integer id);

    boolean findCompte(String nom, String password);
}
