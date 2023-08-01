package com.afm.suppliermanagementsystem.controller;

import com.afm.suppliermanagementsystem.dao.imp.DB;
import com.afm.suppliermanagementsystem.model.Paiement;
import com.afm.suppliermanagementsystem.services.PaiementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaiementsStatistiques implements Initializable {

    @FXML
    private StackedBarChart<String, Number> stackedBarChart;

    @FXML
    private PieChart pieChart;

    @FXML
    private PieChart pieChart1;

    private boolean isAdmin;

    public boolean getisAdmin() {
        return this.isAdmin;
    }

    public void setIsAdminPs(boolean admin) {
        this.isAdmin=admin;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Call the method to populate the charts with data
        initializeCharts();
    }

    // Method to populate the charts with data
    private void initializeCharts() {



        // Get real data from the database using DB.getConnection()
        List<Paiement> paiements = PaiementService.findAll();


        // Pour le StackedBarChart
        int nombreCheques = 0;
        double sommeTotaleCheques = 0;
        double montantMoyenCheques = 0;

        int nombreVirements = 0;
        double sommeTotaleVirements = 0;
        double montantMoyenVirements = 0;

        for (Paiement paiement : paiements) {
            if (paiement.getMoyenPaiement() == Paiement.MoyenPaiement.CHEQUE) {
                nombreCheques++;
                sommeTotaleCheques += paiement.getMontant();
            } else if (paiement.getMoyenPaiement() == Paiement.MoyenPaiement.VIREMENT) {
                nombreVirements++;
                sommeTotaleVirements += paiement.getMontant();
            }
        }

        montantMoyenCheques = nombreCheques == 0 ? 0 : sommeTotaleCheques / nombreCheques;
        montantMoyenVirements = nombreVirements == 0 ? 0 : sommeTotaleVirements / nombreVirements;

        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.setName("CHEQUE");
        barSeries.getData().add(new XYChart.Data<>("Somme Totale", sommeTotaleCheques));
        barSeries.getData().add(new XYChart.Data<>("Montant Moyen", montantMoyenCheques));

        XYChart.Series<String, Number> barSeries2 = new XYChart.Series<>();
        barSeries2.setName("VIREMENT");
        barSeries2.getData().add(new XYChart.Data<>("Somme Totale", sommeTotaleVirements));
        barSeries2.getData().add(new XYChart.Data<>("Montant Moyen", montantMoyenVirements));

        stackedBarChart.getData().addAll(barSeries, barSeries2);


        // Sample data for the PieChart
        int totalCheque = 0;
        int totalVirement = 0;
        for (Paiement paiement : paiements) {
            if (paiement.getMoyenPaiement() == Paiement.MoyenPaiement.CHEQUE) {
                totalCheque++;
            } else if (paiement.getMoyenPaiement() == Paiement.MoyenPaiement.VIREMENT) {
                totalVirement++;
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("CHEQUE", totalCheque),
                new PieChart.Data("VIREMENT", totalVirement)
        );

        pieChart.setData(pieChartData);
        //pieChart.setTitle("Paiement par chèque ou virement");


        // Sample data for the PieChart1
        int totalEffectue = 0;
        int totalNonEffectue = 0;
        for (Paiement paiement : paiements) {
            if (paiement.isEffectue()) {
                totalEffectue++;
            } else {
                totalNonEffectue++;
            }
        }

        ObservableList<PieChart.Data> pieChart1Data = FXCollections.observableArrayList(
                new PieChart.Data("Effectué", totalEffectue),
                new PieChart.Data("Non Effectué", totalNonEffectue)
        );

        pieChart1.setData(pieChart1Data);
        //pieChart1.setTitle("Paiement effectué ou non");
    }


    ///////////////////////////
    @FXML
    private void handleAction1(ActionEvent event) throws IOException {
        System.out.println("statiques initialize  :"+getisAdmin());

        MenuItem menuItem = (MenuItem) event.getSource();

        // Load the MenuAdmis.fxml and get the controller instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/MenuAdmis.fxml"));
        Parent root = loader.load();

        MenuAdmins menuAdminsController = loader.getController();
        menuAdminsController.setIsAdmin(getisAdmin());
        menuAdminsController.initialize();

        // Further code for scene setup and stage configuration
        Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);

        stage.show();
    }

    @FXML
    private void handleAction2(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();

        // Load the MenuAdmins.fxml and set the isAdmin value
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/Notes.fxml"));
        Parent root = loader.load();
        Notes notesController = loader.getController();
        notesController.setIsAdmin(getisAdmin());

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
        System.out.println("handel3 paiement:"+isAdmin);
        paiementsStatistiquesController.setIsAdminPs(getisAdmin());


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
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);

        stage.show();
    }


    ///////////////////////////
}
