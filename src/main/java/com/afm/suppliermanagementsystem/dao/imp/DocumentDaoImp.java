package com.afm.suppliermanagementsystem.dao.imp;

import com.afm.suppliermanagementsystem.dao.DocumentDao;
import com.afm.suppliermanagementsystem.model.DocumentPDF;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentDaoImp implements DocumentDao {

    private final Connection conn;

    public DocumentDaoImp(Connection connection) {
        this.conn = connection;
    }

    @Override
    public void insert(DocumentPDF document) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO document_pdf (nom_fichier, date_creation, chemin_stockage, type_document) VALUES (?, ?, ?, ?)");

            ps.setString(1, document.getNomFichier());
            ps.setDate(2, new java.sql.Date(document.getDateCreation().getTime()));
            ps.setString(3, document.getCheminStockage());
            ps.setString(4, document.getTypeDocument().toString());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting document: " + e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public void update(DocumentPDF document) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE document_pdf SET nom_fichier = ?, date_creation = ?, chemin_stockage = ?, type_document = ? WHERE id = ?");

            ps.setString(1, document.getNomFichier());
            ps.setDate(2, new java.sql.Date(document.getDateCreation().getTime()));
            ps.setString(3, document.getCheminStockage());
            ps.setString(4, document.getTypeDocument().toString());
            ps.setInt(5, document.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating document: " + e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public DocumentPDF findById(String id) {
        return null;
    }

    @Override
    public void deleteById(int id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM document_pdf WHERE id = ?");

            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting document: " + e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public DocumentPDF findById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM document_pdf WHERE id = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                String nomFichier = rs.getString("nom_fichier");
                Date dateCreation = rs.getDate("date_creation");
                String cheminStockage = rs.getString("chemin_stockage");
                String typeDocumentStr = rs.getString("type_document");
                DocumentPDF.TypeDocument typeDocument = DocumentPDF.TypeDocument.valueOf(typeDocumentStr);

                return new DocumentPDF(id, nomFichier, dateCreation, cheminStockage, typeDocument);
            }
        } catch (SQLException e) {
            System.err.println("Error finding document by id: " + e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }

        return null;
    }

    @Override
    public List<DocumentPDF> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM document_pdf");
            rs = ps.executeQuery();

            List<DocumentPDF> documents = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nomFichier = rs.getString("nom_fichier");
                Date dateCreation = rs.getDate("date_creation");
                String cheminStockage = rs.getString("chemin_stockage");
                String typeDocumentStr = rs.getString("type_document");
                DocumentPDF.TypeDocument typeDocument = DocumentPDF.TypeDocument.valueOf(typeDocumentStr);

                DocumentPDF document = new DocumentPDF(id, nomFichier, dateCreation, cheminStockage, typeDocument);
                documents.add(document);
            }

            return documents;
        } catch (SQLException e) {
            System.err.println("Error finding all documents: " + e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }

        return null;
    }

    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing ResultSet: " + e);
            }
        }
    }

    private void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                System.err.println("Error closing PreparedStatement: " + e);
            }
        }
    }
}
