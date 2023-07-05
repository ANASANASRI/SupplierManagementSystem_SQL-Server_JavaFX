package com.afm.suppliermanagementsystem;

import com.afm.suppliermanagementsystem.controller.InscriptionEtAuthentification;
import com.afm.suppliermanagementsystem.controller.MenuAdmins;
import com.afm.suppliermanagementsystem.dao.imp.DB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class HelloApplication extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            DB.getConnection();
            System.out.println("Welcome to our AFM app!");
            showLoginView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }

    public void showLoginView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/InscriptionEtAuthentification.fxml"));
        Parent root = loader.load();
        InscriptionEtAuthentification authentification = loader.getController();
        authentification.setApplication(this);

        primaryStage.setTitle("Attijari Factoring Manager Maroc");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/afm/suppliermanagementsystem/img/logo.png")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}