package com.afm.suppliermanagementsystem.services;

import com.afm.suppliermanagementsystem.dao.NoteDao;
import com.afm.suppliermanagementsystem.dao.imp.NoteDaoImp;
import com.afm.suppliermanagementsystem.model.Note;

import java.sql.Connection;
import java.util.List;

public class NoteService {

    private NoteDao noteDao;

    public NoteService(Connection connection) {
        noteDao = new NoteDaoImp(connection);
    }

    public void add(Note note) {
        noteDao.insert(note);
    }

    public void update(Note note) {
        noteDao.update(note);
    }

    public void delete(String id) {
        noteDao.deleteById(id);
    }

    public Note findById(String id) {
        return noteDao.findById(id);
    }

    public List<Note> getAllNotes() {
        return noteDao.findAll();
    }
}
