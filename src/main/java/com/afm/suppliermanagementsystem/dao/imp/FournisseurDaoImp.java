package com.afm.suppliermanagementsystem.dao.imp;

import com.afm.suppliermanagementsystem.dao.FournisseurDao;
import com.afm.suppliermanagementsystem.model.Fournisseur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FournisseurDaoImp implements FournisseurDao {

    private final Connection conn;

    public FournisseurDaoImp(Connection connection) {
        this.conn = connection;
    }

    @Override
    public void insert(Fournisseur fournisseur) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO fournisseur (cin, nom, adresse, numero_telephone, email, numero_compte_bancaire) VALUES (?, ?, ?, ?, ?, ?)");

            ps.setString(1, fournisseur.getCin());
            ps.setString(2, fournisseur.getNom());
            ps.setString(3, fournisseur.getAdresse());
            ps.setString(4, fournisseur.getNumeroTelephone());
            ps.setString(5, fournisseur.getEmail());
            ps.setString(6, fournisseur.getNumeroCompteBancaire());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting fournisseur: " + e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public void update(Fournisseur fournisseur) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE fournisseur SET nom = ?, adresse = ?, numero_telephone = ?, email = ?, numero_compte_bancaire = ? WHERE cin = ?");

            ps.setString(1, fournisseur.getNom());
            ps.setString(2, fournisseur.getAdresse());
            ps.setString(3, fournisseur.getNumeroTelephone());
            ps.setString(4, fournisseur.getEmail());
            ps.setString(5, fournisseur.getNumeroCompteBancaire());
            ps.setString(6, fournisseur.getCin());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating fournisseur: " + e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public void deleteByCin(String cin) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM fournisseur WHERE cin = ?");

            ps.setString(1, cin);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting fournisseur: " + e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public Fournisseur findByCin(String cin) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM fournisseur WHERE cin = ?");
            ps.setString(1, cin);

            rs = ps.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String adresse = rs.getString("adresse");
                String numeroTelephone = rs.getString("numero_telephone");
                String email = rs.getString("email");
                String numeroCompteBancaire = rs.getString("numero_compte_bancaire");

                return new Fournisseur(cin, nom, adresse, numeroTelephone, email, numeroCompteBancaire);
            }
        } catch (SQLException e) {
            System.err.println("Error finding fournisseur by cin: " + e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }

        return null;
    }

    @Override
    public List<Fournisseur> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM fournisseur");
            rs = ps.executeQuery();

            List<Fournisseur> fournisseurList = new ArrayList<>();

            while (rs.next()) {
                String cin = rs.getString("cin");
                String nom = rs.getString("nom");
                String adresse = rs.getString("adresse");
                String numeroTelephone = rs.getString("numero_telephone");
                String email = rs.getString("email");
                String numeroCompteBancaire = rs.getString("numero_compte_bancaire");

                Fournisseur fournisseur = new Fournisseur(cin, nom, adresse, numeroTelephone, email, numeroCompteBancaire);
                fournisseurList.add(fournisseur);
            }

            return fournisseurList;
        } catch (SQLException e) {
            System.err.println("Error retrieving fournisseurs: " + e);
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