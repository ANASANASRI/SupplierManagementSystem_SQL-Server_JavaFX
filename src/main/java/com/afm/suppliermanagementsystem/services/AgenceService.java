package com.afm.suppliermanagementsystem.services;

import com.afm.suppliermanagementsystem.dao.AgenceDao;
import com.afm.suppliermanagementsystem.dao.imp.AgenceDaoImp;
import com.afm.suppliermanagementsystem.model.Agence;

import java.util.List;

public class AgenceService {

    private static AgenceDao agenceDao = new AgenceDaoImp() ;
    public static List<Agence> findAll() {
        return agenceDao.findAll();
    }

}