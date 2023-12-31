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

    private final Connection conn=DB.getConnection();

    @Override
    public void insert(Fournisseur fournisseur) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO [afm].[afm].[fournisseur] (numIF, nom, adresse, numeroTelephone, email, numeroCompteBancaire) VALUES (?, ?, ?, ?, ?, ?)");

            ps.setInt(1, fournisseur.getNumIF());
            ps.setString(2, fournisseur.getNom());
            ps.setString(3, fournisseur.getAdresse());
            ps.setString(4, fournisseur.getNumeroTelephone());
            ps.setString(5, fournisseur.getEmail());
            ps.setString(6, fournisseur.getNumeroCompteBancaire());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting fournisseur: " + e);
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void modif(Fournisseur fournisseur,int oldNumIF) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE [afm].[afm].[fournisseur] SET nom = ?, adresse = ?, numeroTelephone = ?, email = ?, numeroCompteBancaire = ?, numIF = ? WHERE numIF = ?");

            ps.setString(1, fournisseur.getNom());
            ps.setString(2, fournisseur.getAdresse());
            ps.setString(3, fournisseur.getNumeroTelephone());
            ps.setString(4, fournisseur.getEmail());
            ps.setString(5, fournisseur.getNumeroCompteBancaire());
            ps.setInt(6, fournisseur.getNumIF());
            ps.setInt(7, oldNumIF);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating fournisseur: " + e);
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteBynumIF(int numIF) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM [afm].[afm].[fournisseur] WHERE numIF = ?");

            ps.setInt(1, numIF);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting fournisseur: " + e);
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Fournisseur findBynumIF(int numIF) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM [afm].[afm].[fournisseur] WHERE numIF = ?");
            ps.setInt(1, numIF);

            rs = ps.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String adresse = rs.getString("adresse");
                String numeroTelephone = rs.getString("numeroTelephone");
                String email = rs.getString("email");
                String numeroCompteBancaire = rs.getString("numeroCompteBancaire");

                return new Fournisseur(numIF, nom, adresse, numeroTelephone, email, numeroCompteBancaire);
            }
        } catch (SQLException e) {
            System.err.println("Error finding fournisseur by numIF: " + e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }

        return null;
    }

    @Override
    public List<Fournisseur> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM [afm].[afm].[fournisseur]");
            rs = ps.executeQuery();

            List<Fournisseur> fournisseurList = new ArrayList<>();

            while (rs.next()) {

                Fournisseur fournisseur = new Fournisseur();

                fournisseur.setNumIF(rs.getInt("numIF"));
                fournisseur.setNom(rs.getString("nom"));
                fournisseur.setAdresse(rs.getString("adresse"));
                fournisseur.setNumeroTelephone(rs.getString("numeroTelephone"));
                fournisseur.setEmail(rs.getString("email"));
                fournisseur.setNumeroCompteBancaire(rs.getString("numeroCompteBancaire"));

                fournisseurList.add(fournisseur);
            }

            return fournisseurList;
        } catch (SQLException e) {
            System.err.println("Error retrieving fournisseurs: " + e);
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }
}