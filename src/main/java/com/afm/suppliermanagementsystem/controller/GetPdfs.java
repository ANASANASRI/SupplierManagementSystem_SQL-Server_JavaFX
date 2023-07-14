package com.afm.suppliermanagementsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class GetPdfs implements Initializable {

    @FXML
    private VBox rootPane;
    @FXML
    private ImageView pdfImageView;

    @FXML
    private ImageView pdfImageView1;

    private String pdfFilePath = "/PDFs/ReglementAssurance.pdf";
    private String virementPdfFilePath = "/PDFs/OrdereVirement.pdf";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayPDF();
    }

    private void displayPDF() {
        try {
            PDDocument document1 = PDDocument.load(getClass().getResourceAsStream(pdfFilePath));
            PDDocument document2 = PDDocument.load(getClass().getResourceAsStream(virementPdfFilePath));

            PDFRenderer renderer1 = new PDFRenderer(document1);
            PDFRenderer renderer2 = new PDFRenderer(document2);

            BufferedImage bufferedImage1 = renderer1.renderImage(0);
            BufferedImage bufferedImage2 = renderer2.renderImage(0);

            Image image1 = convertToJavaFXImage(bufferedImage1);
            Image image2 = convertToJavaFXImage(bufferedImage2);

            pdfImageView.setImage(image1);
            pdfImageView1.setImage(image2);

            document1.close();
            document2.close();
        } catch (IOException e) {
            System.out.println(e);
            showPDFLoadErrorAlert();
        }
    }

    private Image convertToJavaFXImage(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return new Image(byteArrayInputStream);
    }

    @FXML
    private void displayReglementPDF(ActionEvent event) {
        try {
            File pdfFile = new File("src/main/resources/PDFs/ReglementAssurance.pdf");
            Desktop.getDesktop().open(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
            showPDFLoadErrorAlert();
        }
    }

    @FXML
    private void displayVirementPDF(ActionEvent event) {
        try {
            File pdfFile = new File("src/main/resources/PDFs/OrdereVirement.pdf");
            Desktop.getDesktop().open(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
            showPDFLoadErrorAlert();
        }
    }



    private void showPDFLoadErrorAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("PDF Load Error");
        alert.setHeaderText(null);
        alert.setContentText("An error occurred while loading the PDF.");
        alert.showAndWait();
    }
}
