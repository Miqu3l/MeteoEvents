package org.meteoevents.meteoevents;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.Paths;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Paths.LOGIN_VIEW));
        Scene scene = new Scene(fxmlLoader.load(), 600, 475);
        stage.setTitle("Meteo Events Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}