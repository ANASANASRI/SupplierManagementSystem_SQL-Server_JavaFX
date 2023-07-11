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


/*public class MainApp extends Application {
    // ... JavaFX application setup code ...

    @Override
    public void start(Stage primaryStage) {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("your_fxml_file.fxml"));
        Parent root = loader.load();

        // Get the controller instance
        YourControllerClass controller = loader.getController();

        // Set up the button click event handler
        Button yourButton = (Button) root.lookup("#yourButtonId");
        yourButton.setOnAction(event -> controller.showFormPopup());

        // Create the scene and set it in the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

public class YourControllerClass {
    @FXML
    private void handleButtonClick(ActionEvent event) {
        showFormPopup();
    }

    public void showFormPopup() {
        // Create a new stage for the popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Form Popup");

        // Create UI controls for the form
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            // Retrieve the entered information from the form
            String name = nameField.getText();
            String email = emailField.getText();

            // Perform desired actions or process the retrieved information
            // ...

            // Close the popup
            popupStage.close();
        });

        // Create the layout for the form
        VBox formLayout = new VBox(10);
        formLayout.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, submitButton);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(10));

        // Create the scene and set it in the popup stage
        Scene popupScene = new Scene(formLayout);
        popupStage.setScene(popupScene);

        // Show the popup
        popupStage.showAndWait();
    }
}
*/
