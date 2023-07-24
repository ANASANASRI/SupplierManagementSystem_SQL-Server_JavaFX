package com.afm.suppliermanagementsystem.dao;

import com.afm.suppliermanagementsystem.model.Note;

import java.util.List;

public interface NoteDao {
    void insert(Note note);

    void update(Note note);

    void deleteById(String id);

    Note findById(String id);

    List<Note> findAll();

    Note findByIdentifiant(String identifiant);

    List<Note> findAllIF(int numIF);
}
