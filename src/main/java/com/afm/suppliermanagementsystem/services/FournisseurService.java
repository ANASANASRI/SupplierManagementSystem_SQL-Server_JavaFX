package com.afm.suppliermanagementsystem.services;

import com.afm.suppliermanagementsystem.dao.FournisseurDao;
import com.afm.suppliermanagementsystem.dao.imp.FournisseurDaoImp;
import com.afm.suppliermanagementsystem.model.Fournisseur;

import java.util.List;

public class FournisseurService {
    private static FournisseurDao fournisseurDao= new FournisseurDaoImp();

    public void save(Fournisseur fournisseur) {
        fournisseurDao.insert(fournisseur);
    }

    public void update(Fournisseur fournisseur) {
        fournisseurDao.update(fournisseur);
    }


    public static void remove(Fournisseur fournisseur) {
        fournisseurDao.deleteBynumIF(fournisseur.getNumIF());
    }

    public Fournisseur findBynumIF(int numIF) {
        return fournisseurDao.findBynumIF(numIF);
    }

    public List<Fournisseur> findAll() {
        return fournisseurDao.findAll();
    }
}
