package com.afm.suppliermanagementsystem.controller;

import com.afm.suppliermanagementsystem.model.Fournisseur;
import com.afm.suppliermanagementsystem.model.Paiement;
import com.afm.suppliermanagementsystem.services.PaiementService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaiementManager {
    @FXML
    private TextField txtIdentifiant;

    @FXML
    private TextField montant;

    @FXML
    private TextField devise;

    @FXML
    private TextField date;

    @FXML
    private CheckBox effectue;

    @FXML
    private ComboBox<String> moyenPaiement;
    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtSearch;

    private ObservableList<Paiement> filteredList;

    @FXML
    private Label fournisseurNumIFLabel;
    @FXML
    private Label fournisseurNomLabel;
    @FXML
    private Label fournisseurNumeroTelephoneLabel;
    @FXML
    private Label fournisseurEmailLabel;
    @FXML
    private Label fournisseurNumeroCompteBancaireLabel;
    @FXML
    private TableView<Paiement> tableViewPaiement;
    @FXML
    private TableColumn<Paiement, String> tableColumnIdentifiant;
    @FXML
    private TableColumn<Paiement, Double> tableColumnMontant;
    @FXML
    private TableColumn<Paiement, String> tableColumnDevise;
    @FXML
    private TableColumn<Paiement, String> tableColumnDate;
    @FXML
    private TableColumn<Paiement, Boolean> tableColumnEffectue;
    @FXML
    private TableColumn<Paiement, String> tableColumnMoyenPaiement;

    private Fournisseur fournisseur;

    public void setFournisseur(Fournisseur fournisseur) {
        List<Paiement> paiements = PaiementService.findAll();

        this.fournisseur = fournisseur;
        displayFournisseurInfo();
        populatePaiementTable();
        //
        tableViewPaiement.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                handleRowClick();
            }
        });
        //
        filteredList = FXCollections.observableArrayList(PaiementService.findAll());

        // Add a text change listener to the search text field
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterTable(newValue);
            }
        });
    }


    private void displayFournisseurInfo() {
        fournisseurNumIFLabel.setText(String.valueOf(fournisseur.getNumIF()));
        fournisseurNomLabel.setText(fournisseur.getNom());
        fournisseurNumeroTelephoneLabel.setText(fournisseur.getNumeroTelephone());
        fournisseurEmailLabel.setText(fournisseur.getEmail());
        fournisseurNumeroCompteBancaireLabel.setText(fournisseur.getNumeroCompteBancaire());
    }

    public void populatePaiementTable() {
        int numIF = fournisseur.getNumIF();
        List<Paiement> paiements = PaiementService.findAllIF(numIF);

        if (paiements != null) {
            paiements.forEach(System.out::println);
            System.out.println("Paiements!");

            tableColumnIdentifiant.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
            tableColumnMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
            tableColumnDevise.setCellValueFactory(new PropertyValueFactory<>("devise"));
            tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tableColumnEffectue.setCellValueFactory(new PropertyValueFactory<>("effectue"));
            tableColumnMoyenPaiement.setCellValueFactory(new PropertyValueFactory<>("moyenPaiement"));

            tableViewPaiement.getItems().addAll(paiements);
        } else {
            System.out.println("Not found.");
        }
    }



///////////////////////////////////

    private void handleRowClick() {
        Paiement selectedRow = tableViewPaiement.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            montant.setText(String.valueOf(selectedRow.getMontant()));
            devise.setText(selectedRow.getDevise());
            date.setText(String.valueOf(selectedRow.getDate()));
            effectue.setSelected(selectedRow.isEffectue());
            moyenPaiement.getSelectionModel().select(String.valueOf(selectedRow.getMoyenPaiement()));
        }
    }

    //////////////////////////////////////

    private void filterTable(String searchText) {
        int numgIF = fournisseur.getNumIF();
        List<Paiement> paiements = PaiementService.findAllIF(numgIF);
        List<Paiement> searchResults = new ArrayList<>();

        // Iterate over the filteredList and add matching suppliers to the searchResults list
        for (Paiement paiement : paiements) {
            String numIF = Integer.toString(paiement.getIdentifiant());
            if (numIF.contains(searchText)) {
                searchResults.add(paiement);
            }
        }

        // Clear the table view
        tableViewPaiement.getItems().clear();

        // Add the search results to the table view
        tableViewPaiement.getItems().addAll(searchResults);
    }

    ///////////////Cleare////////////////
    @FXML
    public void handleImageClick(MouseEvent mouseEvent) {
        clearInputFields();
    }

    private void clearInputFields() {
        montant.clear();
        devise.clear();
        date.clear();
        effectue.setSelected(false);
        moyenPaiement.setValue(null);
    }

    //////////////AJOUTER/////////////////

    @FXML
    private void handleSaveButtonAction() {

        Paiement paiement = new Paiement();

        //
        paiement.setNumIF(fournisseur.getNumIF());
        //
        String effectueValue = effectue.getText();
        boolean isEffectue = Boolean.parseBoolean(effectueValue);
        paiement.setEffectue(isEffectue);
        //
        paiement.setMontant(Double.parseDouble(montant.getText()));
        paiement.setDevise(devise.getText());
        //
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parsedDate = dateFormat.parse(date.getText());
            paiement.setDate(parsedDate);
        } catch (ParseException e) {
            System.err.println("Failed to parse date: " + e.getMessage());
        }
        //
        paiement.setMoyenPaiement(Paiement.MoyenPaiement.valueOf(moyenPaiement.getValue()));


        // Call the save method in PaiementService to insert the paiement
        PaiementService.save(paiement);

        // Refresh the table view to reflect the updated data
        refreshTableView();

        // Clear the input fields after saving
        clearInputFields();

        // Show a success message
        showAlert("Paiement ajouté", "Le paiement a été ajouté avec succès.");
    }

    @FXML
    private void handleUpdateButtonAction() {

    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshTableView() {
        // Get the current data model of the table view
        ObservableList<Paiement> paiements = tableViewPaiement.getItems();

        // Clear the existing data
        paiements.clear();

        // Fetch the updated data from the FournisseurService
        paiements.addAll(PaiementService.findAllIF(fournisseur.getNumIF()));

        // Reapply the data model to the table view
        tableViewPaiement.setItems(paiements);
    }
}