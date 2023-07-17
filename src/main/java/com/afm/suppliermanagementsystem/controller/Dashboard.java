package com.afm.suppliermanagementsystem.controller;

import com.afm.suppliermanagementsystem.model.Compte;
import com.afm.suppliermanagementsystem.services.CompteService;
import com.afm.suppliermanagementsystem.services.PasswordHasher;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtTelephone;
    @FXML
    private TextField txtPseudoNom;
    @FXML
    private TextField txtCIN;
    @FXML
    private TextField txtMotPass;
    @FXML
    private TextField txtAdresse;
    @FXML
    private CheckBox Etat;
    @FXML
    private CheckBox IsAdmin;
    @FXML
    private TableView<Compte> tableViewCompte;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableColumn<Compte, String> tableColumnCIN;
    @FXML
    private TableColumn<Compte, String> tableColumnNom;
    @FXML
    private TableColumn<Compte, String> tableColumnPrenom;
    @FXML
    private TableColumn<Compte, String> tableColumnTelephone;
    @FXML
    private TableColumn<Compte, String> tableColumnPseudoNom;
    @FXML
    private TableColumn<Compte, String> tableColumnAdresse;
    @FXML
    private TableColumn<Compte, Integer> tableColumnEtat;
    @FXML
    private TableColumn<Compte, Integer> tableColumnIsAdmin;
    @FXML
    private TableColumn<Compte, Button> tableColumnREMOVE;
    @FXML
    private Label lblStatus;
    @FXML
    private Button goToButton;
    @FXML
    private TextField txtSearch;

    private ObservableList<Compte> compteList;
    private ObservableList<Compte> filteredList;
    private CompteService compteService;

    public Dashboard() {
        compteService = new CompteService();
    }

    @FXML
    private void initialize() {

        populateCompteTable();

        tableViewCompte.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                handleRowClick();
            }
        });


        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterTable(newValue);
            }
        });
    }

    //////////////////////////////////////

    private void filterTable(String searchText) {
        List<Compte> comptes = CompteService.findAll();
        List<Compte> searchResults = new ArrayList<>();

        // Iterate over the filteredList and add matching suppliers to the searchResults list
        for (Compte compte : comptes) {
            String Cin = compte.getCin();
            if (Cin.contains(searchText)) {
                searchResults.add(compte);
            }
        }

        // Clear the table view
        tableViewCompte.getItems().clear();

        // Add the search results to the table view
        tableViewCompte.getItems().addAll(searchResults);
    }


    public void populateCompteTable() {
        List<Compte> comptes = compteService.findAll();

        if (comptes != null) {
            tableColumnCIN.setCellValueFactory(new PropertyValueFactory<>("cin"));
            tableColumnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            tableColumnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            tableColumnTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
            tableColumnPseudoNom.setCellValueFactory(new PropertyValueFactory<>("pseudo_nom"));
            tableColumnAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            tableColumnEtat.setCellValueFactory(new PropertyValueFactory<>("Etat"));
            tableColumnIsAdmin.setCellValueFactory(new PropertyValueFactory<>("IsAdmin"));

            setupRemoveColumn();

            compteList = FXCollections.observableArrayList(comptes);
            tableViewCompte.setItems(compteList);
        } else {
            System.out.println("No data found.");
        }
    }


    @FXML
    private void handleSaveButtonAction() {
        Compte compte = new Compte();
        compte.setNom(txtNom.getText());
        compte.setPrenom(txtPrenom.getText());
        compte.setTelephone(txtTelephone.getText());
        compte.setPseudoNom(txtPseudoNom.getText());
        compte.setCin(txtCIN.getText());
        compte.setMotPass(PasswordHasher.hashPassword(txtMotPass.getText()));
        compte.setAdresse(txtAdresse.getText());
        compte.setEtat(Etat.isSelected() ? 1 : 0);
        compte.setIsAdmin(IsAdmin.isSelected() ? 1 : 0);


        compteService.save(compte);

        clearInputFields();

        showAlert("Compte ajouté", "Le compte a été ajouté avec succès.");

        refreshTableView();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshTableView() {
        ObservableList<Compte> comptes = tableViewCompte.getItems();
        comptes.clear();
        comptes.addAll(compteService.findAll());
        tableViewCompte.setItems(comptes);
    }

    @FXML
    public void handleUpdateButtonAction(ActionEvent event) {
        Compte selectedCompte = tableViewCompte.getSelectionModel().getSelectedItem();
        if (selectedCompte != null) {
            selectedCompte.setNom(txtNom.getText());
            selectedCompte.setPrenom(txtPrenom.getText());
            selectedCompte.setTelephone(txtTelephone.getText());
            selectedCompte.setPseudoNom(txtPseudoNom.getText());
            selectedCompte.setCin(txtCIN.getText());
            selectedCompte.setMotPass(PasswordHasher.hashPassword(txtMotPass.getText()));
            selectedCompte.setAdresse(txtAdresse.getText());
            selectedCompte.setEtat(Etat.isSelected() ? 1 : 0);
            selectedCompte.setIsAdmin(IsAdmin.isSelected() ? 1 : 0);

            compteService.update(selectedCompte);

            tableViewCompte.refresh();

            clearInputFields();
        }
    }

    private void clearInputFields() {
        txtNom.clear();
        txtPrenom.clear();
        txtTelephone.clear();
        txtPseudoNom.clear();
        txtCIN.clear();
        txtMotPass.clear();
        txtAdresse.clear();
        Etat.setSelected(true);
        IsAdmin.setSelected(false);
    }

    private void setupRemoveColumn() {
        tableColumnREMOVE.setCellFactory(column -> {
            TableCell<Compte, Button> remove = new TableCell<>() {
                final Button removeButton = new Button("Supprimer");

                {
                    removeButton.setOnAction(event -> {
                        Compte compte = getTableView().getItems().get(getIndex());
                        compteService.deleteCin(compte);
                        compteList.remove(compte);
                        tableViewCompte.refresh();
                        System.out.println("Removing compte: " + compte.getCin());
                    });
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(removeButton);
                    }
                }
            };
            return remove;
        });
    }


    @FXML
    private void handleRowClick() {
        Compte selectedCompte = tableViewCompte.getSelectionModel().getSelectedItem();
        if (selectedCompte != null) {
            txtNom.setText(selectedCompte.getNom());
            txtPrenom.setText(selectedCompte.getPrenom());
            txtTelephone.setText(selectedCompte.getTelephone());
            txtPseudoNom.setText(selectedCompte.getPseudoNom());
            txtCIN.setText(selectedCompte.getCin());
            txtMotPass.setText(selectedCompte.getMotPass());
            txtAdresse.setText(selectedCompte.getAdresse());
            Etat.setSelected(selectedCompte.getEtat() == 1);
            IsAdmin.setSelected(selectedCompte.getIsAdmin() == 1);
        }
    }

    @FXML
    public void handleImageClick(MouseEvent mouseEvent) {
        clearInputFields();
    }

    @FXML
    private void handleSearchButtonAction() {
        String searchQuery = txtSearch.getText().trim();
        if (!searchQuery.isEmpty()) {
            filteredList = compteList.filtered(compte ->
                    compte.getCin().contains(searchQuery)
            );
            tableViewCompte.setItems(filteredList);
        } else {
            tableViewCompte.setItems(compteList);
        }
    }

    /////////////////////////

    @FXML
    private void handleGoToButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/MenuAdmis.fxml"));


        Stage stage = (Stage) goToButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);

        stage.show();
    }



}
