package com.afm.suppliermanagementsystem.model;

import java.time.LocalDate;

public class Note {
    private String title;
    private String note;
    private String dateTime;
    private String id;
    private LocalDate date;

    public Note(String title, String note, String id, LocalDate date) {
        this.title = title;
        this.note = note;
        this.id = id;
        this.date = date;
    }

    // Getter and setter for date
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}