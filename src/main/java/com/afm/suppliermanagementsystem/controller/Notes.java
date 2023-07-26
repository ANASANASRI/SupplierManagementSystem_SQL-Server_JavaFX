package com.afm.suppliermanagementsystem.controller;

import com.afm.suppliermanagementsystem.model.Note;
import com.afm.suppliermanagementsystem.model.NoteDataModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;

public class Notes implements Initializable {
    @FXML
    public ListView<String> notesList;
    @FXML
    public TextArea noteTitleBox;
    @FXML
    public TextArea noteBox;
    @FXML
    public Button saveButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button clearButton;
    @FXML
    public Button searchButton;
    @FXML
    public TextField searchField;
    @FXML
    public DatePicker datePicker;

    @FXML
    private Button setReminderButton;


    // Global variables
    private List<Note> notes;
    private String title;
    private String note;
    private String id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notes = NoteDataModel.getInstance().getNotes();
        for (Note note : notes) {
            notesList.getItems().add(note.getTitle());
        }

        notesList.setOnMouseClicked(event -> {
            String current = notesList.getSelectionModel().getSelectedItem();
            if (current != null) {
                clearFields();
                for (Note note : notes) {
                    if (note.getTitle().equals(current)) {
                        noteTitleBox.setText(current);
                        noteBox.setText(note.getNote());
                        datePicker.setValue(note.getDate()); // Set the selected date in the DatePicker
                    }
                }
            }
        });

        deleteButton.setOnAction(event -> {
            int selectedIndex = notesList.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                notes.remove(selectedIndex);
                notesList.getItems().remove(selectedIndex);
            }
        });

        // Real-time search listener
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText();
        });

        setReminderButton.setOnAction(this::setNoteAsReminder);

    }


    public void saveNote() {
        LocalDate selectedDate = datePicker.getValue();
        this.title = noteTitleBox.getText();
        this.note = noteBox.getText();
        this.id = String.valueOf(notes.size() + 100001);

        Note note = new Note();
        note.setTitle(this.title);
        note.setNote(this.note);
        note.setId(this.id);
        note.setDate(selectedDate); // Set the selected date

        notes.add(note);
        updateNotesList(notes);
        clearFields();
    }




    @FXML
    private void clearFields() {
        noteTitleBox.setText("");
        noteBox.setText("");
        datePicker.setValue(null);
    }

    private void updateNotesList(List<Note> notes) {
        notesList.getItems().clear();
        // TODO: Checkbox features should be added here
        notesList.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(String item) {
                BooleanProperty observable = new SimpleBooleanProperty();
                observable.addListener((obs, wasSelected, isNowSelected) -> {
                    System.out.println("CHECKBOX : Checkbox for " + item + " changed to " + wasSelected);

                });
                return observable;
            }
        }));
        for (Note note : notes) {
            notesList.getItems().add(note.getTitle());
        }
    }

    public void searchText() {
        notesList.getItems().clear();
        String searchText = this.searchField.getText();
        if (searchText.equals("")) {
            setNotesBase();
            return;
        }

        List<Note> searchedList = new ArrayList<>();

        for (Note note : notes) {
            String noteTitle = note.getTitle();
            String noteBody = note.getNote();
            if (noteTitle.contains(searchText) || noteBody.contains(searchText)) {
                searchedList.add(note);
            }
        }

        updateNotesList(searchedList);
    }


    public void setNotesBase() {
        notesList.getItems().clear();
        for (Note note : notes) {
            notesList.getItems().add(note.getTitle());
        }
    }

    @FXML
    private void setNoteAsReminder(ActionEvent event) {
        String selectedNote = notesList.getSelectionModel().getSelectedItem();
        System.out.println("Selected Note: " + selectedNote);
    }

    ///////////////////////////

    @FXML
    private void handleAction1(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Parent root = FXMLLoader.load(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/MenuAdmis.fxml"));

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
        Parent root = FXMLLoader.load(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/Notes.fxml"));

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
    private void handleAction3(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Parent root = FXMLLoader.load(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/PaiementsStatistiques.fxml"));

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

    public void handleAction4(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Parent root = FXMLLoader.load(getClass().getResource("/com/afm/suppliermanagementsystem/fxml/InscriptionEtAuthentification.fxml"));

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


    ///////////////////////////

}
