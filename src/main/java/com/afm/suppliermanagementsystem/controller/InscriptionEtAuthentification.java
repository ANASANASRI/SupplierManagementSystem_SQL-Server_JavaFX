package com.afm.suppliermanagementsystem.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.afm.suppliermanagementsystem.HelloApplication;
import com.afm.suppliermanagementsystem.db.DB;
import com.afm.suppliermanagementsystem.model.AdminFinancier;
import com.afm.suppliermanagementsystem.model.Compte;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;


import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class InscriptionEtAuthentification  {
    private HelloApplication application;

    public void setApplication(HelloApplication application) {
        this.application = application;
    }
    //Les variables interface graphique
    public int type;
    public static Compte profile;

    public void loadMenuAdh(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MenuAdherent.fxml"));
        Scene s = new Scene(root);
        Stage fenetre = (Stage) ((Node) e.getSource()).getScene().getWindow();
        fenetre.setScene(s);
        fenetre.setResizable(false);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        fenetre.setX((primScreenBounds.getWidth() - fenetre.getWidth()) / 2);
        fenetre.setY((primScreenBounds.getHeight() - fenetre.getHeight()) / 4);
        fenetre.show();
    }


    private void loadMenuAdm(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MenuAdmins.fxml"));
        Scene s = new Scene(root);
        Stage fenetre = (Stage) ((Node) e.getSource()).getScene().getWindow();
        fenetre.setScene(s);
        fenetre.setResizable(false);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        fenetre.setX((primScreenBounds.getWidth() - fenetre.getWidth()) / 2);
        fenetre.setY((primScreenBounds.getHeight() - fenetre.getHeight()) / 4);
        fenetre.show();
    }


    private void loadInsc(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/Ins.fxml"));
        Scene s = new Scene(root);
        Stage fenetre = (Stage) ((Node) e.getSource()).getScene().getWindow();
        fenetre.setScene(s);
        fenetre.setResizable(false);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        fenetre.setX((primScreenBounds.getWidth() - fenetre.getWidth()) / 2);
        fenetre.setY((primScreenBounds.getHeight() - fenetre.getHeight()) / 4);

        fenetre.show();
    }


    public void inscrire(ActionEvent e) throws IOException, SQLException {
        DB.getConnection();
        loadInsc(e);
    }

}
