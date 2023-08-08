package com.afm.suppliermanagementsystem.dao.imp;

import com.afm.suppliermanagementsystem.dao.CompteDao;
import com.afm.suppliermanagementsystem.model.Compte;
import com.afm.suppliermanagementsystem.model.Fournisseur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompteDaoImp implements CompteDao {

    private final Connection conn=DB.getConnection();

    public CompteDaoImp() {  }

    @Override
    public void insert(Compte compte) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO [afm].[afm].[compte] (nom, prenom, telephone, pseudo_nom, cin, mot_pass, adresse, etat,isAdmin) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)");

            ps.setString(1, compte.getNom());
            ps.setString(2, compte.getPrenom());
            ps.setString(3, compte.getTelephone());
            ps.setString(4, compte.getPseudoNom());
            ps.setString(5, compte.getCin());
            ps.setString(6, compte.getMotPass());
            ps.setString(7, compte.getAdresse());
            ps.setInt(8, compte.getEtat());
            ps.setInt(9, compte.getIsAdmin());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting compte: " + e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public void insertins(Compte compte) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO [afm].[afm].[compte] (nom, prenom, telephone, pseudo_nom, cin, mot_pass, adresse, etat,isAdmin) VALUES (?, ?, ?, ?, ?, ?, ?, 0,?)");

            ps.setString(1, compte.getNom());
            ps.setString(2, compte.getPrenom());
            ps.setString(3, compte.getTelephone());
            ps.setString(4, compte.getPseudoNom());
            ps.setString(5, compte.getCin());
            ps.setString(6, compte.getMotPass());
            ps.setString(7, compte.getAdresse());
            ps.setInt(8, compte.getIsAdmin());

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
            StringBuilder queryBuilder = new StringBuilder("UPDATE [afm].[afm].[compte] SET nom = ?, prenom = ?, telephone = ?, pseudo_nom = ?, cin = ?, adresse = ?, etat = ?, isAdmin = ?");
            List<Object> params = new ArrayList<>();

            // Append password update only if it's not null and not empty
            if (compte.getMotPass() != null && !compte.getMotPass().isEmpty()) {
                queryBuilder.append(", mot_pass = ?");
                params.add(compte.getMotPass());
            }

            queryBuilder.append(" WHERE cin = ?");
            params.add(compte.getCin());

            ps = conn.prepareStatement(queryBuilder.toString());

            ps.setString(1, compte.getNom());
            ps.setString(2, compte.getPrenom());
            ps.setString(3, compte.getTelephone());
            ps.setString(4, compte.getPseudoNom());
            ps.setString(5, compte.getCin());
            ps.setString(6, compte.getAdresse());
            ps.setInt(7, compte.getEtat());
            ps.setInt(8, compte.getIsAdmin());

            // Set password only if it's not null and not empty
            int paramIndex = 9;
            if (compte.getMotPass() != null && !compte.getMotPass().isEmpty()) {
                ps.setObject(paramIndex, compte.getMotPass());
                paramIndex++;
            }

            ps.setString(paramIndex, compte.getCin());

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
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM [afm].[afm].[compte]");
            rs = ps.executeQuery();

            List<Compte> compteList = new ArrayList<>();

            while (rs.next()) {
                Compte compte = new Compte();
                compte.setCin(rs.getString("CIN"));
                compte.setNom(rs.getString("Nom"));
                compte.setPrenom(rs.getString("Prenom"));
                compte.setTelephone(rs.getString("Telephone"));
                compte.setPseudoNom(rs.getString("Pseudo_nom"));
                compte.setAdresse(rs.getString("Adresse"));
                compte.setEtat(rs.getInt("Etat"));
                compte.setIsAdmin(rs.getInt("IsAdmin"));

                compteList.add(compte);
            }

            return compteList;
        } catch (SQLException e) {
            System.err.println("Error retrieving compte: " + e);
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }



    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM [afm].[afm].[compte] WHERE id = ?");

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
        String query = "SELECT COUNT(*) AS count FROM [afm].[afm].[compte] WHERE pseudo_nom = ? AND mot_pass = ? AND IsAdmin = 0";

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
    public boolean findetat(String nom, String password) {
        boolean findetat = false;
        String query = "SELECT COUNT(*) AS count FROM [afm].[afm].[compte] WHERE pseudo_nom = ? AND mot_pass = ? AND etat = 1";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, nom);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    findetat = count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findetat;
    }

    @Override
    public boolean findinfCompte(String nomValue, String prenomValue, String pseudoValue, String identifiantValue) {
        boolean findinfCompte = false;
        String query = "SELECT COUNT(*) AS count FROM [afm].[afm].[compte] WHERE (nom = ? AND prenom = ? AND pseudo_nom = ? AND cin = ?) OR pseudo_nom = ? OR cin = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, nomValue);
            statement.setString(2, prenomValue);
            statement.setString(3, pseudoValue);
            statement.setString(4, identifiantValue);
            statement.setString(5, pseudoValue);
            statement.setString(6, identifiantValue);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    findinfCompte = count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findinfCompte;
    }


    @Override
    public boolean findAdmCompte(String nom, String password) {
        boolean compteExists = false;
        String query = "SELECT COUNT(*) AS count FROM [afm].[afm].[compte] WHERE pseudo_nom = ? AND mot_pass = ? AND IsAdmin = 1";

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

    @Override
    public void deleteByCin(String cin) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM [afm].[afm].[compte] WHERE CIN = ?");

            ps.setString(1, cin);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting compte: " + e);
        } finally {
            DB.closeStatement(ps);
        }
    }

}
