package com.afm.suppliermanagementsystem.dao.imp;

import com.afm.suppliermanagementsystem.dao.PaiementDao;
import com.afm.suppliermanagementsystem.model.Paiement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaiementDaoImp implements PaiementDao {

    private final Connection connection;

    public PaiementDaoImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Paiement paiement) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("INSERT INTO paiement (identifiant, montant, devise, date, effectue, moyenPaiement) VALUES (?, ?, ?, ?, ?, ?)");

            ps.setString(1, paiement.getIdentifiant());
            ps.setDouble(2, paiement.getMontant());
            ps.setString(3, paiement.getDevise());
            ps.setDate(4, new java.sql.Date(paiement.getDate().getTime()));
            ps.setBoolean(5, paiement.isEffectue());
            ps.setString(6, paiement.getMoyenPaiement().name());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to insert paiement: " + e.getMessage());
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public void update(Paiement paiement) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("UPDATE paiement SET montant = ?, devise = ?, date = ?, effectue = ?, moyenPaiement = ? WHERE identifiant = ?");

            ps.setDouble(1, paiement.getMontant());
            ps.setString(2, paiement.getDevise());
            ps.setDate(3, new java.sql.Date(paiement.getDate().getTime()));
            ps.setBoolean(4, paiement.isEffectue());
            ps.setString(5, paiement.getMoyenPaiement().name());
            ps.setString(6, paiement.getIdentifiant());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to update paiement: " + e.getMessage());
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public void deleteById(String identifiant) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("DELETE FROM paiement WHERE identifiant = ?");

            ps.setString(1, identifiant);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete paiement: " + e.getMessage());
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public Paiement findById(String identifiant) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Paiement paiement = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM paiement WHERE identifiant = ?");

            ps.setString(1, identifiant);

            rs = ps.executeQuery();

            if (rs.next()) {
                paiement = extractPaiementFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Failed to find paiement by id: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }

        return paiement;
    }

    @Override
    public List<Paiement> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Paiement> paiements = new ArrayList<>();

        try {
            ps = connection.prepareStatement("SELECT * FROM paiement");
            rs = ps.executeQuery();

            while (rs.next()) {
                Paiement paiement = extractPaiementFromResultSet(rs);
                paiements.add(paiement);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve paiements: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }

        return paiements;
    }

    private Paiement extractPaiementFromResultSet(ResultSet rs) throws SQLException {
        String identifiant = rs.getString("identifiant");
        double montant = rs.getDouble("montant");
        String devise = rs.getString("devise");
        Date date = rs.getDate("date");
        boolean effectue = rs.getBoolean("effectue");
        Paiement.MoyenPaiement moyenPaiement = Paiement.MoyenPaiement.valueOf(rs.getString("moyenPaiement"));

        return new Paiement(identifiant, montant, devise, date, effectue, moyenPaiement);
    }

    private void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                System.err.println("Failed to close PreparedStatement: " + e.getMessage());
            }
        }
    }

    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Failed to close ResultSet: " + e.getMessage());
            }
        }
    }
}
