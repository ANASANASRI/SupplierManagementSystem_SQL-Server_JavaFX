package com.afm.suppliermanagementsystem.services;

import com.afm.suppliermanagementsystem.dao.PaiementDao;
import com.afm.suppliermanagementsystem.dao.imp.PaiementDaoImp;
import com.afm.suppliermanagementsystem.model.Paiement;

import java.util.List;

public class PaiementService {
    private static PaiementDao paiementDao = new PaiementDaoImp();

    public static void save(Paiement paiement) {
        paiementDao.insert(paiement);
    }

    public static Paiement remove(int Id) {
        return paiementDao.deleteById(Id);
    }

    public void update(Paiement paiement) {
        paiementDao.update(paiement);
    }

    public Paiement findByIdentifiant(String identifiant) {
        return paiementDao.findByIdentifiant(identifiant);
    }

    public static List<Paiement> findAll() {
        return paiementDao.findAll();
    }

    public static List<Paiement> findAllIF(int numIF) {
        return paiementDao.findAllIF(numIF);
    }

}
