module org.meteoevents.meteoevents {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens org.meteoevents.meteoevents to javafx.fxml;
    exports org.meteoevents.meteoevents;
    exports controllers;
    opens controllers to javafx.fxml;
    exports controllers.user;
    opens controllers.user to javafx.fxml;
    exports controllers.event;
    opens controllers.event to javafx.fxml;
    exports controllers.measure;
    opens controllers.measure to javafx.fxml;
    exports model.model to com.fasterxml.jackson.databind;
    exports controllers.aemet;
    opens controllers.aemet to javafx.fxml;

    opens model.statusEvent to com.fasterxml.jackson.databind;
    exports model.statusEvent;

    exports model.aemet;
    exports controllers.login;
    opens controllers.login to javafx.fxml;
    exports model.tokenSingleton to com.fasterxml.jackson.databind;

    opens model.model to com.fasterxml.jackson.databind;

    //requires org.junit.jupiter.api;
    //requires org.mockito;
    //requires org.assertj.core;
}