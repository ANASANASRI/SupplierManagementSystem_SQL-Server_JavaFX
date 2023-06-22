package com.afm.suppliermanagementsystem.dao;

import com.afm.suppliermanagementsystem.model.AdminFinancier;

import java.util.List;

public interface AdminDao {
    void insert(AdminFinancier admin);

    void update(AdminFinancier admin);

    void deleteById(String id);

    AdminFinancier findById(String id);

    List<AdminFinancier> findAll();

    void deleteByCin(String cin);

    AdminFinancier findByCin(String cin);
}
