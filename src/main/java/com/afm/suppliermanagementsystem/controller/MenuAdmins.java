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
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MenuAdmins {


    private boolean isAdmin;
    @FXML
    private TextField txtnumIF;
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
    private Button btnUpdate;
    @FXML
    private TableColumn<Fournisseur, Integer> tableColumnnumIF;
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
    private TextField txtSearch;

    private ObservableList<Fournisseur> filteredList;
    private FournisseurService fournisseurService;

    //sql express 2014
    //APRCT & BDRCT

    public boolean isAdmin() {
        return isAdmin;
    }

    public MenuAdmins() {
        fournisseurService = new FournisseurService();
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


    @FXML
    void initialize() {
        btnSave.setStyle("-fx-background-color: #14B8A6;-fx-text-fill: #FFFFFF; -fx-background-radius: 5px; -fx-border-color: #000000; -fx-border-width: 0.5px; -fx-border-radius: 5px;");

        if(isAdmin()==true) {
            System.out.println("UPDATE:"+isAdmin());
            btnUpdate.setStyle("-fx-background-color: #5881F5; -fx-text-fill: #FFFFFF; -fx-background-radius: 5px; -fx-border-color: #000000; -fx-border-width: 0.5px; -fx-border-radius: 5px;");
        } else {
            btnUpdate.setStyle("-fx-background-color: #DBEAFE; -fx-text-fill: #000000; -fx-background-radius: 5px; -fx-border-width: 0.5px;");
        };

        populateFournisseurTable();
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
            tableColumnnumIF.setCellValueFactory(new PropertyValueFactory<>("numIF"));
            tableColumnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            tableColumnAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            tableColumnNumeroTelephone.setCellValueFactory(new PropertyValueFactory<>("numeroTelephone"));
            tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tableColumnNumeroCompteBancaire.setCellValueFactory(new PropertyValueFactory<>("numeroCompteBancaire"));

            setupPaiementColumn();

            setupRemoveColumn();


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
        fournisseur.setNumIF(Integer.parseInt(txtnumIF.getText()));
        fournisseur.setNom(txtNom.getText());
        fournisseur.setAdresse(txtAdresse.getText());
        fournisseur.setNumeroTelephone(txtNumeroTelephone.getText());
        fournisseur.setEmail(txtEmail.getText());
        fournisseur.setNumeroCompteBancaire(txtNumeroCompteBancaire.getText());

        // Check if the fournisseur already exists
        if (fournisseurService.findBynumIF(fournisseur.getNumIF()) != null) {
            showAlert("Fournisseur déjà existant", "Un fournisseur avec le même numéro IF existe déjà.");
            return;
        }

        fournisseurService.save(fournisseur);

        clearInputFields();

        showAlert("Fournisseur ajouté", "Le Fournisseur a été ajouté avec succès.");

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
        ObservableList<Fournisseur> fournisseurs = tableViewFournisseur.getItems();

        fournisseurs.clear();

        fournisseurs.addAll(fournisseurService.findAll());

        tableViewFournisseur.setItems(fournisseurs);
    }

    /////////////////MODIFIER//////////////////

    @FXML
    public void handleUpdateButtonAction(ActionEvent event) {
        Fournisseur selectedFournisseur = tableViewFournisseur.getSelectionModel().getSelectedItem();
        Fournisseur selectedRow = tableViewFournisseur.getSelectionModel().getSelectedItem();

        if (selectedFournisseur != null) {
            if (isAdmin()) {
            selectedFournisseur.setNumIF(Integer.parseInt(txtnumIF.getText()));
            selectedFournisseur.setNom(txtNom.getText());
            selectedFournisseur.setAdresse(txtAdresse.getText());
            selectedFournisseur.setNumeroTelephone(txtNumeroTelephone.getText());
            selectedFournisseur.setEmail(txtEmail.getText());
            selectedFournisseur.setNumeroCompteBancaire(txtNumeroCompteBancaire.getText());

            System.out.println(selectedFournisseur);
            fournisseurService.modif(selectedFournisseur,selectedRow.getNumIF());

            refreshTableView();
            clearInputFields();
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Privilèges d'administrateur requis");
                alert.setHeaderText(null);
                alert.setContentText("Seuls les administrateurs peuvent effectuer la mise à jour.");
                alert.showAndWait();
            }
        }
    }


    /////////////////REMOVE//////////////////



    ///////////////CLEARE///////////////////

    private void clearInputFields() {
        txtnumIF.clear();
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

    ///////////////////////////////////

    private void handleRowClick() {
        Fournisseur selectedRow = tableViewFournisseur.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            txtnumIF.setText(String.valueOf(selectedRow.getNumIF()));
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
            String numIF = Integer.toString(supplier.getNumIF());
            if (numIF.contains(searchText)) {
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

    ////////////////REMOVE///////////////

    private void setupButtonActions() {
    }

    private void setupRemoveColumn() {
        tableColumnREMOVE.setCellFactory(column -> {
            TableCell<Fournisseur, Button> remove = new TableCell<>() {

                final Button removeButton = new Button("Supprimer");

                {

                    if(isAdmin()==false) {
                        tableColumnREMOVE.setStyle("-fx-background-color: #F8F8F8");
                        removeButton.setStyle("-fx-background-color: #FFF1F1;");
                    } else {
                        removeButton.setStyle("-fx-background-color: #EF4444; -fx-text-fill: #FFFFFF;-fx-background-radius: 5px; -fx-border-color: #000000; -fx-border-width: 0.5px; -fx-border-radius: 5px;");
                    }
                    removeButton.setOnAction(event -> {
                        Fournisseur fournisseur = getTableView().getItems().get(getIndex());

                        if(isAdmin()) {
                            // Show a confirmation dialog before deleting
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText(null);
                            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce fournisseur ?");
                            alert.showAndWait()
                                    .filter(response -> response == ButtonType.OK)
                                    .ifPresent(response -> {
                                        fournisseurService.remove(fournisseur);
                                        refreshTableView();
                                        System.out.println("Removing fournisseur: " + fournisseur.getNumIF());
                                    });
                        }else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Privilèges d'administrateur requis");
                            alert.setHeaderText(null);
                            alert.setContentText("Seuls les administrateurs peuvent effectuer la suppression.");
                            alert.showAndWait();
                        }

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

    ////////////Paiement/////////////

    private void setupPaiementColumn() {
        tableColumnPaiement.setCellFactory(column -> {
            TableCell<Fournisseur, Button> paiement = new TableCell<>() {
                final Button paiementButton = new Button("Paiement");

                {

                    paiementButton.setStyle("-fx-background-color: #43BC70;-fx-text-fill: #FFFFFF; -fx-background-radius: 5px; -fx-border-color: #000000; -fx-border-width: 0.5px; -fx-border-radius: 5px;");
                    paiementButton.setOnAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/PaiementManager.fxml"));
                            Parent root = loader.load();
                            //
                            PaiementManager PaiementManagerController = loader.getController();
                            System.out.println("befor go to the  paiement"+ isAdmin());
                            PaiementManagerController.setIsAdminP(isAdmin()); // Set the value as required
                            //
                            PaiementManager paiementManagerController = loader.getController();

                            /*{*/
                                // Get the selected fournisseur object from the table
                                Fournisseur fournisseur = getTableView().getItems().get(getIndex());
                                // Pass the fournisseur object to the PaiementManager controller
                                paiementManagerController.setFournisseur(fournisseur);
                            /*}*/

                            Stage newStage = new Stage();
                            newStage.setResizable(false);
                            newStage.setTitle("Attijari Factoring Manager Maroc");
                            newStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/afm/suppliermanagementsystem/img/logo.png")));
                            newStage.setScene(new Scene(root));
                            newStage.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(paiementButton);
                    }
                }
            };
            return paiement;
        });
    }



///////////////////////////////////////////////////

    @FXML
    private void handleAction1(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();

        // Load the MenuAdmins.fxml and set the isAdmin value
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/MenuAdmis.fxml"));
        Parent root = loader.load();
        MenuAdmins menuAdminsController = loader.getController();
        menuAdminsController.setIsAdmin(isAdmin()); // Set the value as required
        menuAdminsController.initialize();

        Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.setTitle("Attijari Factoring Manager Maroc");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/afm/suppliermanagementsystem/img/logo.png")));

        stage.show();
    }


    @FXML
    private void handleAction2(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();

        // Load the MenuAdmins.fxml and set the isAdmin value
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/Notes.fxml"));
        Parent root = loader.load();
        Notes notesController = loader.getController();
        notesController.setIsAdmin(isAdmin());

        Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void handleAction3(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();

        // Load the PaiementsStatistiques.fxml and get the controller instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/PaiementsStatistiques.fxml"));
        Parent root = loader.load();

        PaiementsStatistiques paiementsStatistiquesController = loader.getController();

        // Set the isAdmin value on the existing controller instance
        System.out.println("handel3 :"+isAdmin);
        paiementsStatistiquesController.setIsAdminPs(isAdmin);


        Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public void handleAction4(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Parent root = FXMLLoader.load(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/InscriptionEtAuthentification.fxml"));


        Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
