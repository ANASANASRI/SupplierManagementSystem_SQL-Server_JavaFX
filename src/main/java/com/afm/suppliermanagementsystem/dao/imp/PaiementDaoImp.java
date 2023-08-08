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

    private final Connection connection = DB.getConnection();

    public PaiementDaoImp() {

    }

    @Override
    public void insert(Paiement paiement) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("INSERT INTO [afm].[afm].[paiement] (montant, devise, date, effectue, moyenPaiement, numIF, agence, libelle, numCheque) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setDouble(1, paiement.getMontant());
            ps.setString(2, paiement.getDevise());
            ps.setDate(3, new java.sql.Date(paiement.getDate().getTime()));
            ps.setBoolean(4, paiement.isEffectue());
            ps.setString(5, paiement.getMoyenPaiement().name());
            ps.setInt(6, paiement.getNumIF());
            ps.setString(7, paiement.getAgence());
            ps.setString(8, paiement.getLibelle());
            ps.setString(9, paiement.getNumCheque());

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
            ps = connection.prepareStatement("UPDATE [afm].[afm].[paiement] SET montant = ?, devise = ?, date = ?, effectue = ?, moyenPaiement = ?, agence = ?, libelle = ?, numCheque = ? WHERE identifiant = ?");

            ps.setDouble(1, paiement.getMontant());
            ps.setString(2, paiement.getDevise());
            ps.setDate(3, new java.sql.Date(paiement.getDate().getTime()));
            ps.setBoolean(4, paiement.isEffectue());
            ps.setString(5, paiement.getMoyenPaiement().name());
            ps.setString(6, paiement.getAgence());
            ps.setString(7, paiement.getLibelle());
            ps.setString(8, paiement.getNumCheque());
            ps.setInt(9, paiement.getIdentifiant());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to update paiement: " + e.getMessage());
        } finally {
            closePreparedStatement(ps);
        }
    }



    @Override
    public Paiement deleteById(int identifiant) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("DELETE FROM [afm].[afm].[paiement] WHERE identifiant = ?");

            ps.setString(1, String.valueOf(identifiant));

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete paiement: " + e.getMessage());
        } finally {
            closePreparedStatement(ps);
        }
        return null;
    }

    @Override
    public Paiement findById(String identifiant) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Paiement paiement = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM [afm].[afm].[paiement] WHERE identifiant = ?");

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
            ps = connection.prepareStatement("SELECT * FROM [afm].[afm].[paiement]");
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

    @Override
    public Paiement findByIdentifiant(String identifiant) {
        return null;
    }

    @Override
    public List<Paiement> findAllIF(int numIF) {
        List<Paiement> paiements = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM [afm].[afm].[paiement] WHERE numIF = ?")) {
            statement.setInt(1, numIF);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int identifiant = resultSet.getInt("identifiant");
                    double montant = resultSet.getDouble("montant");
                    String devise = resultSet.getString("devise");
                    Date date = resultSet.getDate("date");
                    boolean effectue = resultSet.getBoolean("effectue");
                    String moyenPaiement = resultSet.getString("moyenPaiement");
                    String agence = resultSet.getString("agence");
                    String libelle = resultSet.getString("libelle");
                    String numCheque = resultSet.getString("numCheque");

                    Paiement.MoyenPaiement moyenPaiementEnum = Paiement.MoyenPaiement.valueOf(moyenPaiement);

                    // Create a new Paiement object with all the fields
                    Paiement paiement = new Paiement(identifiant, montant, devise, date, effectue, moyenPaiementEnum);
                    paiement.setAgence(agence);
                    paiement.setLibelle(libelle);
                    paiement.setNumCheque(numCheque);

                    paiements.add(paiement);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paiements;
    }



    private Paiement extractPaiementFromResultSet(ResultSet rs) throws SQLException {
        int identifiant = rs.getInt("identifiant");
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
