package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.TokenSingleton;
import model.login.LoginClient;
import utilities.PathsViews;
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

    /** Botó que gestiona el tancament de sessió i el retorn a la pantalla de login. */
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
        jwtToken = TokenSingleton.getInstance().getJwtToken();
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
        loadPanel(PathsViews.EVENT_SEARCH_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per esborrar una mesura de prevenció.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onBorrarMesuraButtonClick(ActionEvent event) {
        loadPanel(PathsViews.MEASURE_SEARCH_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per esborrar un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onBorrarTreballadorButtonClick(ActionEvent event) {
        loadPanel(PathsViews.USER_SEARCH_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per llistar els esdeveniments.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onLlistarEsdevenimentButtonClick(ActionEvent event) {
        loadPanel(PathsViews.EVENT_LIST_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per llistar totes les mesures de prevenció.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onLlistarMesuresButtonClick(ActionEvent event) {
        loadPanel(PathsViews.MEASURE_LIST_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per llistar els treballadors.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onLlistarTreballadorButtonClick(ActionEvent event) {
        loadPanel(PathsViews.USER_LIST_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per modificar un esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onModificarEsdevenimentButtonClick(ActionEvent event) {
        loadPanel(PathsViews.EVENT_SEARCH_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per modificar una mesura de prevenció.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onModificarMesuraButtonClick(ActionEvent event) {
        loadPanel(PathsViews.MEASURE_SEARCH_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per modificar un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onModificarTreballadorButtonClick(ActionEvent event) {
        loadPanel(PathsViews.USER_SEARCH_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per afegir un nou esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onNouEsdevenimentButtonClick(ActionEvent event) {
        loadPanel(PathsViews.EVENT_MANAGEMENT_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per afegir una nova mesura de prevenció.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onNovaMesuraButtonClick(ActionEvent event) {
        loadPanel(PathsViews.MEASURE_MANAGEMENT_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per afegir un nou treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onNouTreballadorButtonClick(ActionEvent event) {
        loadPanel(PathsViews.USER_MANAGEMENT_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per veure un esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onVeureEsdevenimentButtonClick(ActionEvent event) {
        loadPanel(PathsViews.EVENT_SEARCH_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per visualitzar una mesura de prevenció.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onVeureMesuraButtonClick(ActionEvent event) {
        loadPanel(PathsViews.MEASURE_SEARCH_VIEW);
    }

    /**
     * Gestiona l'esdeveniment del botó per veure un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onVeureTreballadorButtonClick(ActionEvent event) {
        loadPanel(PathsViews.USER_SEARCH_VIEW);
    }

    /**
     * Carrega un nou panell.
     *
     * @param path El camí de la vista a carregar.
     */
    private void loadPanel(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Node content = fxmlLoader.load();

            anch_princ_main.getChildren().clear();
            anch_princ_main.getChildren().add(content);

            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}