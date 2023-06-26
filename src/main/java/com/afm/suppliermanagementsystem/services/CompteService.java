package com.afm.suppliermanagementsystem.services;

import com.afm.suppliermanagementsystem.dao.CompteDao;
import com.afm.suppliermanagementsystem.dao.imp.CompteDaoImp;
import com.afm.suppliermanagementsystem.model.Compte;
import com.afm.suppliermanagementsystem.model.Fournisseur;

public class CompteService {
    private static CompteDao compteDao = new CompteDaoImp();

    // Other methods and dependencies

    public static Boolean findCompte(String nom, String password) {
        return compteDao.findCompte(nom, password);
    }

    public void save(Compte compte) {
        compteDao.insert(compte);
    }


}
