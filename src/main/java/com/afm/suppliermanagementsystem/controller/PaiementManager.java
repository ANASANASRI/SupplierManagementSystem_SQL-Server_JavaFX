package com.afm.suppliermanagementsystem.controller;

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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

            // Add the content paragraph to the document
            document.add(contentParagraph);

            // Close the document
            document.close();

            System.out.println("PDF generated successfully.");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////PDF2//////////////////////////////

    private void generateOrdereVirementPDF(Paiement paiement) {
        // Create a PDF document
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


            // Add new paragraph
            Paragraph contentParagraph = new Paragraph();
            contentParagraph.setAlignment(Element.ALIGN_LEFT);
            contentParagraph.setSpacingBefore(10);

            // Create a font for the content
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);

            // Add the content to the paragraph
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
// Create a new paragraph for the right-aligned phrases
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


    /*BUTTON GET PDFs*/

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
            Stage fenetre = (Stage) ((Node) event.getSource()).getScene().getWindow();
            fenetre.setScene(scene);
            fenetre.setResizable(false);
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            fenetre.setX((primScreenBounds.getWidth() - fenetre.getWidth()) / 2);
            fenetre.setY((primScreenBounds.getHeight() - fenetre.getHeight()) / 4);

            fenetre.show();
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
