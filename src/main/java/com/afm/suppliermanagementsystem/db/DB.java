package com.afm.suppliermanagementsystem.db;

import com.afm.suppliermanagementsystem.model.Compte;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
                System.err.println("Connection établie");
            } catch (SQLException e) {
                System.err.println("Problème de chargement de DriverManager");
            }
        }

        System.err.println("Connected: " + conn);
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erreur de fermeture de la connexion");
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("src/main/resources/db.properties")) {
            Properties props = new Properties();

            props.load(fs);

            return props;
        } catch (IOException e) {
            System.err.println("Erreur de chargement des propriétés");
        }
        return null;
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                System.err.println("Erreur de fermeture du Statement");
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Erreur de fermeture du ResultSet");
            }
        }
    }

    public static List<Compte> getComptes() {
        List<Compte> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Compte";
            Connection con = DB.getConnection();
            PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Compte compte = new Compte();

                compte.setPseudo_nom(resultSet.getString(2));
                compte.setMot_pass(resultSet.getString(4));
                compte.setCode_adh(resultSet.getString(1));


                list.add(compte);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int save(Compte cmpt) {
        int st = 0;
        try {
            String sql = "INSERT INTO Compte (pseudo_nom, mot_pass, code_adh, typecompte) VALUES (?, ?, ?, ?)";
            Connection con = DB.getConnection();
            PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);
            preparedStatement.setString(1, cmpt.getPseudo_nom());
            preparedStatement.setString(2, cmpt.getMot_pass());
            preparedStatement.setString(3, cmpt.getIdentifiant());


            st = preparedStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st;
    }

    public static Compte getCompteByEtat(int etat) {
        Compte cmpt = new Compte();
        try {
            String sql = "SELECT * FROM compte WHERE etat = ?";
            Connection con = DB.getConnection();
            PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cmpt.setPseudo_nom(resultSet.getString(2));
                cmpt.setMot_pass(resultSet.getString(4));
                cmpt.setCode_adh(resultSet.getString(1));

            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cmpt;
    }

    public static int personneModif(Compte cmp, String code) {
        int st = 0, st2 = 0, st3 = 0;
        try {
            String sql = "UPDATE compte SET pseudo_nom = ? WHERE code_adh = ?";
            String sq2 = "UPDATE adherent SET tel = ? WHERE code_adh = ?";
            String sq3 = "UPDATE adherent SET adresse = ? WHERE code_adh = ?";

            Connection con = DB.getConnection();
            PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);
            PreparedStatement preparedStatement2 = (PreparedStatement) con.prepareStatement(sq2);
            PreparedStatement preparedStatement3 = (PreparedStatement) con.prepareStatement(sq3);

            preparedStatement.setString(1, cmp.getPseudo_nom());
            preparedStatement.setString(2, code);
            preparedStatement2.setString(1, cmp.getTelephone());
            preparedStatement2.setString(2, code);
            preparedStatement3.setString(1, cmp.getAdresse());
            preparedStatement3.setString(2, code);

            st = preparedStatement.executeUpdate();
            st2 = preparedStatement2.executeUpdate();
            st3 = preparedStatement3.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st * st2 * st3;
    }

    public static int motpassModif(Compte cmp, int etat) {
        int st = 0;
        try {
            String sql = "UPDATE compte SET mot_pass = ? WHERE etat = ?";
            Connection con = DB.getConnection();
            PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);
            preparedStatement.setString(1, cmp.getMot_pass());
            preparedStatement.setInt(2, etat);
            st = preparedStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st;
    }
}
