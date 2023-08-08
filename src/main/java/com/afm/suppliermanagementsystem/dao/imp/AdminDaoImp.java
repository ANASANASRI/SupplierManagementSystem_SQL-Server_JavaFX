package com.afm.suppliermanagementsystem.dao.imp;

import com.afm.suppliermanagementsystem.dao.AdminDao;
import com.afm.suppliermanagementsystem.model.AdminFinancier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminDaoImp implements AdminDao {

    private final Connection conn;

    public AdminDaoImp(Connection connection) {
        this.conn = connection;
    }

    @Override
    public void insert(AdminFinancier admin) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO [afm].[afm].[adminfinancier] (cin, nom, prenom) VALUES (?, ?, ?)");

            ps.setString(1, admin.getCin());
            ps.setString(2, admin.getNom());
            ps.setString(3, admin.getPrenom());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting admin financier: " + e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public void update(AdminFinancier admin) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE [afm].[afm].[adminfinancier] SET nom = ?, prenom = ? WHERE cin = ?");

            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getCin());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating admin financier: " + e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public AdminFinancier findById(String id) {
        return null;
    }

    @Override
    public List<AdminFinancier> findAll() {
        return null;
    }

    @Override
    public void deleteByCin(String cin) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM [afm].[afm].[adminfinancier] WHERE cin = ?");

            ps.setString(1, cin);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting admin financier: " + e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    @Override
    public AdminFinancier findByCin(String cin) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM [afm].[afm].[adminfinancier] WHERE cin = ?");
            ps.setString(1, cin);

            rs = ps.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");

                return new AdminFinancier(cin, nom, prenom);
            }
        } catch (SQLException e) {
            System.err.println("Error finding admin financier by cin: " + e);
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