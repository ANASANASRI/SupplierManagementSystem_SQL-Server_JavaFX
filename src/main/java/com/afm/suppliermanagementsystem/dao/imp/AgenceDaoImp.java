package com.afm.suppliermanagementsystem.dao.imp;

import com.afm.suppliermanagementsystem.dao.AgenceDao;
import com.afm.suppliermanagementsystem.model.Agence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgenceDaoImp implements AgenceDao {

    private final Connection conn = DB.getConnection();

    public List<Agence> findAll() {
        List<Agence> agenceList = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM [afm].[afm].[agences]");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Agence agence = new Agence();
                agence.setAgence(rs.getString("Agence"));
                agence.setAddress(rs.getString("Adresse"));
                agence.setTelephone(rs.getString("Telephone"));

                agenceList.add(agence);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving agences: " + e);
        }

        return agenceList;
    }
}

