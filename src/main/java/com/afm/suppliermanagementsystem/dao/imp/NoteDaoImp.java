package com.afm.suppliermanagementsystem.dao.imp;

import com.afm.suppliermanagementsystem.dao.NoteDao;
import com.afm.suppliermanagementsystem.model.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoImp implements NoteDao {

    private final Connection connection;

    public NoteDaoImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Note note) {
        String sql = "INSERT INTO note (title, note, dateTime, date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, note.getTitle());
            ps.setString(2, note.getNote());
            ps.setString(3, note.getDateTime());
            ps.setDate(4, java.sql.Date.valueOf(note.getDate()));

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to insert note: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(Note note) {
        String sql = "UPDATE note SET title = ?, note = ?, dateTime = ?, date = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, note.getTitle());
            ps.setString(2, note.getNote());
            ps.setString(3, note.getDateTime());
            ps.setDate(4, java.sql.Date.valueOf(note.getDate()));
            ps.setString(5, note.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to update note: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM note WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete note: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Note findById(String id) {
        String sql = "SELECT * FROM note WHERE id = ?";
        Note note = null;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("title");
                    String noteText = rs.getString("note");
                    String dateTime = rs.getString("dateTime");
                    Date date = rs.getDate("date");

                    note = new Note(title, noteText, id, date.toLocalDate());
                    note.setDateTime(dateTime);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to find note by id: " + e.getMessage());
            e.printStackTrace();
        }

        return note;
    }

    @Override
    public List<Note> findAll() {
        String sql = "SELECT * FROM note";
        List<Note> notes = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String id = rs.getString("id");
                String title = rs.getString("title");
                String noteText = rs.getString("note");
                String dateTime = rs.getString("dateTime");
                Date date = rs.getDate("date");

                Note note = new Note(title, noteText, id, date.toLocalDate());
                note.setDateTime(dateTime);
                notes.add(note);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve notes: " + e.getMessage());
            e.printStackTrace();
        }

        return notes;
    }

    @Override
    public Note findByIdentifiant(String identifiant) {
        return null;
    }

    @Override
    public List<Note> findAllIF(int numIF) {
        return null;
    }

    // Other methods (findByIdentifiant, findAllIF) can be implemented if needed

    // Helper method to close resources
    private void closeResource(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                System.err.println("Failed to close resource: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
