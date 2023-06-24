package com.afm.suppliermanagementsystem.services;

import com.afm.suppliermanagementsystem.dao.PaiementDao;
import com.afm.suppliermanagementsystem.dao.imp.PaiementDaoImp;
import com.afm.suppliermanagementsystem.model.Paiement;

import java.util.List;

public class PaiementService {
    private static PaiementDao paiementDao = new PaiementDaoImp();

    public void save(Paiement paiement) {
        paiementDao.insert(paiement);
    }

    public void update(Paiement paiement) {
        paiementDao.update(paiement);
    }

    public Paiement findByIdentifiant(String identifiant) {
        return paiementDao.findByIdentifiant(identifiant);
    }

    public List<Paiement> findAll() {
        return paiementDao.findAll();
    }
}
