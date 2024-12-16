package controllers.principal;

import controllers.event.EventListController;
import controllers.user.UserManagementController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.tokenSingleton.TokenSingleton;
import model.model.User;
import model.crud.CrudUser;
import model.login.LoginClient;
import utilities.PathsViews;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Controlador per a la gestió dels esdeveniments de la vista de la pàgina principal
 * per als usuaris estàndard. En aquesta classe es gestionen les interaccions amb els
 * botons per a visualitzar la llista i l'estat dels esdeveniments.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class PrincipalStandardController {

    /** Contenidor de la vista. */
    @FXML
    private AnchorPane anch_princStandard_main;

    @FXML
    private Button btn_princ_close;

    /** Token JWT utilitzat per a l'autenticació de l'usuari. */
    String jwtToken;

    private HttpClient httpClient;
    private HttpRequest httpRequest;
    private HttpResponse<String> response;
    private LoginClient loginClient;

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
     * Gestiona l'esdeveniment del botó per llistar els esdeveniments.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onLlistarEsdevenimentButtonClick(ActionEvent event) {
        loadPanel(PathsViews.EVENT_LIST_VIEW);
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
     * Gestiona l'esdeveniment del botó per veure un usuari i modificar les seves dades.
     *
     * @param event L'esdeveniment del botó.
     */
    public void onVeureUsuariButtonClick(ActionEvent event) {
        loadPanel(PathsViews.USER_MANAGEMENT_VIEW);
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

            if(path.equals(PathsViews.USER_MANAGEMENT_VIEW)){
                CrudUser crudUser = new CrudUser();
                User user = crudUser.getUserById(TokenSingleton.getInstance().getId());
                UserManagementController controller = fxmlLoader.getController();
                controller.setUser(user);
                controller.getBtn_user_management_delete().setVisible(false);
                controller.getBtn_user_management_save().setVisible(false);
            }else if(path.equals(PathsViews.EVENT_LIST_VIEW)){
                EventListController controller = fxmlLoader.getController();
                controller.getBtn_event_list_delete().setVisible(false);
            }

            anch_princStandard_main.getChildren().clear();
            anch_princStandard_main.getChildren().add(content);

            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}