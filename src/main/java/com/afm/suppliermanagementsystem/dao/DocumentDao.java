package com.afm.suppliermanagementsystem.dao;

import com.afm.suppliermanagementsystem.model.DocumentPDF;

import java.util.List;

public interface DocumentDao {
    void insert(DocumentPDF document);

    void update(DocumentPDF document);

    void deleteById(String id);

    DocumentPDF findById(String id);

    void deleteById(int id);

    abstract DocumentPDF findById(int id);

    List<DocumentPDF> findAll();
}
