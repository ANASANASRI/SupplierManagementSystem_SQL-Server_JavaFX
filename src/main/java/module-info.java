module com.afm.suppliermanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.afm.suppliermanagementsystem to javafx.fxml;
    exports com.afm.suppliermanagementsystem;
}