package com.afm.suppliermanagementsystem.dao.imp;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                // Set TLS version explicitly
                //System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2");

                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                String user = props.getProperty("user");
                String password = props.getProperty("password");

                // Set additional SSL properties
                Properties connectionProps = new Properties();
                connectionProps.setProperty("user", user);
                connectionProps.setProperty("password", password);
                //connectionProps.setProperty("encrypt", "true");
                //connectionProps.setProperty("trustServerCertificate", "true");
                //connectionProps.setProperty("sslProtocol", "TLSv1.2"); // Specify TLSv1.2

                //jdbc:sqlserver://localhost:1433;databaseName=mydatabase;user=sa;password=mypassword;encrypt=true;trustServerCertificate=true;

                // The error message means that the SQL Server has offered to use the TLS 1.0
                // protocol version for encryption during the SSL/TLS handshake,
                // but your Java application (the client) prefers or requires a higher version of the protocol,
                // specifically  TLS1.2

                String connectionUrl = url; // Append to connection URL

                conn = DriverManager.getConnection(connectionUrl, connectionProps);
                System.err.println("Connection established");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e);
                System.err.println("Problem with DriverManager loading");
            }
        }

        System.err.println("Connected: " + conn);
        return conn;
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("src/main/resources/db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            System.err.println("Erreur de chargement de prori?t?s");
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