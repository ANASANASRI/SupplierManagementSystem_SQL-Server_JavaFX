package com.afm.suppliermanagementsystem.services;

import com.afm.suppliermanagementsystem.dao.CompteDao;
import com.afm.suppliermanagementsystem.dao.imp.CompteDaoImp;

public class CompteService {
    private static CompteDao compteDao = new CompteDaoImp();

    // Other methods and dependencies

    public static Boolean findCompte(String nom, String password) {
        return compteDao.findCompte(nom, password);
    }

}
