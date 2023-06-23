module com.afm.suppliermanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.jfoenix;

    opens com.afm.suppliermanagementsystem to javafx.fxml;
    exports com.afm.suppliermanagementsystem;
    exports com.afm.suppliermanagementsystem.controller;
    opens com.afm.suppliermanagementsystem.controller to javafx.fxml;

    exports com.afm.suppliermanagementsystem.model;
    exports com.afm.suppliermanagementsystem.services;
    exports com.afm.suppliermanagementsystem.dao;

}