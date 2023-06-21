package com.afm.suppliermanagementsystem.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.afm.suppliermanagementsystem.HelloApplication;
import com.afm.suppliermanagementsystem.db.DB;
import com.afm.suppliermanagementsystem.model.Compte;
import com.jfoenix.controls.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Authentification {
	@FXML
	JFXTextField pseudo;
	@FXML
	JFXPasswordField motpass;
	@FXML
	Label message;
	private HelloApplication application;

	///////////////////Acces aux fenetre///////////////////////////

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


	///////////////////Connecter///////////////////////////
	public void Connecter(ActionEvent e) throws IOException, SQLException {

		List<Compte> list = DB.getComptes(); // from sql
		Map<String, String> map = new HashMap<String, String>();

		// remplir le Map
		for (Compte a : list) {
			map.put(a.getPseudo_nom(), a.getMot_pass());
		}

		// tester le mot de passe
		if (map.containsKey(pseudo.getText())) {
			String Mot = map.get(pseudo.getText()); // get password by username ( key)
			if (Mot.equals(motpass.getText())) {
				// Creer la nouvelle fenetre
				enLigne(pseudo.getText());
				DB.getConnection();
				if (pseudo.getText().equals("admin1") || pseudo.getText().equals("admin2")) {
					loadMenuAdm(e);
				} else {
					loadMenuAdh(e);
				}
			} else {
				message.setText("Votre nom d'utilisateur ou/et mot de passe est incorrect");
			}

		} else {
			message.setText("Votre nom d'utilisateur est incorrect");
		}
	}

	public void inscrire(ActionEvent e) throws IOException, SQLException {
		DB.getConnection();
		loadInsc(e);
	}

	public void enLigne(String pseud) {
		try {
			String sql = "UPDATE compte SET etat=? WHERE pseudo_nom=?";
			Connection con = DB.getConnection();
			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);
			preparedStatement.setInt(1, 1);
			preparedStatement.setString(2, pseud);
			preparedStatement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deconnection() {
		try {
			String sql = "UPDATE compte SET etat=? WHERE etat=?";
			Connection con = DB.getConnection();
			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);
			preparedStatement.setInt(1, 0);
			preparedStatement.setInt(2, 1);
			preparedStatement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setApplication(HelloApplication application) {
		this.application = application;
	}

}
