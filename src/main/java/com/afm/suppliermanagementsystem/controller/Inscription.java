package com.afm.suppliermanagementsystem.controller;

import com.afm.suppliermanagementsystem.services.PasswordHasher;
import java.io.IOException;
import java.sql.SQLException;

import com.afm.suppliermanagementsystem.dao.imp.DB;
import com.afm.suppliermanagementsystem.model.Compte;
import com.afm.suppliermanagementsystem.services.CompteService;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Inscription {
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
	private JFXTextField pseudo;
	@FXML
	private JFXTextField tel;


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
		String nomValue = nom.getText();
		String prenomValue = prenom.getText();
		String telValue = tel.getText();
		String pseudoValue = pseudo.getText();
		String identifiantValue = identifiant.getText();
		String motPassValue = mot_pass.getText();
		String adresseValue = adresse.getText();

		// Check if any of the fields are empty
		if (nomValue.isEmpty() || prenomValue.isEmpty() || telValue.isEmpty() ||
				pseudoValue.isEmpty() || identifiantValue.isEmpty() ||
				motPassValue.isEmpty() || adresseValue.isEmpty()) {
			Alert errorAlert = new Alert(Alert.AlertType.ERROR);
			errorAlert.setTitle("Erreur");
			errorAlert.setHeaderText(null);
			errorAlert.setContentText("Veuillez remplir tous les champs.");
			errorAlert.showAndWait();
		} else {
			String hashedPassword = PasswordHasher.hashPassword(motPassValue);
			if (hashedPassword != null) {
				Compte compte = new Compte(nomValue, prenomValue, telValue, pseudoValue, identifiantValue, hashedPassword, adresseValue, 1);

				// Save the Compte object using the CompteService
				CompteService compteService = new CompteService();

				if (compteService.findinfCompte(nomValue, prenomValue,pseudoValue,identifiantValue)) {
					Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
					informationAlert.setTitle("Ces informations existent déjà.");
					informationAlert.setHeaderText(null);
					informationAlert.setContentText("Essayer un autre pseudo ou vérifiez votre identification (CIN)");
					informationAlert.initModality(Modality.WINDOW_MODAL);
					informationAlert.showAndWait();
				} else {
					compteService.saveins(compte);

					if (compteService.findCompte(nomValue, hashedPassword)) {
						Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
						informationAlert.setTitle("Créé avec succès");
						informationAlert.setHeaderText(null);
						informationAlert.setContentText("Votre compte a été créé avec succès. Veuillez patienter pour l'activation du compte par l'administrateur.");

						// Rendre la boîte de dialogue modale
						informationAlert.initModality(Modality.WINDOW_MODAL);

						// Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
						informationAlert.showAndWait();
						clearInputFields();

					} else {
						Alert errorAlert = new Alert(Alert.AlertType.ERROR);
						errorAlert.setTitle("Connection Error");
						errorAlert.setHeaderText(null);
						errorAlert.setContentText("Failed to connect! Invalid username or password.");
						errorAlert.showAndWait();
					}
				}
			}
		}
	}


	private void clearInputFields() {
		prenom.clear();
		nom.clear();
		adresse.clear();
		identifiant.clear();
		mot_pass.clear();
		pseudo.clear();
		tel.clear();
	}



	//////////////////////////////////

	@FXML
	private JFXTextField mot_pass_show;
	@FXML
	private ImageView lblclose;
	@FXML
	private ImageView lblopen;
	String password;

	public void openEye(MouseEvent mouseEvent) {
		mot_pass_show.setVisible(false);
		mot_pass.setVisible(true);
		lblopen.setVisible(false);
		lblclose.setVisible(true);
	}

	public void closeEye(MouseEvent mouseEvent) {
		mot_pass_show.setVisible(true);
		mot_pass.setVisible(false);
		lblopen.setVisible(true);
		lblclose.setVisible(false);
	}

	public void hide_passWord(KeyEvent keyEvent) {
		password = mot_pass.getText();
		mot_pass_show.setText(password);
	}

	public void showed_passWord(KeyEvent keyEvent) {
		password = mot_pass_show.getText();
		mot_pass.setText(password);
	}

	public void initialize(){
		mot_pass_show.setVisible(false);
		lblopen.setVisible(false);
	}


	//////////////////////////////////////

}













