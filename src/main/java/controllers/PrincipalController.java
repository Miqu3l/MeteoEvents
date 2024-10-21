package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.LoginClient;
import java.io.IOException;
import java.net.http.HttpClient;

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

    @FXML
    Button btn_princ_close;

    /** Token JWT utilitzat per a l'autenticació de l'usuari. */
    String jwtToken;

    private HttpClient httpClient;
    LoginClient loginClient;

    /**
     * Mètode que s'executa en crear el controlador i que inicialitza la variable HttpClient.
     */
    @FXML
    protected void initialize() {
        httpClient = HttpClient.newHttpClient();
        loginClient = new LoginClient();
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
        try {
            Stage stageActual = (Stage) btn_princ_close.getScene().getWindow();
            stageActual.close();
            loginClient.logoutUsuari(jwtToken);
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
        // TODO
    }

    /**
     * Gestiona l'esdeveniment del botó per esborrar un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onBorrarTreballadorButtonClick(ActionEvent event) {
        // TODO
    }

    /**
     * Gestiona l'esdeveniment del botó per llistar els esdeveniments.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onLlistarEsdevenimentButtonClick(ActionEvent event) {
        // TODO
    }

    /**
     * Gestiona l'esdeveniment del botó per llistar els treballadors.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onLlistarTreballadorButtonClick(ActionEvent event) {
        // TODO
    }

    /**
     * Gestiona l'esdeveniment del botó per modificar un esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onModificarEsdevenimentButtonClick(ActionEvent event) {
        // TODO
    }

    /**
     * Gestiona l'esdeveniment del botó per modificar un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onModificarTreballadorButtonClick(ActionEvent event) {
        // TODO
    }

    /**
     * Gestiona l'esdeveniment del botó per afegir un nou esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onNouEsdevenimentButtonClick(ActionEvent event) {
        // TODO
        System.out.println(jwtToken);
    }

    /**
     * Gestiona l'esdeveniment del botó per afegir un nou treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onNouTreballadorButtonClick(ActionEvent event) {
        // TODO
    }

    /**
     * Gestiona l'esdeveniment del botó per veure un esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onVeureEsdevenimentButtonClick(ActionEvent event) {
        // TODO
    }

    /**
     * Gestiona l'esdeveniment del botó per veure un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onVeureTreballadorButtonClick(ActionEvent event) {
        // TODO
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