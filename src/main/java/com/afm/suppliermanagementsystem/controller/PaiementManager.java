package com.afm.suppliermanagementsystem.controller;

import com.afm.suppliermanagementsystem.model.Fournisseur;
import com.afm.suppliermanagementsystem.model.Paiement;
import com.afm.suppliermanagementsystem.services.PaiementService;
import com.itextpdf.text.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaiementManager {

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

    @FXML
    private TableColumn<Paiement, Button> tableColumnPdf;

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
            setupPDFColumn();

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


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshTableView() {

         ObservableList<Paiement> paiements = tableViewPaiement.getItems();

         paiements.clear();

         paiements.addAll(PaiementService.findAllIF(fournisseur.getNumIF()));

         tableViewPaiement.setItems(paiements);
    }

    /////////////////////PDF1////////////////////////

    private void generateReglementAssurancePDF(Paiement paiement,String numeroCheque) {
        Document document = new Document();

        String[] paiementData = getPaiementData(paiement);
        String setmontant = paiementData[0];
        String setdevise = paiementData[1];
        String setdate = paiementData[2];
        String seteffectue = paiementData[3];
        String setmoyenPaiement = paiementData[4];
        //
        String setnumIF = String.valueOf(fournisseur.getNumIF());
        String setnom = fournisseur.getNom();
        String setnumeroTelephone = fournisseur.getNumeroTelephone();
        String setemail = fournisseur.getEmail();
        String setnumeroCompteBancaire = fournisseur.getNumeroCompteBancaire();

        try {
            // Create a PDF writer
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/PDFs/ReglementAssurance.pdf"));

            // Open the document
            document.open();

            // Create a new paragraph for the content
            Paragraph contentParagraph = new Paragraph();
            contentParagraph.setAlignment(Element.ALIGN_LEFT);
            contentParagraph.setSpacingBefore(10);

            // Create a font for the content
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 15);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16);

            // Add the content to the paragraph
            Font boldFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            Font boldUnderlineFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD | Font.UNDERLINE);


            // Add the content to the paragraph

// Create a new paragraph for the right-aligned phrase
            Paragraph rightAlignedParagraph = new Paragraph();
            rightAlignedParagraph.setAlignment(Element.ALIGN_RIGHT);
            Phrase phrase = new Phrase(getCurrentDate(), contentFont);
            rightAlignedParagraph.add(phrase);
            contentParagraph.add(rightAlignedParagraph);
////////////
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Objet : ", boldUnderlineFont));
            contentParagraph.add(new Phrase("Règlement DE (T4) 2022 DE L'ASSURANCE COMPLIMENTAIRE 2022.", boldFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("M", boldFont));
            contentParagraph.add(new Phrase("onsieur,", contentFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Merci de trouver ci-joint le chèque N°", contentFont1));
            contentParagraph.add(new Phrase(numeroCheque+" d'un montant", contentFont1));
            contentParagraph.add(new Phrase(" "+setmontant+"  dhs  ", boldFont));
            contentParagraph.add(new Phrase("relatif au règlement de", contentFont1));
            contentParagraph.add(new Phrase("  (T4)2022  ", contentFont1));
            contentParagraph.add(new Phrase("de l'assurance complémentaire.", contentFont1));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("C", boldFont));
            contentParagraph.add(new Phrase("ordialement.", contentFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
// Create a new paragraph for the right-aligned phrase
            Paragraph rightAlignedParagraph4 = new Paragraph();
            rightAlignedParagraph4.setAlignment(Element.ALIGN_RIGHT);
            Phrase phrase4 = new Phrase("ATTIJARI FACTORING MAROC", boldFont);
            rightAlignedParagraph4.add(phrase4);
            contentParagraph.add(rightAlignedParagraph4);

            document.add(contentParagraph);

            document.close();

            System.out.println("PDF generated successfully.");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////PDF2//////////////////////////////

    private void generateOrdereVirementPDF(Paiement paiement, String banqueNom, String libelle) {
        Document document = new Document();

        String[] paiementData = getPaiementData(paiement);
        String setmontant = paiementData[0];
        String setdevise = paiementData[1];
        String setdate = paiementData[2];
        String seteffectue = paiementData[3];
        String setmoyenPaiement = paiementData[4];
        //
        String setnumIF = String.valueOf(fournisseur.getNumIF());
        String setnom = fournisseur.getNom();
        String setnumeroTelephone = fournisseur.getNumeroTelephone();
        String setemail = fournisseur.getEmail();
        String setnumeroCompteBancaire = fournisseur.getNumeroCompteBancaire();

        try {

            // Create a PDF writer
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/PDFs/OrdereVirement.pdf"));

            // Open the document
            document.open();


            Paragraph contentParagraph = new Paragraph();
            contentParagraph.setAlignment(Element.ALIGN_LEFT);
            contentParagraph.setSpacingBefore(10);

            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);
            Font boldFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font boldUnderlineFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD | Font.UNDERLINE);



// Create a new paragraph for the right-aligned phrase
            Paragraph rightAlignedParagraph = new Paragraph();
            rightAlignedParagraph.setAlignment(Element.ALIGN_RIGHT);
            Phrase phrase = new Phrase(getCurrentDate(), contentFont);
            rightAlignedParagraph.add(phrase);
            contentParagraph.add(rightAlignedParagraph);

            contentParagraph.add(Chunk.NEWLINE);
////////////
            Paragraph rightAlignedParagraph1 = new Paragraph();
            rightAlignedParagraph1.setAlignment(Element.ALIGN_RIGHT);
            Paragraph rightAlignedParagraph2 = new Paragraph();
            rightAlignedParagraph2.setAlignment(Element.ALIGN_RIGHT);

            Phrase phrase1 = new Phrase("ATTIJARIWAFA Bank", boldFont);
            Phrase phrase2 = new Phrase("Centre d'Affaires.2001", boldFont);

            rightAlignedParagraph1.add(phrase1);
            rightAlignedParagraph2.add(phrase2);

            contentParagraph.add(rightAlignedParagraph1);
            contentParagraph.add(rightAlignedParagraph2);
////////////
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Objet : ", boldUnderlineFont));
            contentParagraph.add(new Phrase("Ordre de Virement", boldFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("M", boldFont));
            contentParagraph.add(new Phrase("onsieur,", contentFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("P", boldFont));
            contentParagraph.add(new Phrase("ar le débit de notre compte N O 200 H 170 000, nous vous prions d'exécuter les virements ci-après.", contentFont1));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Bénéficiaires    : ", boldFont));
            contentParagraph.add(new Phrase(setnom, boldFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Banque            : ", boldFont));
            contentParagraph.add(new Phrase(banqueNom , contentFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("N° Compte           : ", boldFont));
            contentParagraph.add(new Phrase(setnumeroCompteBancaire, boldFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Montant (DHS) : ", boldFont));
            contentParagraph.add(new Phrase(setmontant+" Dhs.", boldFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Libellé              : ", boldFont));
            contentParagraph.add(new Phrase(libelle +"  "+setmontant+""+setdevise+".", boldFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("C", boldFont));
            contentParagraph.add(new Phrase("ordialement.", contentFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
// Create a new paragraph for the right-aligned phrase
            Paragraph rightAlignedParagraph4 = new Paragraph();
            rightAlignedParagraph4.setAlignment(Element.ALIGN_RIGHT);
            Phrase phrase4 = new Phrase("ATTIJARI FACTORING MAROC", boldFont);
            rightAlignedParagraph4.add(phrase4);
            contentParagraph.add(rightAlignedParagraph4);

            // Add the content paragraph to the document
            document.add(contentParagraph);

            // Close the document
            document.close();

            System.out.println("PDF generated successfully.");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /*BUTTON GET PDFs*/

    private void setupPDFColumn() {
        tableColumnPdf.setCellFactory(column -> {
            TableCell<Paiement, Button> getPDF = new TableCell<>() {
                final Button getPDFButton = new Button("getPDF");

                {
                    getPDFButton.setOnAction(event -> {
                        Paiement paiement = getTableView().getItems().get(getIndex());
                        String[] formData = new String[0];
                        try {
                            formData = displayFormPopup();
                            // Continue with the rest of your code using the formData
                        } catch (Exception e) {
                            // Handle the exception appropriately
                            e.printStackTrace();
                        }

                        if (formData != null) {
                            String banqueNom = formData[0];
                            String libelle = formData[1];
                            String numeroCheque = formData[2];

                            generateReglementAssurancePDF(paiement,numeroCheque);
                            generateOrdereVirementPDF(paiement, banqueNom, libelle);

                            loadGetPdfs(event);
                        }
                    });
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(getPDFButton);
                    }
                }
            };
            return getPDF;
        });
    }


    ///////////////////// FORMDATA //////////////////////////

    private String[] displayFormPopup() throws Exception {
        // Create a new stage for the popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("ajouter des nouvelles données");

        // Create UI controls for the form
        TextField banqueField = new TextField();
        banqueField.setPromptText("Le nom du banque *:");

        TextField libelleField = new TextField();
        libelleField.setPromptText("Libellé *:");

        TextField numeroChequeField = new TextField();
        numeroChequeField.setPromptText("Numéro de chèque *:");

        Button submitButton = new Button("GetPdfs");

        // Create a SimpleObjectProperty to hold the form data
        SimpleObjectProperty<String[]> formDataProperty = new SimpleObjectProperty<>();


        submitButton.setOnAction(event -> {
            // Retrieve the form data from the UI controls
            String banqueNom = banqueField.getText();
            String libelle = libelleField.getText();
            String numeroCheque = numeroChequeField.getText();

            // Check if any of the fields are empty
            if (banqueNom.isEmpty() || libelle.isEmpty() || numeroCheque.isEmpty()) {
                // Display an alert message in French
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Champs obligatoires vides");
                alert.setContentText("Veuillez remplir tous les champs obligatoires.");
                alert.showAndWait();
                return; // Return to exit the event handler
            }

            // Create a String array with the retrieved data
            String[] formData = { banqueNom, libelle, numeroCheque };

            // Set the form data to the SimpleObjectProperty
            formDataProperty.set(formData);

            // Close the popup
            popupStage.close();
        });

        // Create the layout for the form (VBox, GridPane, etc.)
        VBox formLayout = new VBox(15);
        formLayout.getChildren().addAll( banqueField,  libelleField, numeroChequeField, submitButton);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(15));

        // Create the scene and set it in the popup stage
        Scene popupScene = new Scene(formLayout);
        popupStage.setScene(popupScene);

        // Show the popup and wait for it to close
        popupStage.showAndWait();

        // Return the form data from the SimpleObjectProperty
        return formDataProperty.get();
    }


    ////////////////////////////////////////////////////////


    private void loadGetPdfs(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/GetPdfs.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setResizable(false);
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            newStage.setX((primScreenBounds.getWidth() - newStage.getWidth()) / 2);
            newStage.setY((primScreenBounds.getHeight() - newStage.getHeight()) / 4);

            newStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*getCurrentDate*/
    public String getCurrentDate() {

        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("fr"));

        String formattedDate = currentDate.format(formatter);

        String fullString = "Casablanca, " + formattedDate;

        return fullString;
    }

    /*getPaiementData*/
    public String[] getPaiementData(Paiement paiement) {
        String montantRow = String.valueOf(paiement.getMontant());
        String deviseRow = paiement.getDevise();
        String dateRow = String.valueOf(paiement.getDate());
        String effectueRow = String.valueOf(paiement.isEffectue());
        String moyenPaiementRow = String.valueOf(paiement.getMoyenPaiement());

        return new String[]{montantRow, deviseRow, dateRow, effectueRow, moyenPaiementRow};
    }

}







//OLD VERSION :
/*package com.afm.suppliermanagementsystem.controller;

import com.afm.suppliermanagementsystem.model.Fournisseur;
import com.afm.suppliermanagementsystem.model.Paiement;
import com.afm.suppliermanagementsystem.services.CompteService;
import com.afm.suppliermanagementsystem.services.PaiementService;
import com.itextpdf.text.*;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaiementManager {

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

    @FXML
    private TableColumn<Paiement, Button> tableColumnPdf;

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
            setupPDFColumn();

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


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshTableView() {

         ObservableList<Paiement> paiements = tableViewPaiement.getItems();

         paiements.clear();

         paiements.addAll(PaiementService.findAllIF(fournisseur.getNumIF()));

         tableViewPaiement.setItems(paiements);
    }

    /////////////////////PDF1////////////////////////

    private void generateReglementAssurancePDF(Paiement paiement) {
        Document document = new Document();

        String[] paiementData = getPaiementData(paiement);
        String setmontant = paiementData[0];
        String setdevise = paiementData[1];
        String setdate = paiementData[2];
        String seteffectue = paiementData[3];
        String setmoyenPaiement = paiementData[4];
        //
        String setnumIF = String.valueOf(fournisseur.getNumIF());
        String setnom = fournisseur.getNom();
        String setnumeroTelephone = fournisseur.getNumeroTelephone();
        String setemail = fournisseur.getEmail();
        String setnumeroCompteBancaire = fournisseur.getNumeroCompteBancaire();

        try {
            // Create a PDF writer
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/PDFs/ReglementAssurance.pdf"));

            // Open the document
            document.open();

            // Create a new paragraph for the content
            Paragraph contentParagraph = new Paragraph();
            contentParagraph.setAlignment(Element.ALIGN_LEFT);
            contentParagraph.setSpacingBefore(10);

            // Create a font for the content
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 15);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16);

            // Add the content to the paragraph
            Font boldFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            Font boldUnderlineFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD | Font.UNDERLINE);


            // Add the content to the paragraph

// Create a new paragraph for the right-aligned phrase
            Paragraph rightAlignedParagraph = new Paragraph();
            rightAlignedParagraph.setAlignment(Element.ALIGN_RIGHT);
            Phrase phrase = new Phrase(getCurrentDate(), contentFont);
            rightAlignedParagraph.add(phrase);
            contentParagraph.add(rightAlignedParagraph);
////////////
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Objet : ", boldUnderlineFont));
            contentParagraph.add(new Phrase("Règlement DE (T4) 2022 DE L'ASSURANCE COMPLIMENTAIRE 2022.", boldFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("M", boldFont));
            contentParagraph.add(new Phrase("onsieur,", contentFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Merci de trouver ci-joint le chèque N°", contentFont1));
            contentParagraph.add(new Phrase("274762 d'un montant", contentFont1));
            contentParagraph.add(new Phrase(" "+setmontant+"  dhs  ", boldFont));
            contentParagraph.add(new Phrase("relatif au règlement de", contentFont1));
            contentParagraph.add(new Phrase("  (T4)2022  ", contentFont1));
            contentParagraph.add(new Phrase("de l'assurance complémentaire.", contentFont1));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("C", boldFont));
            contentParagraph.add(new Phrase("ordialement.", contentFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
// Create a new paragraph for the right-aligned phrase
            Paragraph rightAlignedParagraph4 = new Paragraph();
            rightAlignedParagraph4.setAlignment(Element.ALIGN_RIGHT);
            Phrase phrase4 = new Phrase("ATTIJARI FACTORING MAROC", boldFont);
            rightAlignedParagraph4.add(phrase4);
            contentParagraph.add(rightAlignedParagraph4);

            document.add(contentParagraph);

            document.close();

            System.out.println("PDF generated successfully.");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////PDF2//////////////////////////////

    private void generateOrdereVirementPDF(Paiement paiement) {
        Document document = new Document();

        String[] paiementData = getPaiementData(paiement);
        String setmontant = paiementData[0];
        String setdevise = paiementData[1];
        String setdate = paiementData[2];
        String seteffectue = paiementData[3];
        String setmoyenPaiement = paiementData[4];
        //
        String setnumIF = String.valueOf(fournisseur.getNumIF());
        String setnom = fournisseur.getNom();
        String setnumeroTelephone = fournisseur.getNumeroTelephone();
        String setemail = fournisseur.getEmail();
        String setnumeroCompteBancaire = fournisseur.getNumeroCompteBancaire();

        try {

            // Create a PDF writer
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/PDFs/OrdereVirement.pdf"));

            // Open the document
            document.open();


            Paragraph contentParagraph = new Paragraph();
            contentParagraph.setAlignment(Element.ALIGN_LEFT);
            contentParagraph.setSpacingBefore(10);

            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);
            Font boldFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font boldUnderlineFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD | Font.UNDERLINE);



// Create a new paragraph for the right-aligned phrase
            Paragraph rightAlignedParagraph = new Paragraph();
            rightAlignedParagraph.setAlignment(Element.ALIGN_RIGHT);
            Phrase phrase = new Phrase(getCurrentDate(), contentFont);
            rightAlignedParagraph.add(phrase);
            contentParagraph.add(rightAlignedParagraph);

            contentParagraph.add(Chunk.NEWLINE);
////////////
            Paragraph rightAlignedParagraph1 = new Paragraph();
            rightAlignedParagraph1.setAlignment(Element.ALIGN_RIGHT);
            Paragraph rightAlignedParagraph2 = new Paragraph();
            rightAlignedParagraph2.setAlignment(Element.ALIGN_RIGHT);

            Phrase phrase1 = new Phrase("ATTIJARIWAFA Bank", boldFont);
            Phrase phrase2 = new Phrase("Centre d'Affaires.2001", boldFont);

            rightAlignedParagraph1.add(phrase1);
            rightAlignedParagraph2.add(phrase2);

            contentParagraph.add(rightAlignedParagraph1);
            contentParagraph.add(rightAlignedParagraph2);
////////////
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Objet : ", boldUnderlineFont));
            contentParagraph.add(new Phrase("Ordre de Virement", boldFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("M", boldFont));
            contentParagraph.add(new Phrase("onsieur,", contentFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("P", boldFont));
            contentParagraph.add(new Phrase("ar le débit de notre compte N O 200 H 170 000, nous vous prions d'exécuter les virements ci-après.", contentFont1));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Bénéficiaires    : ", boldFont));
            contentParagraph.add(new Phrase(setnom, boldFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Banque            : ", boldFont));
            contentParagraph.add(new Phrase("CREDIT AGRICOLE, CASA ZANITH", contentFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("N° Compte           : ", boldFont));
            contentParagraph.add(new Phrase(setnumeroCompteBancaire, boldFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Montant (DHS) : ", boldFont));
            contentParagraph.add(new Phrase(setmontant+" Dhs.", boldFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("Libellé              : ", boldFont));
            contentParagraph.add(new Phrase("F0 2300248"+"  "+setmontant+""+setdevise+".", boldFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(new Phrase("C", boldFont));
            contentParagraph.add(new Phrase("ordialement.", contentFont));
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
            contentParagraph.add(Chunk.NEWLINE);
// Create a new paragraph for the right-aligned phrase
            Paragraph rightAlignedParagraph4 = new Paragraph();
            rightAlignedParagraph4.setAlignment(Element.ALIGN_RIGHT);
            Phrase phrase4 = new Phrase("ATTIJARI FACTORING MAROC", boldFont);
            rightAlignedParagraph4.add(phrase4);
            contentParagraph.add(rightAlignedParagraph4);

            // Add the content paragraph to the document
            document.add(contentParagraph);

            // Close the document
            document.close();

            System.out.println("PDF generated successfully.");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    BUTTON GET PDFs

    private void setupPDFColumn() {
        tableColumnPdf.setCellFactory(column -> {
            TableCell<Paiement, Button> getPDF = new TableCell<>() {
                final Button getPDFButton = new Button("getPDF");

                {
                    getPDFButton.setOnAction(event -> {

                        Paiement paiement = getTableView().getItems().get(getIndex());
                        generateReglementAssurancePDF(paiement);
                        generateOrdereVirementPDF(paiement);

                        loadGetPdfs(event);

                    });
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(getPDFButton);
                    }
                }
            };
            return getPDF;
        });
    }

    private void loadGetPdfs(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/GetPdfs.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setResizable(false);
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            newStage.setX((primScreenBounds.getWidth() - newStage.getWidth()) / 2);
            newStage.setY((primScreenBounds.getHeight() - newStage.getHeight()) / 4);

            newStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*getCurrentDate*/
 /*   public String getCurrentDate() {

        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("fr"));

        String formattedDate = currentDate.format(formatter);

        String fullString = "Casablanca, " + formattedDate;

        return fullString;
    }
*/
    /*getPaiementData*/
  /*  public String[] getPaiementData(Paiement paiement) {
        String montantRow = String.valueOf(paiement.getMontant());
        String deviseRow = paiement.getDevise();
        String dateRow = String.valueOf(paiement.getDate());
        String effectueRow = String.valueOf(paiement.isEffectue());
        String moyenPaiementRow = String.valueOf(paiement.getMoyenPaiement());

        return new String[]{montantRow, deviseRow, dateRow, effectueRow, moyenPaiementRow};
    }

}*/
