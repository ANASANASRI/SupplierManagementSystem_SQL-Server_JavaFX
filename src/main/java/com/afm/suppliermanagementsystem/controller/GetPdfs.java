package com.afm.suppliermanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class GetPdfs {

    @FXML
    private WebView webView;
    @FXML
    private WebView webView1;

    @FXML
    private WebView webView2;

    public void initialize() {
        WebEngine webEngine1 = webView1.getEngine();
        String pdfPath1 = "/PDFs/OrdereVirement.pdf";
        String fullPath1 = getClass().getResource(pdfPath1).toExternalForm();
        webEngine1.load(fullPath1);

        WebEngine webEngine2 = webView2.getEngine();
        String pdfPath2 = "/PDFs/ReglementAssurance.pdf";
        String fullPath2 = getClass().getResource(pdfPath2).toExternalForm();
        webEngine2.load(fullPath2);
    }

}
