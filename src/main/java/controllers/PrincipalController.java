package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.meteoevents.meteoevents.App;
import utilities.PathsViews;
import utilities.URLRequests;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Controlador per a la gestió dels esdeveniments de la vista de la pàgina principal
 * per als usuaris administradors. En aquesta classe es gestionen les interaccions amb els
 * botons per a esborrar, llistar, crear, modificar i visualitzar, tant d'esdeveniments com
 * de treballadors.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class PrincipalController {

    /** Contenidor de la vista. */
    @FXML
    private AnchorPane anch_princ_main;

    /** Token JWT utilitzat per a l'autenticació de l'usuari. */
    String jwtToken;

    private HttpClient httpClient;
    private HttpResponse<String> response;

    /**
     * Mètode que s'executa en crear el controlador i que inicialitza la variable HttpClient.
     */
    @FXML
    protected void initialize() {
        httpClient = HttpClient.newHttpClient();
    }

    /**
     * Gestiona l'esdeveniment del botó per tancar la finestra i sortir de la sessió.
     * Fa la petició al backend de login, esborra el token JWT, tanca la finestra actual
     * i obre la vista de login.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onCloseButtonClick(ActionEvent event) {

        //Petició de logout al backend
        try{
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(URLRequests.LOGOUT_URL))
                    .header("Authorization", "Bearer " + jwtToken)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            //Esborra el token
            setJwtToken("");

            //Tanca la finestra actual.
            Node source = (Node) event.getSource();
            Stage stageLogin = (Stage) source.getScene().getWindow();
            stageLogin.close();

            //S'obre la finestra de login
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(PathsViews.LOGIN_VIEW));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Meteo Events");
            stage.setScene(scene);
            stage.show();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gestiona l'esdeveniment del botó per esborrar un esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onBorrarEsdevenimentButtonClick(ActionEvent event) {
        // Implementació pendent
    }

    /**
     * Gestiona l'esdeveniment del botó per esborrar un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onBorrarTreballadorButtonClick(ActionEvent event) {
        // Implementació pendent
    }

    /**
     * Gestiona l'esdeveniment del botó per llistar els esdeveniments.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onLlistarEsdevenimentButtonClick(ActionEvent event) {
        // Implementació pendent
    }

    /**
     * Gestiona l'esdeveniment del botó per llistar els treballadors.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onLlistarTreballadorButtonClick(ActionEvent event) {
        // Implementació pendent
    }

    /**
     * Gestiona l'esdeveniment del botó per modificar un esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onModificarEsdevenimentButtonClick(ActionEvent event) {
        // Implementació pendent
    }

    /**
     * Gestiona l'esdeveniment del botó per modificar un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onModificarTreballadorButtonClick(ActionEvent event) {
        // Implementació pendent
    }

    /**
     * Gestiona l'esdeveniment del botó per afegir un nou esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onNouEsdevenimentButtonClick(ActionEvent event) {
        System.out.println(jwtToken);
    }

    /**
     * Gestiona l'esdeveniment del botó per afegir un nou treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onNouTreballadorButtonClick(ActionEvent event) {
        // Implementació pendent
    }

    /**
     * Gestiona l'esdeveniment del botó per veure un esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onVeureEsdevenimentButtonClick(ActionEvent event) {
        // Implementació pendent
    }

    /**
     * Gestiona l'esdeveniment del botó per veure un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onVeureTreballadorButtonClick(ActionEvent event) {
        // Implementació pendent
    }

    /**
     * Mètode setter per gestionar el token JWT retornqt per la base de dades.
     *
     * @param token El token JWT.
     */
    public void setJwtToken(String token){
        jwtToken = token;
    }
}