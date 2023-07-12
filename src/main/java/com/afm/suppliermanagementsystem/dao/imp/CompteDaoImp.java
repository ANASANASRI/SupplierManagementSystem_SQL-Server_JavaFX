package com.afm.suppliermanagementsystem.dao.imp;

import com.afm.suppliermanagementsystem.dao.CompteDao;
import com.afm.suppliermanagementsystem.model.Compte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompteDaoImp implements CompteDao {

    private final Connection conn=DB.getConnection();

    public CompteDaoImp() {  }

    @Override
    public void insert(Compte compte) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO compte (nom, prenom, telephone, pseudo_nom, cin, mot_pass, adresse, etat) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, compte.getNom());
            ps.setString(2, compte.getPrenom());
            ps.setString(3, compte.getTelephone());
            ps.setString(4, compte.getPseudoNom());
            ps.setString(5, compte.getCin());
            ps.setString(6, compte.getMotPass());
            ps.setString(7, compte.getAdresse());
            ps.setInt(8, compte.getEtat());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting compte: " + e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public void update(Compte compte) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE compte SET nom = ?, prenom = ?, telephone = ?, pseudo_nom = ?, cin = ?, mot_pass = ?, adresse = ?, etat = ? WHERE id = ?");

            ps.setString(1, compte.getNom());
            ps.setString(2, compte.getPrenom());
            ps.setString(3, compte.getTelephone());
            ps.setString(4, compte.getPseudoNom());
            ps.setString(5, compte.getCin());
            ps.setString(6, compte.getMotPass());
            ps.setString(7, compte.getAdresse());
            ps.setInt(8, compte.getEtat());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating compte: " + e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Compte findById(String id) {
        return null;
    }

    @Override
    public List<Compte> findAll() {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM compte WHERE id = ?");

            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting compte: " + e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public boolean findCompte(String nom, String password) {
        boolean compteExists = false;
        String query = "SELECT COUNT(*) AS count FROM compte WHERE pseudo_nom = ? AND mot_pass = ? AND IsAdmin = 0";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, nom);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    compteExists = count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compteExists;
    }



    @Override
    public boolean findAdmCompte(String nom, String password) {
        boolean compteExists = false;
        String query = "SELECT COUNT(*) AS count FROM compte WHERE pseudo_nom = ? AND mot_pass = ? AND IsAdmin = 1";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, nom);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    compteExists = count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compteExists;
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
