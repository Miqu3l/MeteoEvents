package controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import model.TokenSingleton;
import model.User;
import model.crud.CrudUser;
import utilities.PathsViews;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

/**
 * Controlador per a la interfície d'usuari de la llista d'usuaris.
 * Gestiona les accions dels botons "Guardar" i "Visualitzar" i proporciona
 * mètodes per obtenir i establir el panell de la llista d'usuaris.
 */
public class UserListController {

    /** Constant per gestionar que s'ha seleccionat un usuari. */
    private static final String RESPONSE_NULL = "Has de seleccionar un usuari.";

    @FXML
    private AnchorPane anch_user_list;

    /** Panell on es mostrarà la llista d'usuaris. */
    @FXML
    private ListView<User> list_users;

    /** Label on es mostra el missatge de resposta del servidor en cas d'error. */
    @FXML
    private Label lbl_response_list_user;

    private static final String PATH = PathsViews.USER_MANAGEMENT_VIEW;

    private HttpClient httpClient;
    private String jwtToken;
    private List<User> list;
    private CrudUser crudUser;

    /**
     * Mètode que s'executa en crear el controlador.
     */
    @FXML
    protected void initialize() throws Exception {
        jwtToken = TokenSingleton.getInstance().getJwtToken();
        lbl_response_list_user.setText("");
        crudUser = new CrudUser();
        list = requestUsers();
        loadList(list);
    }

    /**
     * Mètode invocat quan es fa clic al botó de "Guardar".
     *
     * @param event l'esdeveniment de clic associat al botó.
     */
    @FXML
    void onDeleteButtonClick(ActionEvent event) throws Exception {
        lbl_response_list_user.setText("");

        User selectedUser = list_users.getSelectionModel().getSelectedItem();

        if(selectedUser != null){
            String id = selectedUser.getID();
            String status = crudUser.deleteUser(id);

            if(status.equals("Usuari esborrat correctament")){
                list_users.getItems().clear();
                list = requestUsers();
                loadList(list);
            }else{
                lbl_response_list_user.setText(status);
            }
        }else{
            lbl_response_list_user.setText(RESPONSE_NULL);
        }
    }

    /**
     * Mètode invocat quan es fa clic al botó de "Visualitzar".
     *
     * @param event l'esdeveniment de clic associat al botó.
     */
    @FXML
    void onViewButtonClick(ActionEvent event) throws IOException {
        lbl_response_list_user.setText("");

        loadPanel(PATH);
    }

    /**
     * Mètode encarregat de fer la petició a la base de dades".
     *
     * @return la llista dels usuaris trobats a la base de dades.
     */
    private List<User> requestUsers() throws Exception {
        return crudUser.getAllUsers();
    }

    /**
     * Mètode encarregat de carregat el listview amb els usuaris retornats per
     * la base de dades.
     *
     * @param list llista d'objectes user retornada per la base de dades.
     */
    private void loadList(List<User> list) throws Exception {
        list_users.getItems().addAll(list);
    }

    /**
     * Carrega un nou panell.
     *
     * @param path El camí de la vista a carregar.
     */
    private void loadPanel(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Node content = fxmlLoader.load();

        User selectedUser = list_users.getSelectionModel().getSelectedItem();

        UserManagementController controller = fxmlLoader.getController();
        controller.setUser(selectedUser);

        anch_user_list.getChildren().clear();
        anch_user_list.getChildren().add(content);

        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);
    }
}