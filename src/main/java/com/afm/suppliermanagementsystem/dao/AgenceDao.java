package com.afm.suppliermanagementsystem.dao;

import com.afm.suppliermanagementsystem.model.Agence;

import java.util.List;

public interface AgenceDao {
    List<Agence> findAll();
}