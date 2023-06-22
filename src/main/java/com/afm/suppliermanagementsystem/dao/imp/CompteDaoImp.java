package com.afm.suppliermanagementsystem.dao.imp;

import com.afm.suppliermanagementsystem.dao.CompteDao;
import com.afm.suppliermanagementsystem.model.Compte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompteDaoImp implements CompteDao {

    private final Connection conn;

    public CompteDaoImp(Connection connection) {
        this.conn = connection;
    }

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
    public Compte findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM compte WHERE id = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String telephone = rs.getString("telephone");
                String pseudoNom = rs.getString("pseudo_nom");
                String cin = rs.getString("cin");
                String motPass = rs.getString("mot_pass");
                String adresse = rs.getString("adresse");
                int etat = rs.getInt("etat");

                return new Compte(nom, prenom, telephone, pseudoNom, cin, motPass, adresse, etat);
            }
        } catch (SQLException e) {
            System.err.println("Error finding compte by id: " + e);
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
