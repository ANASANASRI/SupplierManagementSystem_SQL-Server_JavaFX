package com.afm.suppliermanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
public class GetPdfs {
    @FXML
    private Button printPDF1Button;

    @FXML
    private Button printPDF2Button;

    @FXML
    private WebView webView1;

    @FXML
    private WebView webView2;

    @FXML
    public void initialize() {
        // Initialize the controller
    }

    @FXML
    private void handlePrintPDF1() {
        // Implement the logic to print PDF 1
        // You can use the webView1 to get the content of PDF 1 and print it
    }

    @FXML
    private void handlePrintPDF2() {
        // Implement the logic to print PDF 2
        // You can use the webView2 to get the content of PDF 2 and print it
    }

    public void loadPDF1(String path) {
        webView1.getEngine().load(path);
    }

    public void loadPDF2(String path) {
        webView2.getEngine().load(path);
    }
}
