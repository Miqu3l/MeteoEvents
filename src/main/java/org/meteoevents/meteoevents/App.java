package org.meteoevents.meteoevents;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.PathsViews;

import java.io.IOException;

/**
 * Classe encarregada d'iniciar el programa i la interfície gràfica d'usuari per a l'inici de sessió.
 * En iniciar l'aplicació, es carrega la vista d'inici de sessió mitjançant el fitxer login-view.fxml,
 * on s'ha d'introduïr el nom d'usuari i la contrasenya.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class App extends Application {

    /**
     * Aquest mètode és el punt inicial per a una aplicació JavaFX. S'executa quan l'aplicació,
     * s'inicia i configura l'escena principal que es mostrarà a l'usuari.
     *
     * @param stage Escenari o finestra principal de l'aplicació.
     * @throws IOException Si es produeix un error al carregar el fitxer FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(PathsViews.LOGIN_VIEW));
        Scene scene = new Scene(fxmlLoader.load(), 600, 475);
        stage.setTitle("Meteo Events Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Mètode main. Punt d'entrada de l'aplicació. Crida al mètode launch per iniciar l'aplicació
     * JavaFX.
     *
     * @param args Arguments de línia de comandes passats a l'aplicació.
     */
    public static void main(String[] args) {
        launch();
    }
}