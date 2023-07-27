package com.afm.suppliermanagementsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class GetChequePdf implements Initializable {

    @FXML
    private ImageView pdfImageView;

    private String chequePdfFilePath = "/PDFs/ReglementAssurance.pdf";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayPDF();
    }

    private void displayPDF() {
        try {
            PDDocument document = PDDocument.load(getClass().getResourceAsStream(chequePdfFilePath));

            PDFRenderer renderer = new PDFRenderer(document);

            BufferedImage bufferedImage = renderer.renderImage(0);

            Image image = convertToJavaFXImage(bufferedImage);

            pdfImageView.setImage(image);

            document.close();
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
    private void displayVirementPDF(ActionEvent event) {
        try {
            File pdfFile = new File("src/main/resources/PDFs/ReglementAssurance.pdf");
            Desktop.getDesktop().open(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
            showPDFLoadErrorAlert();
        }
    }

    private void showPDFLoadErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("PDF Load Error");
        alert.setHeaderText(null);
        alert.setContentText("An error occurred while loading the PDF.");
        alert.showAndWait();
    }
}

