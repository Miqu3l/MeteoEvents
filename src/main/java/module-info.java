module org.meteoevents.meteoevents {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.net.http;

    opens org.meteoevents.meteoevents to javafx.fxml;
    exports org.meteoevents.meteoevents;
    exports controllers;
    opens controllers to javafx.fxml;
}