package com.afm.suppliermanagementsystem.dao.imp;

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
            System.err.println("connection effectuer");
        } catch (SQLException e) {
            Properties props = loadProperties();
            String url = props.getProperty("dburl");
            e.printStackTrace();
            System.err.println("Problème de chargement de Driver Manager"+url+"*****"+props);
        }
    }

    System.err.println("Connected"+conn);
    return conn;
}

public static void closeConnection() {
    if (conn != null) {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Erreur de fermeture de connexion");
        }
    }
}

private static Properties loadProperties() {
    try (FileInputStream fs = new FileInputStream("src/main/resources/db.properties")) {
        Properties props = new Properties();

        props.load(fs);

        return props;
    } catch (IOException e) {
        System.err.println("Erreur de chargement de proriétés");
    }
    return null;
}

public static void closeStatement(Statement st) {
    if (st != null) {
        try {
            st.close();
        } catch (SQLException e) {
            System.err.println("Erreur de fermeture de Statement");
        }
    }
}

public static void closeResultSet(ResultSet rs) {
    if (rs != null) {
        try {
            rs.close();
        } catch (SQLException e) {
            System.err.println("Erreur de fermeture de ResultSet");
        }
    }
}

}
