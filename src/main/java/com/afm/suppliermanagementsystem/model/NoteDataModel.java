package com.afm.suppliermanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

public class NoteDataModel {
    private static final NoteDataModel instance = new NoteDataModel();
    private List<Note> notes;

    private NoteDataModel() {
        notes = new ArrayList<>();
    }

    public static NoteDataModel getInstance() {
        return instance;
    }

    public List<Note> getNotes() {
        return notes;
    }
}
