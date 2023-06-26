package com.afm.suppliermanagementsystem.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import com.afm.suppliermanagementsystem.dao.imp.DB;
import com.afm.suppliermanagementsystem.model.Compte;
import com.afm.suppliermanagementsystem.services.CompteService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Inscription {
	public int type;
	public static Compte profile;

	@FXML
	private JFXTextField prenom;

	@FXML
	private JFXTextField nom;

	@FXML
	private JFXTextField adresse;

	@FXML
	private JFXTextField identifiant;
	@FXML
	private PasswordField mot_pass;
	@FXML
	private JFXTextField id;

	@FXML
	private JFXTextField pseudo;

	@FXML
	private JFXTextField tel;

	@FXML
	private Label message;

	@FXML
	private Label message1;

	@FXML
	private Label message11;

	@FXML
	private JFXButton inscrireB;

	private void loadCon(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/InscriptionEtAuthentification.fxml"));
		Scene s = new Scene(root);
		Stage fenetre = (Stage) ((Node) e.getSource()).getScene().getWindow();
		fenetre.setScene(s);
		fenetre.setResizable(false);
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		fenetre.setX((primScreenBounds.getWidth() - fenetre.getWidth()) / 2);
		fenetre.setY((primScreenBounds.getHeight() - fenetre.getHeight()) / 4);

		fenetre.show();
	}


	public void connecter(ActionEvent e) throws IOException, SQLException {
		DB.getConnection();
		loadCon(e);
	}

	@FXML
	public void inscrirenow(ActionEvent e) throws IOException {
			// Get the values from the input fields
			String nomValue = nom.getText();
			String prenomValue = prenom.getText();
			String telValue = tel.getText();
			String pseudoValue = pseudo.getText();
			String identifiantValue = identifiant.getText();
			String motPassValue = mot_pass.getText();
			String adresseValue = adresse.getText();

			// Perform validation if needed

			// Create a new Compte object
			Compte compte = new Compte(nomValue, prenomValue, telValue, pseudoValue, identifiantValue, motPassValue, adresseValue , 1);

			// Save the Compte object using the CompteService
			CompteService compteService = new CompteService();
			compteService.save(compte);

		if (compteService.findCompte(nomValue, motPassValue)) {

			Parent root = FXMLLoader.load(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/MenuAdmis.fxml"));
			Scene s = new Scene(root);
			Stage fenetre = (Stage) ((Node) e.getSource()).getScene().getWindow();
			fenetre.setScene(s);
			fenetre.setResizable(false);
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			fenetre.setX((primScreenBounds.getWidth() - fenetre.getWidth()) / 2);
			fenetre.setY((primScreenBounds.getHeight() - fenetre.getHeight()) / 4);

			fenetre.show();
		} else {
			// Show an alert for unsuccessful connection
			Alert errorAlert = new Alert(Alert.AlertType.ERROR);
			errorAlert.setTitle("Connection Error");
			errorAlert.setHeaderText(null);
			errorAlert.setContentText("Failed to connect! Invalid username or password.");
			errorAlert.showAndWait();
		}

		}
}

