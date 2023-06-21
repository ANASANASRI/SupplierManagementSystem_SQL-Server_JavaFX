package com.afm.suppliermanagementsystem.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import com.afm.suppliermanagementsystem.db.DB;
import com.afm.suppliermanagementsystem.model.Compte;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

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

public class Inscription {

	// Les variables interface graphique
	public int type;
	public static Compte profile;

	@FXML
	private JFXTextField prenom;

	@FXML
	private JFXTextField nom;

	@FXML
	private JFXTextField adresse;

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

	// Disable enable
	public void loadAuthen(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Authentification.fxml"));
		Scene s = new Scene(root);
		Stage fenetre = (Stage) ((Node) e.getSource()).getScene().getWindow();
		fenetre.setScene(s);
		fenetre.setResizable(false);
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		fenetre.setX((primScreenBounds.getWidth() - fenetre.getWidth()) / 2);
		fenetre.setY((primScreenBounds.getHeight() - fenetre.getHeight()) / 4);
		fenetre.show();
	}

	// Générer un mot de passe
	public String GenMotPass() {
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(8);
		for (int i = 0; i < 8; i++) {
			int randomLimitedInt = 97 + (int) (random.nextFloat() * (122 - 97 + 1));
			buffer.append((char) randomLimitedInt);
		}
		String mot_pass = buffer.toString();
		return mot_pass;
	}

	// Fonction inscrire
	public void inscrire(ActionEvent e) throws IOException, SQLException {
		message1.setVisible(false);
		message.setVisible(false);

		if (nom.getText().isEmpty() || pseudo.getText().isEmpty() || prenom.getText().isEmpty()
				|| tel.getText().isEmpty() || id.getText().isEmpty()) {
			//MenuAdmins.messageWarning();
			return;
		} else {
			if (verificationCompte()) {
				message.setVisible(true);
				message.setText("Le nom d'utilisateur est déjà utilisé.");
				return;
			} else {
				// Enregistrer les informations
				Compte compte;
						compte = new Compte();




				String motpass = GenMotPass();
				compte.setMot_pass(motpass);
				compte.setPseudo_nom(pseudo.getText());

				// Save the account and display success message
				int statues = DB.save(compte);

				if (statues > 0) {
					message1.setVisible(true);
					message1.setText("Vous êtes inscrit, votre mot de passe est : ");
					message11.setText(motpass);
				} else {
					message.setVisible(true);
					message.setText("Désolé, il y a un problème.");
				}
			}
		}
	}

	// Vérifier si le compte existe déjà
	public boolean verificationCompte() {
		List<Compte> comptes = DB.getComptes();
		for (Compte compte : comptes) {
			if (compte.getPseudo_nom().equals(pseudo.getText())) {
				return true;
			}
		}
		return false;
	}
}
