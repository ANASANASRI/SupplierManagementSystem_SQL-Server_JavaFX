package com.afm.suppliermanagementsystem.controller;

import com.afm.suppliermanagementsystem.HelloApplication;
import com.afm.suppliermanagementsystem.model.Fournisseur;
import com.afm.suppliermanagementsystem.services.FournisseurService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuAdmins {
    @FXML
    private TextField txtnumSIREN;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtAdresse;
    @FXML
    private TextField txtNumeroTelephone;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtNumeroCompteBancaire;

    @FXML
    private TableView<Fournisseur> tableViewFournisseur;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpadte;
    @FXML
    private TableColumn<Fournisseur, Integer> tableColumnnumSIREN;
    @FXML
    private TableColumn<Fournisseur, String> tableColumnNom;
    @FXML
    private TableColumn<Fournisseur, String> tableColumnAdresse;
    @FXML
    private TableColumn<Fournisseur, String> tableColumnNumeroTelephone;
    @FXML
    private TableColumn<Fournisseur, String> tableColumnEmail;
    @FXML
    private TableColumn<Fournisseur, String> tableColumnNumeroCompteBancaire;
    @FXML
    private TableColumn<Fournisseur, Button> tableColumnPaiement;
    @FXML
    private TableColumn<Fournisseur, Button> tableColumnREMOVE;
    @FXML
    private Label lblStatus;

    @FXML
    private TextField txtSearch;

    private ObservableList<Fournisseur> suppliersList;
    private ObservableList<Fournisseur> filteredList;

    private FournisseurService fournisseurService;

    public MenuAdmins() {
        fournisseurService = new FournisseurService();
    }


    @FXML
    private void initialize() {
        populateFournisseurTable();
        setupButtonActions();
        //
        tableViewFournisseur.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                handleRowClick();
            }
        });
        //
        filteredList = FXCollections.observableArrayList(fournisseurService.findAll());

        // Add a text change listener to the search text field
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterTable(newValue);
            }
        });


    }

        /*/////////////////*/

    public void populateFournisseurTable() {
        List<Fournisseur> fournisseurs = fournisseurService.findAll();

        if (fournisseurs != null) {
            fournisseurs.forEach(System.out::println);
            System.out.println("Fournisseur!");

            //
            tableColumnnumSIREN.setCellValueFactory(new PropertyValueFactory<>("numSIREN"));
            tableColumnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            tableColumnAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            tableColumnNumeroTelephone.setCellValueFactory(new PropertyValueFactory<>("numeroTelephone"));
            tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tableColumnNumeroCompteBancaire.setCellValueFactory(new PropertyValueFactory<>("numeroCompteBancaire"));

            setupButtonActions();

            tableViewFournisseur.getItems().addAll(fournisseurs);

        } else {
            System.out.println("No found.");
        }

    }


    ////////////////AJOUTER////////////////////
    @FXML
    private void handleSaveButtonAction() {
        // Create a new Fournisseur object and set its properties using the text fields
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNumSIREN(Integer.parseInt(txtnumSIREN.getText()));
        fournisseur.setNom(txtNom.getText());
        fournisseur.setAdresse(txtAdresse.getText());
        fournisseur.setNumeroTelephone(txtNumeroTelephone.getText());
        fournisseur.setEmail(txtEmail.getText());
        fournisseur.setNumeroCompteBancaire(txtNumeroCompteBancaire.getText());

        // Call the save method in FournisseurService to insert the fournisseur
        fournisseurService.save(fournisseur);

        // Clear the input fields after saving
        clearInputFields();

        // Refresh the table view to reflect the updated data
        refreshTableView();
    }

    private void refreshTableView() {
        // Get the current data model of the table view
        ObservableList<Fournisseur> fournisseurs = tableViewFournisseur.getItems();

        // Clear the existing data
        fournisseurs.clear();

        // Fetch the updated data from the FournisseurService
        fournisseurs.addAll(fournisseurService.findAll());

        // Reapply the data model to the table view
        tableViewFournisseur.setItems(fournisseurs);
    }

    /////////////////MODIFIER//////////////////

    @FXML
    public void handleUpdateButtonAction(ActionEvent event) {
        Fournisseur selectedFournisseur = tableViewFournisseur.getSelectionModel().getSelectedItem();
        if (selectedFournisseur != null) {
            // Update the selected row's data
            selectedFournisseur.setNumSIREN(Integer.parseInt(txtnumSIREN.getText()));
            selectedFournisseur.setNom(txtNom.getText());
            selectedFournisseur.setAdresse(txtAdresse.getText());
            selectedFournisseur.setNumeroTelephone(txtNumeroTelephone.getText());
            selectedFournisseur.setEmail(txtEmail.getText());
            selectedFournisseur.setNumeroCompteBancaire(txtNumeroCompteBancaire.getText());

            // Call the update method in FournisseurService
            FournisseurService fournisseurService = new FournisseurService();
            fournisseurService.update(selectedFournisseur);

            // Refresh the table view to reflect the updated data
            tableViewFournisseur.refresh();

            clearInputFields();
        }
    }


    ///////////////////////////////////

    private void clearInputFields() {
        txtnumSIREN.clear();
        txtNom.clear();
        txtAdresse.clear();
        txtNumeroTelephone.clear();
        txtEmail.clear();
        txtNumeroCompteBancaire.clear();
    }

    ////////////////////////////////////


    private HelloApplication application;

    public void setApplication(HelloApplication application) {
        this.application = application;
    }


    /////////////////////////////////////////

    private void setupButtonActions() {
        tableColumnPaiement.setCellFactory(param -> new TableCell<Fournisseur, Button>() {
            private final Button button = new Button("Paiement");

            {
                // Add button action
                button.setOnAction(event -> {
                    // Get the fournisseur associated with this row
                    Fournisseur fournisseur = getTableRow().getItem();
                    if (fournisseur != null) {
                        // Perform the payment action for the fournisseur
                        performPayment(fournisseur);
                    }
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });

        // Set the button cell factory for the "Supprimer" column
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Fournisseur, Button>() {
            private final Button button = new Button("Supprimer");

            {
                // Add button action
                button.setOnAction(event -> {
                    // Get the fournisseur associated with this row
                    Fournisseur fournisseur = getTableRow().getItem();
                    if (fournisseur != null) {
                        // Prompt the user for confirmation before deleting the fournisseur
                        boolean confirmed = showDeleteConfirmationDialog(fournisseur);
                        if (confirmed) {
                            // Delete the fournisseur from the table and database
                            deleteFournisseur(fournisseur);
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
    }

    private void performPayment(Fournisseur fournisseur) {
        // Add your code here to handle the payment action for the fournisseur
        // You can implement the logic to perform the payment operation
        // such as displaying payment dialog, updating payment status, etc.
    }

    private void openEditFournisseurDialog(Fournisseur fournisseur) {
        // Add your code here to open the edit fournisseur dialog
        // You can implement the logic to display the dialog and allow the user to edit the fournisseur details
    }

    private boolean showDeleteConfirmationDialog(Fournisseur fournisseur) {
        // Add your code here to show a confirmation dialog for deleting the fournisseur
        // You can implement the logic to display a dialog box asking for confirmation
        // and return true if the user confirms the deletion, or false otherwise
        return false; // Replace this with your actual implementation
    }

    private void deleteFournisseur(Fournisseur fournisseur) {
        // Add your code here to delete the fournisseur from the table and database
        // You can implement the logic to remove the fournisseur from the table view
        // and delete it from the underlying data source (e.g., database)
    }

    ///////////////////////////////////

    private void handleRowClick() {
        Fournisseur selectedRow = tableViewFournisseur.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            txtnumSIREN.setText(String.valueOf(selectedRow.getNumSIREN()));
            txtNom.setText(selectedRow.getNom());
            txtAdresse.setText(selectedRow.getAdresse());
            txtNumeroTelephone.setText(selectedRow.getNumeroTelephone());
            txtEmail.setText(selectedRow.getEmail());
            txtNumeroCompteBancaire.setText(selectedRow.getNumeroCompteBancaire());

            // Perform other actions or updates based on the selected row
        }
    }

    //////////////////////////////////////

    private void filterTable(String searchText) {
        List<Fournisseur> fournisseurs = fournisseurService.findAll();
        List<Fournisseur> searchResults = new ArrayList<>();

        // Iterate over the filteredList and add matching suppliers to the searchResults list
        for (Fournisseur supplier : fournisseurs) {
            String numSIREN = Integer.toString(supplier.getNumSIREN());
            if (numSIREN.contains(searchText)) {
                searchResults.add(supplier);
            }
        }

        // Clear the table view
        tableViewFournisseur.getItems().clear();

        // Add the search results to the table view
        tableViewFournisseur.getItems().addAll(searchResults);
    }

    ///////////////Cleare////////////////
    @FXML
    public void handleImageClick(MouseEvent mouseEvent) {
        clearInputFields();
    }
}
