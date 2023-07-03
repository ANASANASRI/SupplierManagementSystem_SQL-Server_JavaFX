package com.afm.suppliermanagementsystem.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.afm.suppliermanagementsystem.HelloApplication;
import com.afm.suppliermanagementsystem.dao.imp.DB;
import com.afm.suppliermanagementsystem.model.Compte;


import com.afm.suppliermanagementsystem.services.CompteService;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class InscriptionEtAuthentification  {

    @FXML
    private JFXTextField pseudoc;

    @FXML
    private JFXPasswordField motpassc;

    private HelloApplication application;

    public void setApplication(HelloApplication application) {
        this.application = application;
    }
    //Les variables interface graphique
    public int type;
    public static Compte profile;

    private void loadInsc(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/Ins.fxml"));
        Scene s = new Scene(root);
        Stage fenetre = (Stage) ((Node) e.getSource()).getScene().getWindow();
        fenetre.setScene(s);
        fenetre.setResizable(false);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        fenetre.setX((primScreenBounds.getWidth() - fenetre.getWidth()) / 2);
        fenetre.setY((primScreenBounds.getHeight() - fenetre.getHeight()) / 4);

        fenetre.show();
    }


    public void inscrire(ActionEvent e) throws IOException, SQLException {
        DB.getConnection();
        loadInsc(e);
    }

    //////////////////////////////////////////////////////////////

    private void loadConnAdm(ActionEvent e) throws IOException {
        String nom = pseudoc.getText();
        String password = motpassc.getText();
        boolean compteExists = CompteService.findCompte(nom,password);

        if (compteExists) {
            Parent root = FXMLLoader.load(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/MenuAdmis.fxml"));
            Scene s = new Scene(root);
            Stage fenetre = (Stage) ((Node) e.getSource()).getScene().getWindow();
            fenetre.setScene(s);
            fenetre.setResizable(false);
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            fenetre.setX((primScreenBounds.getWidth() - fenetre.getWidth()) / 2);
            fenetre.setY((primScreenBounds.getHeight() - fenetre.getHeight()) / 4);

            fenetre.show();
        } else {
            showAlert("Compte non existant", "Le compte spécifié n'existe pas.");
        }
    }

    public void ConnectAdmin(ActionEvent e) throws IOException, SQLException {
        DB.getConnection();
        loadConnAdm(e);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    //////////////////////////////////////////////////////////////


    //////////////////////////////////

    @FXML
    private JFXTextField mot_pass_show;
    @FXML
    private ImageView lblclose;
    @FXML
    private ImageView lblopen;

    String password;

    public void openEye(MouseEvent mouseEvent) {
        mot_pass_show.setVisible(false);
        motpassc.setVisible(true);
        lblopen.setVisible(false);
        lblclose.setVisible(true);
    }

    public void closeEye(MouseEvent mouseEvent) {
        mot_pass_show.setVisible(true);
        motpassc.setVisible(false);
        lblopen.setVisible(true);
        lblclose.setVisible(false);
    }

    public void hide_passWord(KeyEvent keyEvent) {
        password = motpassc.getText();
        mot_pass_show.setText(password);
    }

    public void showed_passWord(KeyEvent keyEvent) {
        password = mot_pass_show.getText();
        motpassc.setText(password);
    }

    public void initialize(){
        mot_pass_show.setVisible(false);
        lblopen.setVisible(false);
    }


    //////////////////////////////////////


    /*@FXML
    private JFXTextField pseudoc;

    @FXML
    private JFXPasswordField motpassc;

    // ...

    @FXML
    public void connect(ActionEvent event) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DB.getConnection();

            String pseudo_nom = pseudoc.getText();
            String mot_pass = motpassc.getText();

            String sql = "SELECT * FROM table_compte WHERE pseudo_nom = ? AND mot_pass = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, pseudo_nom);
            stmt.setString(2, mot_pass);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // The account exists, perform the redirection to adminmenu.fxml

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/MenuAdmis.fxml"));
                    Parent root = loader.load();
                    MenuAdmins adminMenuController = loader.getController();
                    adminMenuController.setAccountInfo(rs.getString("nom"), rs.getString("prenom"));

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("The account does not exist or the credentials are incorrect.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(stmt);
            DB.closeConnection();
        }
    }*/



}
