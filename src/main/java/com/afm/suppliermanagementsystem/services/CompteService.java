package com.afm.suppliermanagementsystem.services;

import com.afm.suppliermanagementsystem.dao.CompteDao;
import com.afm.suppliermanagementsystem.dao.imp.CompteDaoImp;
import com.afm.suppliermanagementsystem.model.Compte;
import com.afm.suppliermanagementsystem.model.Fournisseur;

import java.util.List;

public class CompteService {
    private static CompteDao compteDao = new CompteDaoImp();

    // Other methods and dependencies

    public static Boolean findCompte(String nom, String password) {
        return compteDao.findCompte(nom, password);
    }

    public static boolean findAdmCompte(String nom, String password) {
        return compteDao.findAdmCompte(nom, password);
    }

    public void save(Compte compte) {
        compteDao.insert(compte);
    }


    public void deleteCin(Compte compte) {
        compteDao.deleteByCin(compte.getCin());
    }

    public void update(Compte selectedCompte) {
        compteDao.update(selectedCompte);
    }

    public static List<Compte> findAll() {
        return compteDao.findAll();
    }
}
