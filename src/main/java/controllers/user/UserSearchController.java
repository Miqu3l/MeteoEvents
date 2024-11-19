package controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.TokenSingleton;
import model.User;
import model.crud.CrudUser;
import utilities.PathsViews;
import java.io.IOException;

/**
 * Controlador per a la funcionalitat de cerca d'usuaris.
 * Gestiona la selecció del criteri de cerca (ID, nom o nom d'usuari)
 * i executa la cerca segons l'atribut i valor introduïts.
 */
public class UserSearchController {

    /** Cadena de text que representa el valor de cerca introduït per l'usuari. */
    private String search;

    /** Cadena de text que indica l'atribut seleccionat per a la cerca (ID, nom o nom d'usuari). */
    private String attribute;

    /** Panell principal de l'àrea de cerca d'usuaris. */
    @FXML
    private AnchorPane anch_user_search;

    /** Etiqueta que mostra el criteri de cerca seleccionat per l'usuari. */
    @FXML
    private Label lbl_user_search;

    /** Etiqueta per mostrar missatges d'error o d'avís si no es troba cap resultat. */
    @FXML
    private Label lbl_response;

    /** Camp de text on l'usuari introdueix el valor que vol buscar. */
    @FXML
    private TextField txt_user_search;

    private String jwtToken;
    private CrudUser crudUser;
    private User user;

    /**
     * Mètode que s'executa en crear el controlador.
     */
    @FXML
    protected void initialize() throws Exception {
        jwtToken = TokenSingleton.getInstance().getJwtToken();
        crudUser = new CrudUser();
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca per ID.
     * Estableix l'atribut de cerca com a "ID".
     *
     * @param event l'esdeveniment de clic associat al botó ID.
     */
    @FXML
    void onIDButtonClick(ActionEvent event) {
        lbl_user_search.setText("ID");
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca per nom.
     * Estableix l'atribut de cerca com a "Nom".
     *
     * @param event l'esdeveniment de clic associat al botó Nom.
     */
    @FXML
    void onNameButtonClick(ActionEvent event) {
        lbl_user_search.setText("Nom");
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca per nom d'usuari.
     * Estableix l'atribut de cerca com a "Nom usuari".
     *
     * @param event l'esdeveniment de clic associat al botó Nom d'usuari.
     */
    @FXML
    void onUsernameButtonClick(ActionEvent event) {
        lbl_user_search.setText("Nom usuari");
    }

    /**
     * Mètode invocat quan es fa clic al botó de cerca.
     * Obté els valors de l'atribut seleccionat i del valor de cerca introduït.
     * Si algun d'aquests valors està buit, mostra un missatge d'error a l'usuari.
     *
     * @param event l'esdeveniment de clic associat al botó de cerca.
     */
    @FXML
    void onSearchButtonClick(ActionEvent event) throws Exception {
        user = null;
        lbl_response.setText("");
        attribute = lbl_user_search.getText();
        search = txt_user_search.getText();

        if (attribute.isEmpty() || search.isEmpty()) {
            lbl_response.setText("Has d'introduïr l'atribut i el valor a buscar");
        } else {
            user = crudUser.getUserById(search);
            if(user!=null){
                loadPanel(PathsViews.USER_MANAGEMENT_VIEW);
            }else{
                lbl_response.setText("Usuari no trobat");
            }
        }
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

            UserManagementController controller = fxmlLoader.getController();
            controller.setUser(user);

            anch_user_search.getChildren().clear();
            anch_user_search.getChildren().add(content);

            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Configura el CrudUser, útil per injectar mocks en prova.
     *
     * @param crudUser la instància de CrudEvent a injectar.
     */
    public void setCrudUser(CrudUser crudUser) {
        this.crudUser = crudUser;
    }
}